package es.deusto.spq.ciudades.client.controller;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import com.mysql.fabric.Server;

import es.deusto.spq.ciudades.client.Client;
import es.deusto.spq.ciudades.server.Collector;
import es.deusto.spq.ciudades.server.jdo.DAO.IManagerDAO;
import es.deusto.spq.ciudades.server.jdo.DAO.ManagerDAO;
import es.deusto.spq.ciudades.server.jdo.data.Ciudad;
import es.deusto.spq.ciudades.server.jdo.data.CiudadDTO;
import es.deusto.spq.ciudades.server.jdo.data.Usuario;
import es.deusto.spq.ciudades.server.jdo.data.UsuarioDTO;
import es.deusto.spq.ciudades.server.remote.IRemoteFacade;
import junit.framework.JUnit4TestAdapter;

/**
 * SEGUNDO SE EJECUTARÁ ESTA CLASE PARA COMPROBAR EL FUNCIONAMIENTO DE LOS
 * MÉTODOS DEL CLIENTE Y SERVIDOR JUNTO CON LAS CONSULTAS HECHAS A LA BASE DE
 * DATOS
 * 
 *
 */
// @Ignore
public class CiudadesControllerTest {

	private static final Logger logger = Logger.getLogger(CiudadesControllerTest.class);

	@Rule
	public ContiPerfRule contiPerfRule = new ContiPerfRule();

	private String[] arg = { "127.0.0.1", "1099", "CiudadesControllerTest" };
	private static String cwd = CiudadesControllerTest.class.getProtectionDomain().getCodeSource().getLocation()
			.getFile();
	private static Thread rmiRegistryThread = null;
	private static Thread rmiServerThread = null;

	private CiudadesController controller;
	private static IManagerDAO managerDAO;

	private static IRemoteFacade collector;

