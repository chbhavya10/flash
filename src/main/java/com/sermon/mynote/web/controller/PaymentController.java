package com.sermon.mynote.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sermon.mynote.domain.UserFavorateEvents;
import com.sermon.mynote.domain.Payment;
import com.sermon.mynote.domain.StatusResponse;
import com.sermon.mynote.service.PaymentService;

@RequestMapping("/payment")
@Controller
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	

	@RequestMapping(value = "/savePayment", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	private StatusResponse favorateOrUnfavorateEvent(@RequestBody Payment payment) {
		
		return paymentService.paymentPersist(payment);
	}
	
	


}

