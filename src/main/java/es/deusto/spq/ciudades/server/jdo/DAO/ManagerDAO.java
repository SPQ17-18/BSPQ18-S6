package es.deusto.spq.ciudades.server.jdo.DAO;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import org.apache.log4j.Logger;

public class ManagerDAO implements IManagerDAO{
	
	private static final Logger logger = Logger.getLogger(ManagerDAO.class);

	private PersistenceManagerFactory pmf;

	/**
	 * Constructor para manager
	 */
	public ManagerDAO() {
		pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
	}

}
