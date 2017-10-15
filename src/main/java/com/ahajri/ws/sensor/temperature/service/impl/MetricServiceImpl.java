package com.ahajri.ws.sensor.temperature.service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.ejb.Local;
import javax.ejb.Stateless;

import com.ahajri.ws.sensor.temperature.service.MetricService;
/**
 * 
 * @author ahajri
 *
 */
@Stateless(name = "metricService")
@Local(MetricService.class)
public class MetricServiceImpl implements MetricService {
	
	private ConcurrentMap<String, ConcurrentHashMap<String, Integer>> metricMap = new ConcurrentHashMap<>();

	/*
	 * (non-Javadoc)
	 * @see com.ahajri.ws.sensor.temperature.service.MetricService#increaseCount(java.lang.String, int)
	 */
	@Override
	public void increaseCount(String request, int status) {
		ConcurrentHashMap<String, Integer> statusMap = metricMap.get(request);
		if (statusMap == null) {
			statusMap = new ConcurrentHashMap<String, Integer>();
		}

		Integer count = statusMap.get(String.valueOf(status));
		if (count == null) {
			count = 1;
		} else {
			count++;
		}
		statusMap.put(String.valueOf(status), count);
		metricMap.put(request, statusMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ahajri.ws.sensor.temperature.service.MetricService#getFullMetric()
	 */
	@Override
	public Map<String, ConcurrentHashMap<String, Integer>> getFullMetric() {
		return metricMap;
	}
}
