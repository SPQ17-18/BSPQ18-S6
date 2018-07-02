package es.deusto.spq.ciudades.server.jdo.DAO;

import java.text.ParseException;
import java.util.Collection;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import es.deusto.spq.ciudades.server.jdo.data.Ciudad;
import es.deusto.spq.ciudades.server.jdo.data.CiudadUsuario;
import es.deusto.spq.ciudades.server.jdo.data.Puntuacion;
import es.deusto.spq.ciudades.server.jdo.data.Usuario;

/**
 * PRIMERO SE EJECUTARÁ ESTA CLASE DE TEST PARA EL BORRADO DE ALGUNOS POSIBLES
 * DATOS QUE QUEDEN EN LA BASE DE DATOS!
 * 
 * @author Jesús
 *
 */
@Ignore
public class ManagerDAOTest {

	private PersistenceManagerFactory pmf = null;
	private PersistenceManager pm = null;
	private Transaction tx = null;

	@Before
	public void setUp() throws Exception {
		this.pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		this.pm = this.pmf.getPersistenceManager();
		this.tx = this.pm.currentTransaction();
	}

	/**
	 * Borrado de ciuades puntadas por cada usuario:
	 * 
	 * @throws ParseException
	 */
	@Test
	public void test1() throws ParseException {
		try {
			tx.begin();
			Query<?> q = pm.newQuery(CiudadUsuario.class);
			@SuppressWarnings("unchecked")
			Collection<CiudadUsuario> list = (Collection<CiudadUsuario>) q.execute();
			pm.deletePersistentAll(list);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Borrado de usuarios:
	 * 
	 * @throws ParseException
	 */
	@Test
	public void test2() throws ParseException {
		try {
			tx.begin();
			Query<?> q = pm.newQuery(Usuario.class);
			@SuppressWarnings("unchecked")
			Collection<Usuario> list = (Collection<Usuario>) q.execute();
			pm.deletePersistentAll(list);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Borrado de ciudades:
	 * 
	 * @throws ParseException
	 */
	@Test
	public void test3() throws ParseException {
		try {
			tx.begin();
			Query<?> q = pm.newQuery(Ciudad.class);
			@SuppressWarnings("unchecked")
			Collection<Ciudad> list = (Collection<Ciudad>) q.execute();
			pm.deletePersistentAll(list);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Borrado de puntuaciones:
	 * 
	 * @throws ParseException
	 */
	@Test
	public void test4() throws ParseException {
		try {
			tx.begin();
			Query<?> q = pm.newQuery(Puntuacion.class);
			@SuppressWarnings("unchecked")
			Collection<Puntuacion> list = (Collection<Puntuacion>) q.execute();
			pm.deletePersistentAll(list);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

}
