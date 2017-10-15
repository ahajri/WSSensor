package com.ahajri.ws.sensor.temperature.config;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.ahajri.ws.sensor.temperature.SondeTempEndpoint;
import com.ahajri.ws.sensor.temperature.exception.handler.WsExceptionMapper;
import com.ahajri.ws.sensor.temperature.filter.WsDynamicFilter;
import com.ahajri.ws.sensor.temperature.interceptor.WsReaderInterceptor;
import com.ahajri.ws.sensor.temperature.interceptor.WsWriterInterceptor;

@ApplicationPath("/*")
public class AppSensor extends Application {

	private Set<Class<?>> classes = new HashSet<Class<?>>();
	private Set<Object> singletons = new HashSet<Object>();

	public AppSensor() {
		super();
		classes.add(SondeTempEndpoint.class);
		classes.add(WsDynamicFilter.class);
		classes.add(WsExceptionMapper.class);
		classes.add(WsReaderInterceptor.class);
		classes.add(WsWriterInterceptor.class);
	}

	@Override
	public Map<String, Object> getProperties() {
		return super.getProperties();
	}

	@Override
	public Set<Class<?>> getClasses() {
		return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

}
