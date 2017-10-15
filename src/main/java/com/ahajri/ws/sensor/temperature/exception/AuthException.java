package com.ahajri.ws.sensor.temperature.exception;

import java.io.IOException;

import javax.ws.rs.NotAuthorizedException;
/**
 * 
 * @author ahajri
 *
 */
public class AuthException extends IOException {

	public AuthException(String msg, NotAuthorizedException cause) {
		super(msg,cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8913481097517694664L;

}
