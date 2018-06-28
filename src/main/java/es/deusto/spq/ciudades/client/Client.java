package es.deusto.spq.ciudades.client;

import org.apache.log4j.Logger;

import es.deusto.spq.ciudades.client.gui.VentanLogin;


public class Client {

	final static Logger logger = Logger.getLogger(Client.class);
	
	public static void main(String[] args) {
		
		if(args.length !=5) {
			logger.error("Use: java [policy] [codebase] Client.Client [host] [port] [server] [language] [country]");
			System.exit(0);
		}
		
		if(System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
	
		final VentanLogin vInicio = new VentanLogin(args);
		vInicio.centreWindow();
		vInicio.setVisible(true);
		
		
	}
}
