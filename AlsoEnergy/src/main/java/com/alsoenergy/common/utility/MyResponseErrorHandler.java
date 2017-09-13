package com.alsoenergy.common.utility;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class MyResponseErrorHandler implements ResponseErrorHandler {
	@Override
	public void handleError(ClientHttpResponse clienthttpresponse) throws IOException {

	    if (clienthttpresponse.getStatusCode() == HttpStatus.FORBIDDEN) {
	        System.out.println(HttpStatus.FORBIDDEN + " response. Throwing authentication exception");
	    }
	    if (clienthttpresponse.getStatusCode() == HttpStatus.NOT_FOUND) {
	    	System.out.println("Amit2");
	        System.out.println(HttpStatus.NOT_FOUND + " response. Throwing Not Found exception");
	    }
	}

	@Override
	public boolean hasError(ClientHttpResponse clienthttpresponse) throws IOException {
System.out.println("Amit :: " + clienthttpresponse.getStatusCode() + "   " + clienthttpresponse.getRawStatusCode());
	    if (clienthttpresponse.getStatusCode() != HttpStatus.OK) {
//	        System.out.println("Status code: " + clienthttpresponse.getStatusCode());
//	        System.out.println("Response" + clienthttpresponse.getStatusText());
//	        System.out.println(clienthttpresponse.getBody());
//
//	        
	    }
	    if (clienthttpresponse.getStatusCode() == HttpStatus.FORBIDDEN) {
            System.out.println("Call returned a error 403 forbidden resposne ");
            return true;
        }
	    if (clienthttpresponse.getStatusCode() == HttpStatus.NOT_FOUND) {
	    	System.out.println("Amit1");
	            System.out.println("Call returned a error 404 NotFound resposne ");
	            return true;
	    }
	    return false;
	}
}
