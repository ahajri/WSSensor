package com.ahajri.ws.sensor.temperature.bean;

/**
 * 
 * @author ahajri
 * @version 1
 */
public class DernierReleve {

	private Mesure mesure;

	private String errorCode;

	private String errorLabel;

	private String code;

	private String status;

	public DernierReleve() {
		super();
	}

	public DernierReleve(Mesure mesure, String errorCode, String errorLabel) {
		super();
		this.mesure = mesure;
		this.errorCode = errorCode;
		this.errorLabel = errorLabel;
	}

	public Mesure getMesure() {
		return mesure;
	}

	public void setMesure(Mesure mesure) {
		this.mesure = mesure;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorLabel() {
		return errorLabel;
	}

	public void setErrorLabel(String errorLabel) {
		this.errorLabel = errorLabel;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "DernierReleve [mesure=" + mesure.toString() + ", errorCode=" + errorCode + ", errorLabel=" + errorLabel
				+ ", code=" + code + ", status=" + status + "]";
	}

}
