package com.sermon.mynote.service.jpa;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sermon.mynote.domain.Payment;
import com.sermon.mynote.domain.StatusResponse;
import com.sermon.mynote.repository.PaymentRepository;
import com.sermon.mynote.service.PaymentService;

@Service("addPayment")
@Repository
public class PaymentServiceImpl implements PaymentService{

	@Autowired
	PaymentRepository paymentRepository;

	@Override
	public StatusResponse paymentPersist(Payment payment) {
		
		StatusResponse statusResponse = new StatusResponse();

		try {
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
			Date date = new Date();
			System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
			String datesdf = dateFormat.format(date);
			
			payment.setCreatedDate(dateFormat.format(date));
			paymentRepository.save(payment);
			statusResponse.setStatus(true);
			statusResponse.setMessage("Successfully Updated the Status");
		} catch (Exception e) {
			statusResponse.setMessage("Error Occured in Payment Saving");
			e.printStackTrace();
			
		}
		
		return statusResponse;
	}

	
}
