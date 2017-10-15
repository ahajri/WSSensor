package com.ahajri.ws.sensor.temperature.interceptor;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;

import com.ahajri.ws.sensor.temperature.utils.WsConstants;

/**
 * 
 * @author ahajri
 *
 */
@Priority(value = 2000)
@Provider
public class WsReaderInterceptor implements ReaderInterceptor {

	@Override
	public Object aroundReadFrom(ReaderInterceptorContext context) throws IOException, WebApplicationException {
		context.setProperty(WsConstants.START_TIME, System.currentTimeMillis());
		return context.proceed();
	}

}
