package es.deusto.spq.ciudades.server.jdo.DAO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;

import es.deusto.spq.ciudades.server.jdo.data.Ciudad;
import es.deusto.spq.ciudades.server.jdo.data.CiudadUsuario;
import es.deusto.spq.ciudades.server.jdo.data.Usuario;

public class ManagerDAO implements IManagerDAO {

	private static final Logger logger = Logger.getLogger(ManagerDAO.class);

	private PersistenceManagerFactory pmf;

	/**
	 * Constructor para manager
	 */
	public ManagerDAO() {
		pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
	}

	/**
	 * Metodo para guardar una ciudad en la base de datos
	 * 
	 * @param ciudad
	 *            Ciudad para insertar
	 * @throws Exception
	 *             Lanza una excepcion cuando ocurre un error
	 */
	public void storeCiudad(Ciudad ciudad) throws Exception {
		logger.info("Guardando una ciudad cuyo nombre es " + ciudad.getNombreCiudad());
		this.storeObject(ciudad);
	}

	/**
	 * Metodo para guardar objetos en la base de datos
	 * 
	 * @param object
	 *            Objecto para guardar
	 * @throws Exception
	 *             Lanza una excepcion en caso de que ocurra un error
	 */
	private void storeObject(Object object) throws Exception {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			pm.makePersistent(object);
			pm.detachCopy(object);
			tx.commit();
		} catch (Exception ex) {
			logger.error("Problem occurred trying to store the object");
			throw new Exception();
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	public void puntuarCiudadUsuario(Ciudad ciudad, Usuario usuario) {

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();

			Ciudad ciudadBuena = null;
			Usuario usuarioBueno = null;

			Query<Ciudad> q = pm.newQuery("SELECT FROM " + Ciudad.class.getName());
			List<Ciudad> resultCiudades = (List<Ciudad>) q.execute();

			Query<Usuario> q2 = pm.newQuery("SELECT FROM " + Usuario.class.getName());
			List<Usuario> resultUsuarios = (List<Usuario>) q2.execute();

			for (Ciudad c : resultCiudades) {
				if (c.getNombreCiudad().equals(ciudad.getNombreCiudad())) {
					ciudadBuena = c;
				}
			}

			for (Usuario u : resultUsuarios) {
				if (u.getEmail().equals(usuario.getEmail())) {
					usuarioBueno = u;
				}
			}

			// Creamos el objeto bueno a guardar:
			// Objeto a guardar en la base de datos:
			CiudadUsuario ciudadUsuario = new CiudadUsuario(ciudadBuena, usuarioBueno);
			pm.makePersistent(ciudadUsuario);

			tx.commit();
		} catch (Exception ex) {
			logger.error("Error intentado obtener las ciudades");
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Metodo para obtener las ciudades de la base de datos
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Ciudad> getCiudades() {
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx = pm.currentTransaction();
		ArrayList<Ciudad> ciudades = new ArrayList<Ciudad>();

		pm.getFetchPlan().setMaxFetchDepth(6);

		try {
			tx.begin();
			Query<?> q = pm.newQuery("SELECT FROM " + Ciudad.class.getName());
			List<Ciudad> result = (List<Ciudad>) q.execute();

			for (int i = 0; i < result.size(); i++) {
				ciudades.add(new Ciudad());
				ciudades.get(i).copyCiudad(result.get(i));
			}

			tx.commit();
		} catch (Exception ex) {
			logger.error("Error intentado obtener las ciudades");
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		return ciudades;
	}

	/**
	 * Metodo para actualizar una ciudad de la base de datos
	 * 
	 * @param ciudad
	 *            Ciudad a actualizar
	 * @throws Exception
	 *             Lanza una excepcion cuando ocurre un error
	 */

	@Override
	public void updateCiudad(Ciudad c) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();
			pm.makePersistent(c);
			tx.commit();
		} catch (Exception ex) {
			System.out.println("Error actualizando una ciudad: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}

	}

	/**
	 * Metodo para borrar una ciudad
	 * 
	 * @param idCiudad
	 *            Id de la ciudad a borrar
	 */
	public void deleteCiudad(int idCiudad) {
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();

			Query<Ciudad> query = pm.newQuery(Ciudad.class, "id de la ciudad =='" + idCiudad + "'");

			Collection<?> result = (Collection<?>) query.execute();

			Ciudad c = (Ciudad) result.iterator().next();

			query.close(result);

			pm.deletePersistent(c);

			tx.commit();
		} catch (Exception ex) {
			logger.error("Error al borrar una ciudad: " + ex.getMessage());

		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			if (pm != null && !pm.isClosed()) {
				pm.close();
			}
		}
	}

	/**
	 * Metodo para obtener los puntos de una ciudad de la base de datos
	 * 
	 * @param idCiudad
	 *            Id de la ciudad
	 * @throws Exception
	 *             Lanza una excepcion en caso de que ocurra un error
	 * @return Devuelve los puntos de una ciudad
	 */
	public int getCiudadPoints(int idCiudad) {
		PersistenceManager pm = pmf.getPersistenceManager();

		pm.getFetchPlan().setMaxFetchDepth(4);
		Transaction tx = pm.currentTransaction();
		int points = 0;

		try {
			tx.begin();
			Query<?> q = pm.newQuery("SELECT FROM " + Ciudad.class.getName() + " WHERE idCiudad == '" + idCiudad + "'");
			@SuppressWarnings("unchecked")
			List<Ciudad> result = (List<Ciudad>) q.execute();

			points = result.get(0).getPuntuacionTotal();
			tx.commit();
		} catch (Exception ex) {
			logger.error("Error al obtener los puntos de una ciudad con el id: " + idCiudad + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		return points;
	}

	/**
	 * Metodo para obtener el usuario de la base de datos
	 * 
	 * @param email
	 *            Email del usuario
	 * @return Devuelve un usuario de la base de datos
	 */
	public String getUsuario(String email) {
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx = pm.currentTransaction();
		String dev = "";

		try {
			tx.begin();
			@SuppressWarnings("unchecked")
			Query<Usuario> query = pm.newQuery("SELECT FROM " + Usuario.class.getName());

			List<Usuario> usuarios = (List<Usuario>) query.executeList();

			for (Usuario u : usuarios) {
				if (u.getEmail().equals(email)) {
					dev = u.getPassword();
				}
			}

			// System.out.println("Resultado de la operación getUsuario(): " +
			// result.getEmail());

			tx.commit();
		} catch (Exception ex) {
			logger.error("Error al obtener un usuario de la base de datos: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		return dev;
	}

	/**
	 * Metodo para guarda un usuario en la base de datos
	 * 
	 * @param usuario
	 *            Usuario a insertar
	 * @throws Exception
	 *             Lanza una excepcion en caso de que ocurra un error
	 */
	public void storeUsuario(Usuario usuario) throws Exception {
		logger.info("* Guardando al usuario cuyo email es: " + usuario.getEmail());
		this.storeObject(usuario);
	}

	/**
	 * Metodo para obtener los usuarios de la base de datos
	 * 
	 * @return Devuelve una lista de usuarios
	 */
	public ArrayList<Usuario> getUsuarios() {
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx = pm.currentTransaction();
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

		pm.getFetchPlan().setMaxFetchDepth(3);

		try {
			tx.begin();
			Query<?> q = pm.newQuery("SELECT FROM " + Usuario.class.getName());
			@SuppressWarnings("unchecked")
			List<Usuario> result = (List<Usuario>) q.execute();

			logger.info("Obtenidos todos los usuarios.");

			for (int i = 0; i < result.size(); i++) {
				usuarios.add(new Usuario());
				usuarios.get(i).copyUsuario(result.get(i));
			}

			tx.commit();
		} catch (Exception ex) {
			logger.error("Error al obtener todos los usuarios: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		return usuarios;
	}

	/**
	 * Metodo para borrar un usuario de la base de datos
	 * 
	 * @param usuario
	 *            Usuario para borrar
	 * @throws Exception
	 *             Lanza una excepcion en caso de que ocurra un error
	 */
	public void deleteUsuario(Usuario usuario) throws Exception {

		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();

			Query<Usuario> query = pm.newQuery(Usuario.class, "email =='" + usuario.getEmail() + "'");

			Collection<?> result = (Collection<?>) query.execute();

			Usuario u = (Usuario) result.iterator().next();

			query.close(result);

			pm.deletePersistent(u);

			tx.commit();
		} catch (Exception ex) {
			logger.error("Error al borrar un usuario: " + ex.getMessage());
			throw new Exception();
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			if (pm != null && !pm.isClosed()) {
				pm.close();
			}
		}

	}

	/**
	 * Metodo para gestionar un usuario en la base de datos
	 * 
	 * @param usuario
	 *            Usuario que vamos a gestionar
	 * @throws Exception
	 *             Lanza una excepcion en caso de que ocurra un error
	 */
	public void manageUsuario(Usuario usuario) throws Exception {

		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();
			Query<?> query = pm.newQuery(
					"SELECT FROM " + Usuario.class.getName() + " WHERE  email== '" + usuario.getEmail() + "'");
			query.setUnique(true);
			Usuario result = (Usuario) query.execute();

			result.setApellido(usuario.getApellido());
			result.setEmail(usuario.getEmail());
			result.setNombre(usuario.getNombre());
			result.setPassword(usuario.getPassword());
			// result.setCiudades(result.getCiudadesPuntuadas());

			tx.commit();

		} catch (Exception ex) {
			logger.error("Error al actualizar un usuario: " + ex.getMessage());
			throw new Exception();
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

	}

	public static void main(String[] args) {
		IManagerDAO dao = new ManagerDAO();

		if (args.length != 3) {
			logger.error("Atencion: faltan argumentos");
			System.exit(0);
		}

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		Usuario u1 = new Usuario("cristian@opendeusto.es", "Cristian", "Perez", "1234");
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
		Ciudad c4= new Ciudad("Barcelona", "Espania", 0, 0, 0, 0, 0, 0);
		Ciudad c5 = new Ciudad("Bilbao", "Espania", 0, 0, 0, 0, 0, 0);
		Ciudad c6 = new Ciudad("Lugo", "Espania", 0, 0, 0, 0, 0, 0);
		Ciudad c7 = new Ciudad("Valencia", "Espania", 0, 0, 0, 0, 0, 0);
		Ciudad c8 = new Ciudad("Londres", "Reino Unido", 0, 0, 0, 0, 0, 0);
		Ciudad c9 = new Ciudad("Roma", "Italia", 0, 0, 0, 0, 0, 0);
		Ciudad c10= new Ciudad("Lisboa", "Portugal", 0, 0, 0, 0, 0, 0);
		Ciudad c11= new Ciudad("Venecia", "Italia", 0, 0, 0, 0, 0, 0);
		Ciudad c12= new Ciudad("Budapest", "Hungria", 0, 0, 0, 0, 0, 0);
		Ciudad c13= new Ciudad("Praga", "Republica Checa", 0, 0, 0, 0, 0, 0);
		Ciudad c14= new Ciudad("Atenas", "Grecia", 0, 0, 0, 0, 0, 0);
		Ciudad c15= new Ciudad("Estambul", "Turquia", 0, 0, 0, 0, 0, 0);
		Ciudad c16= new Ciudad("Viena", "Austria", 0, 0, 0, 0, 0, 0);
		Ciudad c17= new Ciudad("Salzburgo", "Austria", 0, 0, 0, 0, 0, 0);
		Ciudad c18= new Ciudad("Florencia", "Italia", 0, 0, 0, 0, 0, 0);
		Ciudad c19= new Ciudad("Cracovia", "Polonia", 0, 0, 0, 0, 0, 0);
		Ciudad c20= new Ciudad("Estocolmo", "Suecia", 0, 0, 0, 0, 0, 0);
		Ciudad c21= new Ciudad("Copenhague", "Dinamarca", 0, 0, 0, 0, 0, 0);
		Ciudad c22= new Ciudad("Oporto", "Portugal", 0, 0, 0, 0, 0, 0);
		Ciudad c23= new Ciudad("San Petersburgo", "Rusia", 0, 0, 0, 0, 0, 0);
		Ciudad c24= new Ciudad("Granada", "Espania", 0, 0, 0, 0, 0, 0);
		Ciudad c25= new Ciudad("Sevilla", "Espania", 0, 0, 0, 0, 0, 0);
		Ciudad c26= new Ciudad("Moscu", "Rusia", 0, 0, 0, 0, 0, 0);
		Ciudad c27= new Ciudad("Montecarlo", "Monaco", 0, 0, 0, 0, 0, 0);
		Ciudad c28= new Ciudad("Amberes", "Belgica", 0, 0, 0, 0, 0, 0);

		
		ArrayList<Ciudad> cu1 = new ArrayList<Ciudad>();
		cu1.add(c1); // El usuario 1 ha puntuado la ciudad 1

		ArrayList<Ciudad> cu2 = new ArrayList<Ciudad>();
		cu2.add(c1);
		cu2.add(c2);

		try {
			dao.storeUsuario(u1);
			dao.storeUsuario(u2);
			dao.storeUsuario(u3);
			dao.storeUsuario(u4);
			dao.storeUsuario(u5);
			dao.storeUsuario(u6);
			dao.storeUsuario(u7);
			dao.storeUsuario(u8);
			dao.storeUsuario(u9);
			dao.storeUsuario(u10);
			dao.storeUsuario(u11);
			dao.storeUsuario(u12);
			dao.storeUsuario(u13);
			dao.storeUsuario(u14);
			dao.storeUsuario(u15);
			dao.storeUsuario(u16);
			dao.storeUsuario(u17);
			dao.storeUsuario(u18);
			dao.storeUsuario(u19);
			dao.storeUsuario(u20);
			
			
			dao.storeCiudad(c1);
			dao.storeCiudad(c2);
			dao.storeCiudad(c3);
			dao.storeCiudad(c4);
			dao.storeCiudad(c5);
			dao.storeCiudad(c6);
			dao.storeCiudad(c7);
			dao.storeCiudad(c8);
			dao.storeCiudad(c9);
			dao.storeCiudad(c10);
			dao.storeCiudad(c11);
			dao.storeCiudad(c12);
			dao.storeCiudad(c13);
			dao.storeCiudad(c14);
			dao.storeCiudad(c15);
			dao.storeCiudad(c16);
			dao.storeCiudad(c17);
			dao.storeCiudad(c18);
			dao.storeCiudad(c19);
			dao.storeCiudad(c20);
			dao.storeCiudad(c21);
			dao.storeCiudad(c22);
			dao.storeCiudad(c23);
			dao.storeCiudad(c24);
			dao.storeCiudad(c25);
			dao.storeCiudad(c26);
			dao.storeCiudad(c27);
			dao.storeCiudad(c28);



		} catch (Exception e) {

		}
		logger.info("Base de datos rellena con exito");

	}

}
