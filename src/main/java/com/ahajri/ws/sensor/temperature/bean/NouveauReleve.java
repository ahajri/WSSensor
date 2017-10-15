package com.ahajri.ws.sensor.temperature.bean;
/**
 * 
 * @author ahajri
 * @version 1
 */
public class NouveauReleve {
	private String hwaddr;
	private String temperature;
	private String timestamp;

	public NouveauReleve() {
		super();
	}

	public NouveauReleve(String hwaddr, String temperature, String timestamp) {
		super();
		this.hwaddr = hwaddr;
		this.temperature = temperature;
		this.timestamp = timestamp;
	}

	public String getHwaddr() {
		return hwaddr;
	}

	public void setHwaddr(String hwaddr) {
		this.hwaddr = hwaddr;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "NouveauReleve [hwaddr=" + hwaddr + ", temperature=" + temperature + ", timestamp="
				+ timestamp + "]";
	}

}
