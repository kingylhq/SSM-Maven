package com.lq.util.company;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 3341180093581777371L;

	public BusinessException() {
		super();
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

}
