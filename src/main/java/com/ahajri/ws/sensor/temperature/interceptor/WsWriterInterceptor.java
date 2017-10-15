package com.ahajri.ws.sensor.temperature.interceptor;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author ahajri
 *
 */
@Priority(value = 2000)
@Provider
public class WsWriterInterceptor implements WriterInterceptor {

	private final Logger logger = LoggerFactory.getLogger(WsWriterInterceptor.class);

	@Override
	public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {

		context.proceed();
	}

}
