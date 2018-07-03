package es.deusto.spq.ciudades.server.jdo.DAO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.SynchronousQueue;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.sound.midi.Synthesizer;

import org.apache.log4j.Logger;

import es.deusto.spq.ciudades.server.jdo.data.Ciudad;
import es.deusto.spq.ciudades.server.jdo.data.CiudadUsuario;
import es.deusto.spq.ciudades.server.jdo.data.Puntuacion;
import es.deusto.spq.ciudades.server.jdo.data.Usuario;

/**
 * 
 * Funciones las cuales trabajan con la base de datos...
 *
 */
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

	@Override
	public void storePuntuacion(Puntuacion puntuacion) throws Exception {
		// TODO Auto-generated method stub
		logger.info("Guardando ciudad puntuada... " + puntuacion.getNombreCiudad());
		this.storeObject(puntuacion);
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

		System.out.println("Obteniendo suuario en puntuarCiudadUSuario: " + usuario.getEmail());

		try {
			tx.begin();

			Ciudad ciudadBuena = null;
			Usuario usuarioBueno = null;

			@SuppressWarnings("unchecked")
			Query<Ciudad> q = pm.newQuery("SELECT FROM " + Ciudad.class.getName());
			@SuppressWarnings("unchecked")
			List<Ciudad> resultCiudades = (List<Ciudad>) q.execute();

			@SuppressWarnings("unchecked")
			Query<Usuario> q2 = pm.newQuery("SELECT FROM " + Usuario.class.getName());
			@SuppressWarnings("unchecked")
			List<Usuario> resultUsuarios = (List<Usuario>) q2.execute();

			for (Ciudad c : resultCiudades) {
				if (c.getNombreCiudad().equals(ciudad.getNombreCiudad())) {
					ciudadBuena = c;
				}
			}

			for (Usuario u : resultUsuarios) {
				if (u.getEmail().equals(usuario.getEmail())) {
					usuarioBueno = u;
					System.out.println(usuarioBueno.getNombre() + " usuarioBueno encontrado!");
				}
			}

			// Creamos el objeto bueno a guardar:
			// Objeto a guardar en la base de datos:
			CiudadUsuario ciudadUsuario = new CiudadUsuario(ciudadBuena, usuarioBueno);
			System.out.println(ciudadUsuario.getCiudad().getNombreCiudad() + " cogido ciudad de CiudadUsuario...");
			System.out.println(ciudadUsuario.getUsuario().getNombre() + " cogido usuario de CiudadUsuario...");
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
	 * Metodo para borrar una ciudad
	 * 
	 * @param nombreCiudad
	 *            Id de la ciudad a borrar
	 */
	public void deleteCiudad(String nombreCiudad) {
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();

			Query<Ciudad> query = pm.newQuery(Ciudad.class, "id de la ciudad =='" + nombreCiudad + "'");

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
	 * @param nombreCiudad
	 *            Id de la ciudad
	 * @throws Exception
	 *             Lanza una excepcion en caso de que ocurra un error
	 * @return Devuelve los puntos de una ciudad
	 */
	public int getCiudadPoints(String nombreCiudad) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		int points = 0;

		try {
			tx.begin();
			@SuppressWarnings("unchecked")
			Query<Ciudad> q = pm.newQuery("SELECT FROM " + Ciudad.class.getName());

			@SuppressWarnings("unchecked")
			List<Ciudad> result = (List<Ciudad>) q.execute();

			for (Ciudad c : result) {
				if (c.getNombreCiudad().equals(nombreCiudad)) {
					points = c.getPuntuacionTotal();
				}
			}

			tx.commit();
		} catch (Exception ex) {
			logger.error("Error al obtener los puntos de una ciudad con el id: " + nombreCiudad + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		return points;
	}

	public static Usuario usuarioDAO = new Usuario();

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
					System.out.println("ManagerDAO:" + u.getEmail());
					usuarioDAO.copyUsuario(u);
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
			Usuario f = (Usuario) result.iterator().next();

			query.close(result);
			pm.deletePersistent(f);

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

	@Override
	public ArrayList<CiudadUsuario> getCiudadesPuntuadasPorUsuarios() {
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx = pm.currentTransaction();
		ArrayList<CiudadUsuario> ciudadesPuntuadas = new ArrayList<CiudadUsuario>();

		try {
			tx.begin();
			@SuppressWarnings("unchecked")
			Query<CiudadUsuario> q = pm.newQuery("SELECT FROM " + CiudadUsuario.class.getName());
			@SuppressWarnings("unchecked")
			List<CiudadUsuario> result = (List<CiudadUsuario>) q.execute();

			for (int i = 0; i < result.size(); i++) {
				ciudadesPuntuadas.add(new CiudadUsuario());
				ciudadesPuntuadas.get(i).copyCiudadUsuario(result.get(i));
				ciudadesPuntuadas.get(i).setCiudad(new Ciudad());
				ciudadesPuntuadas.get(i).getCiudad().copyCiudad(result.get(i).getCiudad());
				ciudadesPuntuadas.get(i).setUsuario(new Usuario());
				ciudadesPuntuadas.get(i).getUsuario().copyUsuario(result.get(i).getUsuario());
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

		return ciudadesPuntuadas;
	}

	@Override
	public ArrayList<Puntuacion> getPuntuaciones() {
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx = pm.currentTransaction();
		ArrayList<Puntuacion> ciudadesPuntuadas = new ArrayList<Puntuacion>();

		try {
			tx.begin();
			@SuppressWarnings("unchecked")
			Query<Puntuacion> q = pm.newQuery("SELECT FROM " + Puntuacion.class.getName());
			@SuppressWarnings("unchecked")
			List<Puntuacion> result = (List<Puntuacion>) q.execute();

			for (int i = 0; i < result.size(); i++) {
				ciudadesPuntuadas.add(new Puntuacion());
				ciudadesPuntuadas.get(i).copyPuntuacion(result.get(i));
			}

			tx.commit();
		} catch (Exception ex) {
			logger.error("Error intentado obtener las puntuacuones de las ciudades");
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		return ciudadesPuntuadas;
	}

}
