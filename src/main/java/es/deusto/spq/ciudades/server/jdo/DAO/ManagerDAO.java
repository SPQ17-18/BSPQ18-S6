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
import es.deusto.spq.ciudades.server.jdo.data.Usuario;

public class ManagerDAO implements IManagerDAO{
	
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
		logger.info("Guardan una ciudad cuyo nombre es " + ciudad.getNombreCiudad());
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

	public void updateCiudad(Ciudad ciudad) throws Exception {

		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx = pm.currentTransaction();

		pm.getFetchPlan().setMaxFetchDepth(4);

		try {
			tx.begin();

			Query<?> query = pm.newQuery("SELECT FROM " + Ciudad.class.getName() + " WHERE  idCiudad== '" + ciudad.getIdCiudad() + "'");
			query.setUnique(true);
			Ciudad result = (Ciudad) query.execute();

			result.setIdCiudad(ciudad.getIdCiudad());
			result.setNombreCiudad(ciudad.getNombreCiudad());
			result.setNumVotantes(ciudad.getNumVotantes());
			result.setPais(ciudad.getPais());
			result.setPuntuacionCultura(ciudad.getPuntuacionCultura());
			result.setPuntuacionGastronomia(ciudad.getPuntuacionGastronomia());
			result.setPuntuacionOcio(ciudad.getPuntuacionOcio());
			result.setPuntuacionTotal(ciudad.getPuntuacionTotal());
			result.setPuntuacionTransporte(ciudad.getPuntuacionTransporte());

			tx.commit();

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Error actualizando una ciudad: " + ex.getMessage());
			throw new Exception();
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
	public Usuario getUsuario(String email) {
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx = pm.currentTransaction();
		Usuario usuario = new Usuario();

		pm.getFetchPlan().setMaxFetchDepth(3);

		try {
			tx.begin();
			Query<?> query = pm.newQuery("SELECT FROM " + Usuario.class.getName() + " WHERE email == '" + email + "'");
			query.setUnique(true);
			Usuario result = (Usuario) query.execute();
			usuario.copyUsuario(result);
			tx.commit();
		} catch (Exception ex) {
			logger.error("Error al obtener un usuario de la base de datos: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		return usuario;
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
		logger.info("* Guardando al usuario cuyo mail es: " + usuario.getEmail());
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
	 * Method to manage a member in the DB
	 * 
	 * @param member
	 *            Member to manage
	 * @throws Exception
	 *             Throws an exception when an error occurs
	 */
	public void manageMember(Member member) throws Exception {

		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();
			Query<?> query = pm
					.newQuery("SELECT FROM " + Member.class.getName() + " WHERE  email== '" + member.getEmail() + "'");
			query.setUnique(true);
			Member result = (Member) query.execute();

			result.setBirthday(member.getBirthday());
			result.setName(member.getName());
			result.setSurname(member.getSurname());
			result.setPassword(member.getPassword());
			result.setPoints(member.getPoints());

			tx.commit();

		} catch (Exception ex) {
			logger.error("Error updating a member: " + ex.getMessage());
			throw new Exception();
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

	}

	
	


}
