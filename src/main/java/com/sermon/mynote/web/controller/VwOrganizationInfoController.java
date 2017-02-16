package com.sermon.mynote.web.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.transfer.Upload;
import com.sermon.mynote.domain.StatusMsg;
import com.sermon.mynote.domain.StatusResponse;
import com.sermon.mynote.domain.VwOrganizationInfo;
import com.sermon.mynote.service.OrganizationService;
import com.sermon.mynote.service.VwOrganizationInfoService;
import com.sermon.util.AppConstants;

@RequestMapping("/orgInfo")
@Controller
public class VwOrganizationInfoController {

	final Logger logger = LoggerFactory.getLogger(VwOrganizationInfoController.class);

	@Autowired
	private VwOrganizationInfoService vwOrganizationInfoService;

	@Autowired
	private OrganizationService organizationService;

	@RequestMapping(value = "/searchByOrgId/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<VwOrganizationInfo> getOrganizationInfoByOrgId(@PathVariable int id) {
		logger.info("Listing contacts");

		List<VwOrganizationInfo> vwOrgInfo = vwOrganizationInfoService.findOrganizationInfoByOrgId(id);
		return vwOrgInfo;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public StatusResponse updateOrgInfo(@RequestBody VwOrganizationInfo orgInfo) {

		int result = vwOrganizationInfoService.updateOrgInfo(orgInfo.getOrganizationId(), orgInfo.getWebsite(),
				orgInfo.getPrimaryEmail(), orgInfo.getGeneralInfo(), orgInfo.getHours(), orgInfo.getFacebookLink());

		organizationService.updateOrganization(orgInfo.getOrganizationId(), orgInfo.getAddress1(),
				orgInfo.getAddress2(), orgInfo.getCityId(), orgInfo.getStateId(), orgInfo.getCountryID(),
				orgInfo.getZipCode());

		StatusResponse status = new StatusResponse();
		if (result == 0) {
			status.setStatus(true);
		} else {
			status.setStatus(false);
		}

		return status;
	}
	
	@RequestMapping(value = "/UploadImage/{orgId}", method = RequestMethod.POST)
	@ResponseBody
	public StatusMsg FileUpload(@PathVariable int orgId, HttpServletRequest request, HttpServletResponse response) {
		MultipartHttpServletRequest mRequest;
		MultipartFile mFile = null;
		StatusMsg statusMsg = new StatusMsg();
		String imgName = null;
		logger.info("orgId : " + orgId);
		try {
			mRequest = (MultipartHttpServletRequest) request;
			mRequest.getParameterMap();

			Iterator<String> itr = mRequest.getFileNames();
			while (itr.hasNext()) {
				mFile = mRequest.getFile(itr.next());
				imgName = mFile.getOriginalFilename();
				logger.info("filename : " + imgName + " size : " + mFile.getSize());
			}

			String existingNoteImgName = vwOrganizationInfoService.getOrgImage(orgId);
			String imgToDelete = null;
			if (existingNoteImgName != null) {
				imgToDelete = existingNoteImgName;
			}

			// convert image to jpg
			BufferedImage image = ImageIO.read(mFile.getInputStream());
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "jpg", baos);
			InputStream fis = new ByteArrayInputStream(baos.toByteArray());

			String temp = imgName.substring(0, imgName.lastIndexOf('.'));
			String imageName = temp + ".jpg";

			Upload myUpload = vwOrganizationInfoService.uploadOrgFiles(fis, imageName, imgToDelete, orgId);
			myUpload.waitForCompletion();
			if (myUpload.isDone())
				vwOrganizationInfoService.saveImage(orgId, imageName);

			statusMsg.setStatus(AppConstants.FILES_UPLOAD);
			return statusMsg;
		} catch (Exception e) {
			e.printStackTrace();
			statusMsg.setStatus(AppConstants.ERROR_INTERNAL);
			return statusMsg;
		}
	}
	
	
	@RequestMapping(value = "/getOrgImage/{id}", method = RequestMethod.GET, produces = "image/jpeg")
	@ResponseBody
	public byte[] getSermonImage(@PathVariable int id) throws IOException {

		InputStream inputStream = vwOrganizationInfoService.getOrgImageAsStream(id);

		byte[] bytes = null;
		String extension = null;

		S3ObjectInputStream s3InputStream = null;
		if (inputStream != null) {
			try {
				s3InputStream = (S3ObjectInputStream) inputStream;
				extension = s3InputStream.getHttpRequest().getURI().getPath();
				extension = extension.substring(extension.lastIndexOf(".") + 1);
				bytes = IOUtils.toByteArray(s3InputStream);
				int size = bytes.length;
				logger.info("image size : " + size);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				s3InputStream.close();
			}
			return bytes;
		} else {
			return bytes;
		}

	}
	

}
