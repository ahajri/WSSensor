package com.ahajri.ws.sensor.temperature.exception;

/**
 * Exception commune des Facades REST
 * 
 * @author ahajri
 * @version 1
 */
public class RestException extends Throwable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3832425987434445458L;

	private int httpStatusCode;
	private String code;
	private Exception ex;

	public RestException(String message, Exception ex, int httpStatusCode) {
		super(message, ex.getCause());
		System.out.println(httpStatusCode);
		this.httpStatusCode = httpStatusCode;
		this.ex = ex;

	}

	public Exception getEx() {
		return ex;
	}

	public void setEx(Exception ex) {
		this.ex = ex;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "RestException [httpStatusCode=" + httpStatusCode + ", code=" + code + ", ex=" + ex + "]";
	}

}
