package com.ramiro.poclayoutcomprovante.service;

public class ServiceBindException extends Exception {

	public ServiceBindException(String message) {
        super(message);
    }
	
	public ServiceBindException(String message, Throwable throwable) {
        super(message);
        initCause(throwable);
    }
	
}
