package com.ded.BTS.Exceptions;

public class ProcessingException extends RuntimeException{

	private String businessMessage;
	
	public ProcessingException(String businessMessage, Exception ex) {
		super(ex.getMessage());
		this.businessMessage=businessMessage;
	}
	
	
}
