package es.deusto.spq.ciudades.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

import es.deusto.spq.ciudades.server.remote.IRemoteFacade;

public class Server {

	private static Thread rmiRegistryThread = null;
	private static Thread rmiServerThread = null;

	public static void main(final String[] args) {
		// if (args.length != 3) {
		// System.exit(0);
		// }
		//
		// if (System.getSecurityManager() == null) {
		// System.setSecurityManager(new SecurityManager());
		// }
		//
		// String name = "//" + args[0] + ":" + args[1] + "/" + args[2];
		//
		// try {
		// IRemoteFacade server = new Server();
		// Naming.rebind(name, server);
		// logger.info("Server '" + name + "' active and waiting...");
		// java.io.InputStreamReader inputStreamReader = new
		// java.io.InputStreamReader(System.in);
		// java.io.BufferedReader stdin = new java.io.BufferedReader(inputStreamReader);
		// @SuppressWarnings("unused")
		// String line = stdin.readLine();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		// Launch the RMI registry
		class RMIRegistryRunnable implements Runnable {

			public void run() {
				try {
					java.rmi.registry.LocateRegistry.createRegistry(1099);
				} catch (Exception e) {
					// e.printStackTrace();
				}
			}
		}

		rmiRegistryThread = new Thread(new RMIRegistryRunnable());
		rmiRegistryThread.start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}

		class RMIServerRunnable implements Runnable {

			public void run() {
				System.setProperty("java.security.policy", "target\\classes\\security\\java.policy");

				if (System.getSecurityManager() == null) {
					System.setSecurityManager(new SecurityManager());
				}

				String name = "//" + args[0] + ":" + args[1] + "/" + args[2];

				try {
					IRemoteFacade collector = new Collector();
					((Collector) collector).iniJFrame();
					Naming.rebind(name, collector);

				} catch (RemoteException re) {
					System.exit(-1);
				} catch (MalformedURLException murle) {
					System.exit(-1);
				}
			}
		}

		rmiServerThread = new Thread(new RMIServerRunnable());
		rmiServerThread.start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException ie) {
		}
	}

}
