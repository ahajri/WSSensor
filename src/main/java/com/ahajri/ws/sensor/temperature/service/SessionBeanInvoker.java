package com.ahajri.ws.sensor.temperature.service;

import java.io.Serializable;

import javax.naming.NamingException;

import com.ejb.core.db.service.MongoService;

/**
 * Session Bean Invoker
 * 
 * @author ahajri
 *
 */
public interface SessionBeanInvoker extends Serializable {

	public MongoService invokeMongoService() throws NamingException;

}
