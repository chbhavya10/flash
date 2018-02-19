package com.sermon.mynote.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sermon.mynote.model.ResponseMsg;
import com.stripe.exception.InvalidRequestException;
import com.stripe.exception.StripeException;

/**
 * Global Exception Handler for REST Endpoints 
 * @author govind
 *
 */
@ControllerAdvice
public class RestExceptionControllerAdvice extends ResponseEntityExceptionHandler {
		
	  @ExceptionHandler(StripeException.class)
	  public ResponseEntity<?> stripeException(final StripeException e) {
		  ResponseMsg responseMsg = new ResponseMsg(e.getMessage());
		  e.printStackTrace();
		  return new ResponseEntity(responseMsg, HttpStatus.valueOf(e.getStatusCode()));
	  }
	  
	  @ExceptionHandler(InvalidRequestException.class)
	  public ResponseEntity<?> stripeInvalidRequestException(final InvalidRequestException e) {
		  ResponseMsg responseMsg = new ResponseMsg(e.getMessage());
		  e.printStackTrace();
		  return new ResponseEntity(responseMsg, HttpStatus.BAD_REQUEST);
	  }
	  
	  @ExceptionHandler(Exception.class)
	  public ResponseEntity<?> runtimeException(final Exception e) {
		  ResponseMsg responseMsg = new ResponseMsg("Internal Server Error ...");
		  e.printStackTrace();
		  return new ResponseEntity(responseMsg, HttpStatus.INTERNAL_SERVER_ERROR);
	  }
}
