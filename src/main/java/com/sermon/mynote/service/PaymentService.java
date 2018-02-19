package com.sermon.mynote.service;


import com.sermon.mynote.domain.Payment;
import com.sermon.mynote.domain.StatusResponse;

public interface PaymentService {
	

	StatusResponse paymentPersist(Payment payment);


}
