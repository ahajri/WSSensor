package com.ahajri.ws.sensor.temperature.filter;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Date;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ahajri.ws.sensor.temperature.exception.RestError;
import com.ahajri.ws.sensor.temperature.service.MetricService;
import com.ahajri.ws.sensor.temperature.utils.WsConstants;
import com.ahajri.ws.sensor.temperature.utils.SensorUtils;

/**
 * 
 * @author ahajri
 *
 */
@Provider
@Priority(value = 3000)
public class LoggingFilter implements ContainerResponseFilter {

	/** Wildfly Logger */
	private final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

	@Inject
	private MetricService metricService;

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {

		long startTime = (Long) requestContext.getProperty(WsConstants.START_TIME);
		long execDuration = System.currentTimeMillis() - startTime;

		Object backendResponse = responseContext.getEntity();

		if (backendResponse != null && (backendResponse instanceof RestError)) {
			RestError error =(RestError)backendResponse;
			String moreInfo = error.getMoreInfo();
			metricService.increaseCount(moreInfo, error.getStatus());
			return;// Be aware to not interrupt exception handler
		}
		Date date = responseContext.getDate();

		int httpStatus = responseContext.getStatus();

		if (date == null) {
			date = new Date();
		}

		// Manage metrics
		Annotation[] annotations = responseContext.getEntityAnnotations();

		Class<?> methodClass = annotations[0].annotationType();
		Path annoPath = (Path) annotations[1];
		String path = annoPath.value();
		if (methodClass.equals(GET.class) && path.contains("/temperature") && !path.contains("metric")) {
			metricService.increaseCount("GET:" + path, httpStatus);
		} else if (methodClass.equals(POST.class) && path.contains("/temperature")) {
			metricService.increaseCount("POST:" + path, httpStatus);
		}

		logger.info(SensorUtils.formatDate(date, WsConstants.LOG_DATE_PATTERBN) + " ===>request duration "
				+ execDuration + " ms, status: " + httpStatus);

	}
}
