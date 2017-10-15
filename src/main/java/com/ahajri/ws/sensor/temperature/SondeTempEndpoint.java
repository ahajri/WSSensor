package com.ahajri.ws.sensor.temperature;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.naming.NamingException;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.mortbay.jetty.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ahajri.ws.sensor.temperature.annotation.Authenticated;
import com.ahajri.ws.sensor.temperature.bean.DernierReleve;
import com.ahajri.ws.sensor.temperature.bean.NouveauReleve;
import com.ahajri.ws.sensor.temperature.bean.Releves;
import com.ahajri.ws.sensor.temperature.exception.RestException;
import com.ahajri.ws.sensor.temperature.service.MetricService;
import com.ahajri.ws.sensor.temperature.service.SessionBeanInvoker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/**
 * 
 * @author ahajri
 *
 */
@Path("/sonde/api/v1")
public class SondeTempEndpoint {

	/** Logger */
	private static Logger logger = LoggerFactory.getLogger(SondeTempEndpoint.class);

	@Inject
	private MetricService metricService;

	@Inject
	private SessionBeanInvoker sessionBeanInvoker;

	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@POST
	@Path("/temperature")
	@Consumes(MediaType.APPLICATION_JSON)
	@Authenticated
	public Response doPostTemperature(@NotNull List<NouveauReleve> nouveauxReleves) throws RestException {
		logger.debug("*****************************************************************");
		logger.debug(" Stockage d'un tableau de releves de temperature ... " + gson.toJson(nouveauxReleves));
		logger.debug("*****************************************************************");
		return Response.ok().build();
	}

	@GET
	@Path("/temperature")
	@Produces(MediaType.APPLICATION_JSON)
	@Authenticated
	public Response doGetTemperatureByPeriod(@NotNull @QueryParam(value = "resourceID") String resourceID,
			@NotNull @QueryParam(value = "debut") String debut, @NotNull @QueryParam(value = "fin") String fin)
			throws RestException {

		return Response.ok().build();
	}

	@GET
	@Path("/temperature/{resourceID}/dernier")
	@Produces(MediaType.APPLICATION_JSON)
	@Authenticated
	public Response doGetTemperatureDernier(@NotNull @PathParam("resourceID") String resourceID) throws RestException {
		logger.debug("*****************************************************************************");
		logger.debug("Recherche du dernier releve de temperature disponible la sonde  " + resourceID);
		logger.debug("*****************************************************************************");
		DernierReleve dernierReleve = new DernierReleve();

		return Response.ok().entity(dernierReleve).build();
	}

	@GET
	@Path("/temperature/{resourceID}/{pathID}")
	@Produces(MediaType.APPLICATION_JSON)
	@Authenticated
	public Response doGetTemperaturePathID(@Context SecurityContext sc,
			@NotNull @PathParam("resourceID") final String resourceID,
			@NotNull @PathParam("pathID") final String pathID) throws RestException {
		logger.debug("*****************************************************************************");
		logger.debug("Recherche des releve de temperature disponible la sonde  " + resourceID + ", jour ou semaine: "
				+ pathID);
		logger.debug("*****************************************************************************");
		Releves releves = new Releves();
		return Response.ok().entity(releves).build();
	}

	@GET
	@Path(value = "/temperature/metric")
	@Produces(MediaType.APPLICATION_JSON)
	public Response doGetMetric() throws RestException {
		return Response.ok().entity(metricService.getFullMetric()).build();
	}

	@GET
	@Path(value = "/temperature/pingHBase")
	@Produces(MediaType.APPLICATION_JSON)
	@Authenticated
	public Response doGetHBase(@Context SecurityContext sc) throws RestException {
		String testRemote = null;
		try {
			testRemote = sessionBeanInvoker.invokeMongoService().testRemote();
		} catch (EJBException | NamingException ex) {
			throw new RestException(ex.getMessage(), ex, HttpStatus.ORDINAL_500_Internal_Server_Error);
		}
		Map<String, String> helloMap = new HashMap<>();
		helloMap.put("EJB-ANSWER: ", testRemote);
		return Response.ok().entity(helloMap).build();
	}
}