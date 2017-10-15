package com.ahajri.ws.sensor.temperature.bean;

/**
 * 
 * @author ahajri
 * @version 1
 */
public class Mesure implements Comparable<Mesure> {
	private String temperature;
	private String date;

	public Mesure() {
		super();
	}

	public Mesure(String temperature, String date) {
		super();
		this.temperature = temperature;
		this.date = date;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Mesure [temperature=" + temperature + ", date=" + date + "]";
	}

	@Override
	public int compareTo(Mesure o) {
		return this.date.compareTo(o.getDate());
	}

}
