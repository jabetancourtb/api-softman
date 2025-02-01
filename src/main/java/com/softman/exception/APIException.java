package com.softman.exception;

import lombok.Getter;


@Getter
public class APIException extends Exception {

	private static final long serialVersionUID = 1L;	
	
	private Object responseObjectException;
	private Integer responseStatus;
	private Exception exception;
	
	public APIException(Object responseObjectException, Integer responseStatus,	Exception exception){		
		this.responseObjectException = responseObjectException;
		this.responseStatus = responseStatus;
		this.exception = exception;
	}
	
}
