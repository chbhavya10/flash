package com.sermon.mynote.service;

import com.sermon.mynote.domain.Donation;

public interface StripePaymentsTxService {

	public Donation save(Donation stripePaymentTx);

}
