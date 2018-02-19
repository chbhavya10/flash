package com.sermon.mynote.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sermon.mynote.domain.Donation;
import com.sermon.mynote.payments.stripe.ChargeRequest;
import com.sermon.mynote.payments.stripe.StripePaymentService;
import com.sermon.mynote.service.StripePaymentsTxService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

@Controller
public class StripePaymentController {

	@Autowired
    private StripePaymentService stripePayService;
	
	@Autowired
	private StripePaymentsTxService stripePaymentTxService;
	
	
	@RequestMapping(value = "/stripe/donate", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public ResponseEntity<?>  chargeStripeAmount(@RequestBody ChargeRequest chargeRequest) throws StripeException {
		System.out.println(chargeRequest);
		Charge charge = stripePayService.charge(chargeRequest);
	
		if(charge!=null && "succeeded".equalsIgnoreCase(charge.getStatus())){
			Donation tsp = new Donation();
			tsp.setChargeAmt(charge.getAmount().intValue());
			tsp.setChargeId(charge.getId());
			tsp.setBalanceTxnId(charge.getBalanceTransaction());
			tsp.setCreateDate(new Date());
			tsp.setStatus(charge.getStatus());
			tsp.setDestinationAcctId(chargeRequest.getDestinationAcctId());
			stripePaymentTxService.save(tsp);
		}
		Map<String,String> respMap = new HashMap<String,String>();
		respMap.put("status", charge.getStatus());
		respMap.put("chargeId", charge.getId());
		respMap.put("balanceTxnId", charge.getBalanceTransaction());
		return new ResponseEntity<>(respMap,HttpStatus.OK);
	
	}
}
