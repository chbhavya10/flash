package com.sermon.mynote.service.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sermon.mynote.domain.Donation;
import com.sermon.mynote.repository.StripePaymentRepository;
import com.sermon.mynote.service.StripePaymentsTxService;

@Service
@Transactional
public class StripePaymentsTxServiceImpl implements StripePaymentsTxService{
	
	@Autowired
	StripePaymentRepository stripePaymentsRepo;

	@Override
	public Donation save(Donation stripePaymentTx) {
		return stripePaymentsRepo.save(stripePaymentTx);
	}
 
}
