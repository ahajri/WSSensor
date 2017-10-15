package com.ahajri.ws.sensor.temperature.exception.handler;

import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.mortbay.jetty.HttpStatus;

import com.ahajri.ws.sensor.temperature.exception.AuthException;
import com.ahajri.ws.sensor.temperature.exception.RestError;
import com.ahajri.ws.sensor.temperature.exception.RestException;
import com.ahajri.ws.sensor.temperature.utils.SensorUtils;

/**
 * End Point Exception Handler
 * 
 * @author ahajri
 *
 */
@Provider
public class WsExceptionMapper implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable e) {
		RestError error;
		if (e instanceof RestException) {
			RestException ex = (RestException) e;
			error = SensorUtils.response(ex.getHttpStatusCode(), ex.getCode(), ex.getMessage(), ex.getEx().getCause().getMessage(),
					ex.getCause().getMessage());
			return Response.status(ex.getHttpStatusCode()).entity(error).build();
		} else if (e instanceof AuthException) {
			AuthException ex = (AuthException) e;
			error = SensorUtils.response(HttpStatus.ORDINAL_401_Unauthorized, String.valueOf(HttpStatus.ORDINAL_401_Unauthorized),
					ex.getMessage(), "Authentification Obligatoire", "Authentication required");
			return Response.status(HttpStatus.ORDINAL_401_Unauthorized).entity(error).build();
		}else if(e instanceof WebApplicationException){
			WebApplicationException ex = (WebApplicationException)e;
			return ex.getResponse();
		
		}else if(e instanceof NamingException){
			NamingException ex = (NamingException)e;
			error = SensorUtils.response(HttpStatus.ORDINAL_500_Internal_Server_Error, String.valueOf(HttpStatus.ORDINAL_500_Internal_Server_Error),
					 "Problème de Base de données",ex.getMessage(), e.getMessage());
			return Response.status(500).entity(error).build();
		
		}else{
			Map<String, String> errorMap = new HashMap<String, String>();
			errorMap.put("cause", "Problème interne veuillez contacter l'administrateur");
			return Response.status(500).entity(errorMap).build();
		}
		
	}

	
}
