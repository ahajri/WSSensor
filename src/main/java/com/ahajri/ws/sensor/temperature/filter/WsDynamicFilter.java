package com.ahajri.ws.sensor.temperature.filter;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

import com.ahajri.ws.sensor.temperature.annotation.Authenticated;

/**
 * 
 * @author ahajri
 *
 */
@Provider
public class WsDynamicFilter implements DynamicFeature {

	@Override
	public void configure(ResourceInfo resInfo, FeatureContext ctx) {
		if (resInfo.getResourceMethod().isAnnotationPresent(Authenticated.class)) {
			ctx.register(AuthFilter.class);
			ctx.register(LoggingFilter.class);
		}
	}

}
