package com.ahajri.ws.sensor.temperature.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Exception where sensor not found
 * 
 * @author ahajri
 *
 */
public class SensorNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4828559441855900689L;

	public SensorNotFoundException(Class<?> clazz, Exception exception) {
		super( exception);
	}

	public SensorNotFoundException(Class<?> clazz, Exception exception, String errorCode, String attribute) {
		//super(clazz, exception, errorCode, attribute);
	}

	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	//@Override
	protected void init(Class<?> clazz, Throwable throwable, String errorCode, String... attribut) {
		Logger log = LoggerFactory.getLogger(getClass());
		if (clazz != null) {
			log = LoggerFactory.getLogger(clazz);
		}
//		setOriginException((Exception) throwable);
//		setErrorCode(errorCode);
//		setAttribut(attribut);
		log.error(attribut[0], (Exception) throwable);

	}

}
