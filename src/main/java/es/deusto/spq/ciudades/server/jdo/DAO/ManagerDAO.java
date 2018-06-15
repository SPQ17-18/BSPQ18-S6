package es.deusto.spq.ciudades.server.jdo.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;

import es.deusto.spq.ciudades.server.jdo.data.Ciudad;

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




}
