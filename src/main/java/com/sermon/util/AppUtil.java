package com.sermon.util;

import java.security.MessageDigest;

public class AppUtil {

	public static String sha256(String base) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(base.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static String getFileExtension(String extension) {
		if (extension.equals("pdf"))
			return AppConstants.MIME_PDF;

		else if (extension.equals("png"))
			return AppConstants.MIME_IMG_PNG;
		else
			return AppConstants.MIME_IMG_JPG;
	}

}
