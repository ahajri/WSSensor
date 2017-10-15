package com.ahajri.ws.sensor.temperature.exception;

import java.io.Serializable;

/**
 * Classe du message d'erreur REST
 * 
 * @author ahajri
 * @version 1
 */
public class RestError implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9054040415035607804L;

	private int status;
	private String msg, devMsg, moreInfo,code;

	public RestError(int status, String code, String msg, String devMsg, String moreInfo) {
		super();
		this.status = status;
		this.code = code;
		this.msg = msg;
		this.devMsg = devMsg;
		this.moreInfo = moreInfo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getDevMsg() {
		return devMsg;
	}

	public void setDevMsg(String devMsg) {
		this.devMsg = devMsg;
	}

	public String getMoreInfo() {
		return moreInfo;
	}

	public void setMoreInfo(String moreInfo) {
		this.moreInfo = moreInfo;
	}

	@Override
	public String toString() {
		return "RestError [status=" + status + ", code=" + code + ", msg=" + msg + ", devMsg=" + devMsg + ", moreInfo="
				+ moreInfo + "]";
	}

}
