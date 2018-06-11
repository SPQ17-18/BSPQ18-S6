package es.deusto.spq.ciudades.server;

import java.rmi.server.UnicastRemoteObject;

import org.apache.log4j.Logger;

import es.deusto.spq.ciudades.server.remote.IRemoteFacade;

public class Server extends UnicastRemoteObject implements IRemoteFacade {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(Server.class);

}
