package es.deusto.spq.ciudades.client;

import java.rmi.Naming;

import org.apache.log4j.Logger;

import es.deusto.spq.ciudades.client.gui.VentanLogin;
import es.deusto.spq.ciudades.server.remote.IRemoteFacade;

/**
 * 
 * En esta clase cliente lanzamos la ejecucion del cliente 
 * 
 *
 */
public class Client {

	final static Logger logger = Logger.getLogger(Client.class);
	private IRemoteFacade collector;

	public static void main(String[] args) {

		if (args.length != 5) {
			logger.error("Use: java [policy] [codebase] Client.Client [host] [port] [server] [language] [country]");
			System.exit(0);
		}

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		Client cliente = new Client();
		cliente.conectarConFachada(args);

		VentanLogin vInicio = new VentanLogin(args, cliente.getCollector());
		vInicio.setVisible(true);

	}

	public void conectarConFachada(String[] args) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
			String URL = "//" + args[0] + ":" + args[1] + "/" + args[2];
			this.setCollector((IRemoteFacade) Naming.lookup(URL));
		} catch (Exception e) {

		}
	}

	public IRemoteFacade getCollector() {
		return collector;
	}

	public void setCollector(IRemoteFacade collector) {
		this.collector = collector;
	}
}
