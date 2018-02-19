package com.sermon.mynote.payments.stripe;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

@Service
public class StripePaymentService {
 
    @Value("${STRIPE_SECRET_KEY}")
    private String secretKey;
     
    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }
	
    /* Stripe API requests expect amounts to be provided in a currency’s smallest unit, 
	 * Generally smallest units are cents. There are some exception like japan currency there is no smallest unit.  
	 * So Multiplying the received amount by 100 to convert into actual charge amount
	 * 
	 */
    public Charge charge(ChargeRequest chargeRequest) throws StripeException {
    	 Map<String, Object> chargeParams = new HashMap<>();
         chargeParams.put("amount", chargeRequest.getAmount()*100);
         chargeParams.put("currency", chargeRequest.getCurrency());
         chargeParams.put("description", chargeRequest.getDescription());
         chargeParams.put("source",chargeRequest.getSource());
         Map<String, Object> destinationParams = new HashMap<String, Object>();
         destinationParams.put("account", chargeRequest.getDestinationAcctId());
         chargeParams.put("destination", destinationParams);
         System.out.println("Charge Map --->"+chargeParams);
         return Charge.create(chargeParams);
    }
}