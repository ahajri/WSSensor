package com.ahajri.ws.sensor.temperature.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author ahajri
 * @version 1
 */
public class Releves {

	private List<Mesure> mesures = new ArrayList<>();

	private String errorCode;

	private String errorLabel;

	private String code;

	private String status;

	public Releves() {
		super();
	}

	public Releves(List<Mesure> mesures, String errorCode, String errorLabel) {
		super();
		this.mesures = mesures;
		this.errorCode = errorCode;
		this.errorLabel = errorLabel;
	}

	public List<Mesure> getMesures() {
		return mesures;
	}

	public void setMesures(List<Mesure> mesures) {
		this.mesures.clear();
		this.mesures.addAll(mesures);
	}

	public void addMesure(Mesure mesure) {
		this.mesures.add(mesure);
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
		return "Releves [mesures=" + mesures + ", errorCode=" + errorCode + ", errorLabel=" + errorLabel + ", code="
				+ code + ", status=" + status + "]";
	}

}
