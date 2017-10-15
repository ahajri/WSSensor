package com.ahajri.ws.sensor.temperature.filter;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.relation.Role;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import org.mortbay.jetty.HttpStatus;
import org.picketbox.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ahajri.ws.sensor.temperature.exception.AuthException;
import com.ahajri.ws.sensor.temperature.exception.RestError;
import com.ahajri.ws.sensor.temperature.utils.BasicAuth;
import com.ahajri.ws.sensor.temperature.utils.WsConstants;
import com.ahajri.ws.sensor.temperature.utils.SensorUtils;
import com.sun.jersey.core.util.Priority;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter {
	
	/** Wildfly Logger */
	private final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

		String subject = validateAuthorization(authHeader); // execute custom
		requestContext.setProperty(WsConstants.START_TIME, System.currentTimeMillis());
		String methodName = requestContext.getMethod();
		String path = requestContext.getUriInfo().getPath().replace("/sonde/api/v1", "");
		String moreInfo = methodName+":"+path;
		if (!requestContext.getSecurityContext().isSecure()) {
			//Forcing HTTPS
			RestError error = SensorUtils.response(HttpStatus.ORDINAL_403_Forbidden,
					String.valueOf(HttpStatus.ORDINAL_403_Forbidden), "HTTPS Obligatiore", "HTTPS Required",
					moreInfo);
			Response response = Response.status(HttpStatus.ORDINAL_403_Forbidden).entity(error).build();
			throw new WebApplicationException(response);
		}
		if (!StringUtil.isNullOrEmpty(subject)) {

			requestContext.setSecurityContext(new SecurityContext() {

				@Override
				public Principal getUserPrincipal() {
					return new Principal() {
						@Override
						public String getName() {
							return subject;
						}
					};
				}

				@Override
				public boolean isUserInRole(String role) {
					List<Role> roles = Collections.emptyList();
					try {
						roles = findUserRoles(subject);
					} catch (MalformedObjectNameException e) {
						logger.error("Could not fin roles: " + e.getMessage());
					}
					return roles.contains(role);
				}

				@Override
				public boolean isSecure() {
					return requestContext.getUriInfo().getAbsolutePath().toString().startsWith("https");
				}

				@Override
				public String getAuthenticationScheme() {
					return requestContext.getUriInfo().getRequestUri().getScheme();
				}

			});

		} else {
			throw new AuthException(HttpStatus.Unauthorized, new NotAuthorizedException(Status.UNAUTHORIZED));
		}

	}

	/**
	 * 
	 * @param subject
	 * @return {@link List} role
	 * @throws MalformedObjectNameException
	 */
	private List<Role> findUserRoles(String subject) throws MalformedObjectNameException {
		// FIXME: replace with third party service methord
		List<ObjectName> roleValues = Arrays.asList(new ObjectName("XML-USER"), new ObjectName("JSON-USER"));
		Role authRole = new Role("AUTH_ROLE", roleValues);
		Role mgmtRole = new Role("MGMT_ROLE", roleValues);
		Role appRole = new Role("APP_ROLE", roleValues);
		return Arrays.asList(authRole, mgmtRole, appRole);
	}

	/**
	 * 
	 * @param authHeaderVal
	 * @return
	 */
	private String validateAuthorization(String authHeaderVal) {
		try {
			return BasicAuth.decode(authHeaderVal)[0];
			// FIXME: verify in HBase
		} catch (Exception e) {
			return null;
		}
	}

}
