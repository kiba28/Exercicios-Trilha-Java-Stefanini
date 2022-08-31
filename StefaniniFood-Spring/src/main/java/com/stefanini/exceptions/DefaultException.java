package com.stefanini.exceptions;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DefaultException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private final Integer status;
	private final String error;
	private final String message;

	public DefaultException(Integer status, String error, String message) {
		super();
		this.status = status;
		this.error = error;
		this.message = message;
	}

}
