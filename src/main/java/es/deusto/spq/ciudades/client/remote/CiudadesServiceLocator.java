package es.deusto.spq.ciudades.client.remote;

import org.apache.log4j.Logger;

import es.deusto.spq.ciudades.server.remote.IRemoteFacade;

public class CiudadesServiceLocator {

	final static Logger logger = Logger.getLogger(CiudadesServiceLocator.class);
	
	private IRemoteFacade ciudadesManagerService;
	
	/**
	 * Clase constructora
	 */
	public CiudadesServiceLocator() {
		
	}
	
	/**
	 * Establece la conexion con el servidor.
	 * @param args0 IP
	 * @param args1 Port
	 * @param args2 Name
	 */
	public void setServices(String args0, String args1, String args2) {
		String service = "//" + args0 + ":" + args1 + "/" + args2;
		try {
			ciudadesManagerService = (IRemoteFacade) java.rmi.Naming.lookup(service);
			logger.info("Server OK: " + service);
		} catch (Exception e) {
			logger.error("Error trying to set Server: " + service);
		}
	}

	/**
	 * Obtiene la Remote Facade para comunicarse con el servidor
	 * @return Remote Facade
	 */
	public IRemoteFacade getService() {
		return ciudadesManagerService;
	}


}
