package com.ahajri.ws.sensor.temperature.service.impl;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.ahajri.ws.sensor.temperature.service.SessionBeanInvoker;
import com.ejb.core.db.service.MongoService;

/**
 * 
 * @author ahajri
 *
 */
@Stateful
@Local(SessionBeanInvoker.class)
public class SessionBeanInvokerImpl implements SessionBeanInvoker {

	private Context context;

	/**
	 * Just for test jar-client
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// System.out.println(System.getProperty("user.dir"));
		final Hashtable<String, Object> jndiProperties = new Hashtable<>();
		jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		// jndiProperties.put(Context.PROVIDER_URL,
		// "http-remote://localhost:8080");
		// HTTP transport
		jndiProperties.put(Context.PROVIDER_URL, "remote+http://localhost:8080");
		jndiProperties.put("jboss.naming.client.ejb.context", true);
		Context context = new InitialContext(jndiProperties);
		String lookupName = "ejb:/DBCore-jar-with-dependencies/mongoService!" + MongoService.class.getName() + "?stateless";
		System.out.println(lookupName);
		MongoService mongoService = (MongoService) context.lookup(lookupName);
		// "ejb:/HBaseCore/hbase-ejb-client-1.0.0!com.ahajri.ws.core.hbase.ejb.MongoService?statelass"
		System.out.println(mongoService.testRemote());
		Map<String, Object> post = new HashMap<>();
		post.put("NAME", "DUMMY DATA => 2500 EUR");
		mongoService.insertOne("ArticleCollection", post);
		System.out.println(mongoService.findInCollection("ArticleCollection", new HashMap<>()));
		mongoService.deleteOne("ArticleCollection", post);
	}

	/**
	 * 
	 * @throws NamingException
	 */
	public SessionBeanInvokerImpl() throws NamingException {
		super();
		final Hashtable<String, Object> jndiProperties = new Hashtable<>();
		jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		// jndiProperties.put(Context.PROVIDER_URL,
		// "http-remote://localhost:8080");
		// HTTP transport
		jndiProperties.put(Context.PROVIDER_URL, "remote+http://localhost:8080");
		jndiProperties.put("jboss.naming.client.ejb.context", true);
		context = new InitialContext(jndiProperties);
	}

	/**
	 * Serialization Version UID
	 */
	private static final long serialVersionUID = -961561931970464670L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.edf.dco.webservices.temperature.service.SessionBeanInvoker#
	 * invokeMongoService()
	 */
	@Override
	public MongoService invokeMongoService() throws NamingException {
		String lookupName = "ejb:/DBCore/mongoService!" + MongoService.class.getName() + "?stateless";
		return (MongoService) context.lookup(lookupName);
	}

}
