package es.deusto.spq.ciudades.client;

import org.apache.log4j.Logger;
import es.deusto.spq.ciudades.client.gui.VentanaInicioSesion;

public class Client {

	final static Logger logger = Logger.getLogger(Client.class);
	
	public static void main(String[] args) {
		
		if(args.length !=3) {
			logger.error("Use: java [policy] [codebase] Client.Client [host] [port] [server]");
			System.exit(0);
		}
		
		if(System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
	
		final VentanaInicioSesion vInicio = new VentanaInicioSesion();
		
		
	}
}