	private Usuario u1;

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(CiudadesControllerTest.class);
	}

	/**
	 * Comprobado servidor, funcina correctamente.
	 */
	@BeforeClass
	public static void setUpClass() {
		// Launch the RMI registry
		class RMIRegistryRunnable implements Runnable {
			public void run() {
				try {
					java.rmi.registry.LocateRegistry.createRegistry(1099);
					logger.info("BeforeClass: RMI registry ready.");
				} catch (Exception e) {
					logger.error("Exception starting RMI registry:");
					e.printStackTrace();
				}
			}
		}

		rmiRegistryThread = new Thread(new RMIRegistryRunnable());
		rmiRegistryThread.start();
		try {
			Thread.sleep(8888);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}

		// Launch the server
		class RMIServerRunnable implements Runnable {
			public void run() {
				System.setProperty("java.rmi.server.codebase", "file:" + cwd);
				System.setProperty("java.security.policy", "target\\classes\\security\\java.policy");

				if (System.getSecurityManager() == null) {
					System.setSecurityManager(new SecurityManager());
				}

				String name = "//127.0.0.1:1099/CiudadesControllerTest";
				logger.info("BeforeClass - Setting the server ready name: " + name);

				try {
					collector = new Collector();
					((Collector) collector).iniJFrame();
					Naming.rebind(name, collector);

				} catch (RemoteException re) {
					logger.error(" # Server RemoteException: " + re.getMessage());
					re.printStackTrace();
					System.exit(-1);
				} catch (MalformedURLException murle) {
					logger.error(" # Server MalformedURLException: " + murle.getMessage());
					murle.printStackTrace();
					System.exit(-1);
				}
			}
		}

		rmiServerThread = new Thread(new RMIServerRunnable());
		rmiServerThread.start();
		try {
			Thread.sleep(8888);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}

	/**
	 * Comprobado cliente, funciona bien.
	 */
	private static Client cliente;

	@Test
	@PerfTest(invocations = 1, threads = 1)
	public void test1() {
		// Launch the client
		try {
			System.setProperty("java.security.policy", "target\\classes\\security\\java.policy");
			if (System.getSecurityManager() == null) {
				System.setSecurityManager(new SecurityManager());
			}

			cliente = new Client();
			cliente.conectarConFachada(this.arg);

			if (System.getSecurityManager() == null) {
				System.setSecurityManager(new SecurityManager());
			}

			try {
				if (cliente.getCollector() != null) {
					setController(new CiudadesController(cliente.getCollector()));
				} else {
					logger.error("COLLECTOR test1, COLLECTOR es nulo!");
				}
			} catch (Exception e) {
				logger.error(" # Client RemoteException: " + e.getMessage());
			}
		} catch (Exception re) {
			logger.error(" # Client RemoteException: " + re.getMessage());
			System.exit(-1);
		}
	}

	/**
	 * Test para crear datos e insertarlos en la BD: funciona bien:
	 */
	@Test
	public void test2() {

		// Base de datos inicial para todos los tests

		managerDAO = new ManagerDAO();

		// Rellenar la bd

		u1 = new Usuario("cristian@opendeusto.es", "Cristian", "Perez", "1234");
		Usuario u2 = new Usuario("jesus@opendeusto.es", "Jesus", "de la Pisa", "qwerty");
		Usuario u4 = new Usuario("jon@opendeusto.es", "Jon", "Alonso", "2222");
		Usuario u5 = new Usuario("xabi@opendeusto.es", "Xabier", "Santos", "22aa");
		Usuario u6 = new Usuario("mar@opendeusto.es", "Mar", "Abando", "44bb");
		Usuario u7 = new Usuario("luis@opendeusto.es", "Luis", "Arribas", "cc33");
		Usuario u8 = new Usuario("javi@opendeusto.es", "Javier", "Lorenzo", "wwcc");
		Usuario u9 = new Usuario("pablo@opendeusto.es", "Pablo", "Diez", "2233");
		Usuario u10 = new Usuario("laura@opendeusto.es", "Laura", "Moreno", "lm93");
		Usuario u11 = new Usuario("martina@opendeusto.es", "Martina", "Echaniz", "me23");
		Usuario u12 = new Usuario("unai@opendeusto.es", "Unai", "Vitxo", "uve2");
		Usuario u13 = new Usuario("oscar@opendeusto.es", "Oscar", "Rodriguez", "or22");
		Usuario u14 = new Usuario("andoni@opendeusto.es", "Andoni", "Vicentiz", "av11");
		Usuario u15 = new Usuario("jonh@opendeusto.es", "Jon", "Hernandez", "jhrb");
		Usuario u16 = new Usuario("paula@opendeusto.es", "Paula", "Aspiunza", "pa35");
		Usuario u17 = new Usuario("belen@opendeusto.es", "Belen", "Abando", "ba23");
		Usuario u18 = new Usuario("patricia@opendeusto.es", "Patricia", "Ballano", "pbb1");
		Usuario u19 = new Usuario("sara@opendeusto.es", "Sara", "Arroyo", "samt");
		Usuario u20 = new Usuario("lucia@opendeusto.es", "Lucia", "Garay", "lg96");

		Usuario u3 = new Usuario("admin@opendeusto.es", "admin", "fasdfasd", "admin");

		Ciudad c1 = new Ciudad("Madrid", "Espania", 0, 0, 0, 0, 0, 0);
		Ciudad c2 = new Ciudad("Paris", "Francia", 0, 0, 0, 0, 0, 0);
		Ciudad c3 = new Ciudad("Monaco", "Monaco", 0, 0, 0, 0, 0, 0);
		Ciudad c4 = new Ciudad("Barcelona", "Espania", 0, 0, 0, 0, 0, 0);
		Ciudad c5 = new Ciudad("Bilbao", "Espania", 0, 0, 0, 0, 0, 0);
		Ciudad c6 = new Ciudad("Lugo", "Espania", 0, 0, 0, 0, 0, 0);
		Ciudad c7 = new Ciudad("Valencia", "Espania", 0, 0, 0, 0, 0, 0);
		Ciudad c8 = new Ciudad("Londres", "Reino Unido", 0, 0, 0, 0, 0, 0);
		Ciudad c9 = new Ciudad("Roma", "Italia", 0, 0, 0, 0, 0, 0);
		Ciudad c10 = new Ciudad("Lisboa", "Portugal", 0, 0, 0, 0, 0, 0);
		Ciudad c11 = new Ciudad("Venecia", "Italia", 0, 0, 0, 0, 0, 0);
		Ciudad c12 = new Ciudad("Budapest", "Hungria", 0, 0, 0, 0, 0, 0);
		Ciudad c13 = new Ciudad("Praga", "Republica Checa", 0, 0, 0, 0, 0, 0);
		Ciudad c14 = new Ciudad("Atenas", "Grecia", 0, 0, 0, 0, 0, 0);
		Ciudad c15 = new Ciudad("Estambul", "Turquia", 0, 0, 0, 0, 0, 0);
		Ciudad c16 = new Ciudad("Viena", "Austria", 0, 0, 0, 0, 0, 0);
		Ciudad c17 = new Ciudad("Salzburgo", "Austria", 0, 0, 0, 0, 0, 0);
		Ciudad c18 = new Ciudad("Florencia", "Italia", 0, 0, 0, 0, 0, 0);
		Ciudad c19 = new Ciudad("Cracovia", "Polonia", 0, 0, 0, 0, 0, 0);
		Ciudad c20 = new Ciudad("Estocolmo", "Suecia", 0, 0, 0, 0, 0, 0);
		Ciudad c21 = new Ciudad("Copenhague", "Dinamarca", 0, 0, 0, 0, 0, 0);
		Ciudad c22 = new Ciudad("Oporto", "Portugal", 0, 0, 0, 0, 0, 0);
		Ciudad c23 = new Ciudad("San Petersburgo", "Rusia", 0, 0, 0, 0, 0, 0);
		Ciudad c24 = new Ciudad("Granada", "Espania", 0, 0, 0, 0, 0, 0);
		Ciudad c25 = new Ciudad("Sevilla", "Espania", 0, 0, 0, 0, 0, 0);
		Ciudad c26 = new Ciudad("Moscu", "Rusia", 0, 0, 0, 0, 0, 0);
		Ciudad c27 = new Ciudad("Montecarlo", "Monaco", 0, 0, 0, 0, 0, 0);
		Ciudad c28 = new Ciudad("Amberes", "Belgica", 0, 0, 0, 0, 0, 0);

		try {
			managerDAO.storeUsuario(u1);
			managerDAO.storeUsuario(u2);
			managerDAO.storeUsuario(u3);
			managerDAO.storeUsuario(u4);
			managerDAO.storeUsuario(u5);
			managerDAO.storeUsuario(u6);
			managerDAO.storeUsuario(u7);
			managerDAO.storeUsuario(u8);
			managerDAO.storeUsuario(u9);
			managerDAO.storeUsuario(u10);
			managerDAO.storeUsuario(u11);
			managerDAO.storeUsuario(u12);
			managerDAO.storeUsuario(u13);
			managerDAO.storeUsuario(u14);
			managerDAO.storeUsuario(u15);
			managerDAO.storeUsuario(u16);
			managerDAO.storeUsuario(u17);
			managerDAO.storeUsuario(u18);
			managerDAO.storeUsuario(u19);
			managerDAO.storeUsuario(u20);

			managerDAO.storeCiudad(c1);
			managerDAO.storeCiudad(c2);
			managerDAO.storeCiudad(c3);
			managerDAO.storeCiudad(c4);
			managerDAO.storeCiudad(c5);
			managerDAO.storeCiudad(c6);
			managerDAO.storeCiudad(c7);
			managerDAO.storeCiudad(c8);
			managerDAO.storeCiudad(c9);
			managerDAO.storeCiudad(c10);
			managerDAO.storeCiudad(c11);
			managerDAO.storeCiudad(c12);
			managerDAO.storeCiudad(c13);
			managerDAO.storeCiudad(c14);
			managerDAO.storeCiudad(c15);
			managerDAO.storeCiudad(c16);
			managerDAO.storeCiudad(c17);
			managerDAO.storeCiudad(c18);
			managerDAO.storeCiudad(c19);
			managerDAO.storeCiudad(c20);
			managerDAO.storeCiudad(c21);
			managerDAO.storeCiudad(c22);
			managerDAO.storeCiudad(c23);
			managerDAO.storeCiudad(c24);
			managerDAO.storeCiudad(c25);
			managerDAO.storeCiudad(c26);
			managerDAO.storeCiudad(c27);
			managerDAO.storeCiudad(c28);

		} catch (Exception e) {

		}
		logger.info("Base de datos rellena con exito");
	}

	// Comprobamos los puntos de una ciudad

	// Ahora mismo, al actualizar la ciudad de Lisboa, Lisboa deberia tener 5 de
	// puntuacion total

	@Test
	@Required(totalTime = 1000, max = 500, average = 200)
	public void test3() {
		logger.info(
				"Test para obtener los puntos de una ciudad - Obteniendo puntos de una ciudad de la base de datos - Valido");
		int points = 0;
		try {
			points = cliente.getCollector().getCiudadPoints("Lisboa");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(5, points);
	}

	// Comprobamos que funcione bien la funcion borrar Ciudad
	// Hay 28 ciudades, si borramos 1 deberia haber 27

	// Comprobamos que aniadimos una nueva ciudad

	@Test
	@PerfTest(invocations = 1)
	@Required(totalTime = 5000, average = 1000)
	public void test4() {
		logger.info(
				"Test para insertar una nueva ciudad - Insertando una ciudad a la base de datos con los metodos set - Valido");

		List<CiudadDTO> arrayCiudadDTO = new ArrayList<CiudadDTO>();

		for (int i = 0; i <= 5; i++) {
			arrayCiudadDTO.add(new CiudadDTO());
			arrayCiudadDTO.get(i).setNombreCiudad("NombreCiudad_Nº" + i);
			arrayCiudadDTO.get(i).setNumVotantes(0);
			arrayCiudadDTO.get(i).setPais("Pais_Nº" + i);
			arrayCiudadDTO.get(i).setPuntuacionCultura(0);
			arrayCiudadDTO.get(i).setPuntuacionGastronomia(0);
			arrayCiudadDTO.get(i).setPuntuacionOcio(0);
			arrayCiudadDTO.get(i).setPuntuacionTransporte(0);
			arrayCiudadDTO.get(i).setPuntuacionTotal(0);
		}

		if (cliente.getCollector() != null) {
			try {
				for (int i = 0; i < arrayCiudadDTO.size(); i++) {
					assertEquals(true, cliente.getCollector().insertCiudad(arrayCiudadDTO.get(i)));
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}
		} else {
			logger.error("El controller es nulo!");
		}
	}

	// Comprobamos que se registra un usuario
	@Test
	@Required(totalTime = 5000)
	public void test5() {

		logger.info("Test para registrar un usuario - Insertando un usuario a labasede datos - Valido ");

		UsuarioDTO userDTO = new UsuarioDTO();
		userDTO.setApellido("Cualquiera");
		userDTO.setNombre("Pepito");
		userDTO.setEmail("PepitoCualquierda@opendeusto.es");
		userDTO.setPassword("12345");

		if (cliente.getCollector() != null) {
			try {
				assertEquals(true, cliente.getCollector().registerUsuario(userDTO));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}
		} else {
			logger.error("El controller es nulo!");
		}
	}

	// Comprobamos que se borra un usuario
	@Test
	@Required(totalTime = 550, average = 300)
	public void test6() {
		logger.info("Test para borrar un usuario - Borrando un usuario de la base dedatos - Valido");
		// Se eliminara el usuario cuyo nombre es Cristian
		try {
			cliente.getCollector().deleteUsuario(u1);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Usuario> usuarios = null;
		try {
			usuarios = cliente.getCollector().getUsuarios();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(21, usuarios.size());

	}

	public CiudadesController getController() {
		return controller;
	}

	public void setController(CiudadesController controller) {
		this.controller = controller;
	}
}
