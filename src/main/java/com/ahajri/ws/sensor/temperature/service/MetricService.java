package com.ahajri.ws.sensor.temperature.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author ahajri
 *
 */
public interface MetricService {
	/**
	 * 
	 * @param request
	 * @param status
	 */
	public void increaseCount(String request, int status) ;

	/**
	 * 
	 * @return
	 */
	public Map<String, ConcurrentHashMap<String, Integer>> getFullMetric() ;
}
