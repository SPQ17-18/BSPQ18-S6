package es.deusto.spq.ciudades.client.controller;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import es.deusto.spq.ciudades.client.remote.CiudadesServiceLocator;

public class CiudadesController {
	
	final static Logger logger = Logger.getLogger(CiudadesController.class);
	
	private CiudadesServiceLocator csl;
	
	public CiudadesController(String[] args) throws RemoteException{
		csl = new CiudadesServiceLocator();
		csl.setServices(args[0], args[1], args[2]);
	}

}
