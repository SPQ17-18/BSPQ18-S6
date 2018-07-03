package es.deusto.spq.ciudades.server;

import java.awt.EventQueue;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.apache.log4j.Logger;

import es.deusto.spq.ciudades.server.jdo.DAO.IManagerDAO;
import es.deusto.spq.ciudades.server.jdo.DAO.ManagerDAO;
import es.deusto.spq.ciudades.server.jdo.data.Assembler;
import es.deusto.spq.ciudades.server.jdo.data.Ciudad;
import es.deusto.spq.ciudades.server.jdo.data.CiudadDTO;
import es.deusto.spq.ciudades.server.jdo.data.CiudadUsuario;
import es.deusto.spq.ciudades.server.jdo.data.CiudadUsuarioDTO;
import es.deusto.spq.ciudades.server.jdo.data.Puntuacion;
import es.deusto.spq.ciudades.server.jdo.data.PuntuacionDTO;
import es.deusto.spq.ciudades.server.jdo.data.Usuario;
import es.deusto.spq.ciudades.server.jdo.data.UsuarioDTO;
import es.deusto.spq.ciudades.server.remote.IRemoteFacade;

/**
 * 
 * Funciones del servidor... Estan estan declaradas en la fachada remota
 * y se definen en esta clase. 
 * 
 * Ademas, esta clase hace uso del ManagerDAO para trabajar con la base de datos y 
 * el assembler para convertir objetos DTO.
 *
 */
public class Collector extends UnicastRemoteObject implements IRemoteFacade {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(Server.class);

	private IManagerDAO dao;
	private Assembler assembler;

	/**
	 * Constructor para el servidor
	 * 
	 * @throws RemoteException
	 *             Lanza una excepcion en caso de que ocurra un error
	 */
	public Collector() throws RemoteException {
		dao = new ManagerDAO();
		assembler = new Assembler();
	}

	public boolean puntuarCiudadUsuario(Ciudad ciudad, Usuario usuario) {
		try {
			System.out.println(usuario.getEmail() + " collector!");
			dao.puntuarCiudadUsuario(ciudad, usuario);
			logger.info("Ciudad puntuad: " + ciudad.getNombreCiudad());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Metodo para insertar una ciudad
	 * 
	 * @param ciudadDTO
	 *            Data para insertar la ciudad
	 * @throws RemoteException
	 *             Lanza una excepcion en caso de que ocurra un error
	 * 
	 * @result Devuelve true cuando la ciudad es correctamente insertada
	 */
	public boolean insertCiudad(CiudadDTO ciudadDTO) throws RemoteException {
		try {
			Ciudad ciudad = assembler.disassembleCiudad(ciudadDTO);
			dao.storeCiudad(ciudad);
			logger.info("Insertada una ciudad en la base de datos que se llama " + ciudadDTO.getNombreCiudad());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean puntuarCiudad(PuntuacionDTO puntuacionDTO) throws RemoteException {
		try {
			Puntuacion puntuacion = assembler.disaassemblePuntuacion(puntuacionDTO);
			dao.storePuntuacion(puntuacion);
			logger.info("Insertada nueva puntuación de ciudad " + puntuacion.getNombreCiudad());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Metodo para obtener las ciudades
	 * 
	 * @throws RemoteException
	 *             Lanza una excepcion en caso de que ocurra un error
	 * @result Devuelve una lista con las ciudades
	 */
	public ArrayList<CiudadDTO> getCiudades() throws RemoteException {
		ArrayList<Ciudad> ciudades = dao.getCiudades();
		logger.info("El cliente pregunta por las ciudades");
		return assembler.assembleCiudad(ciudades);

	}

	@Override
	public ArrayList<CiudadUsuarioDTO> getCiudadesPuntuadasPorUsuarios() throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<CiudadUsuario> ciudades = dao.getCiudadesPuntuadasPorUsuarios();
		logger.info("Ciudades puntuadas por usuarios obtenidas");
		return assembler.assembleCiudadUsuario(ciudades);
	}

	@Override
	public ArrayList<PuntuacionDTO> getPuntuaciones() throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<Puntuacion> puntuaciones = dao.getPuntuaciones();
		logger.info("Puntuaciones de ciudades obteneidas");
		return assembler.assemblePuntuacion(puntuaciones);
	}

	/**
	 * Metodo para actualizar una ciudad
	 * 
	 * @param ciudadDTO
	 *            Data para actualizar una ciudad
	 * @throws RemoteException
	 *             Lanza una excepcion en caso de que ocurra un error
	 * @result Devuelve true cuando la ciudad se ha insertado correctamente
	 */
	public boolean updateCiudad(CiudadDTO ciudadDTO) throws RemoteException {
		try {
			Ciudad ciudad = assembler.disassembleCiudad(ciudadDTO);
			dao.updateCiudad(ciudad);
			logger.info("Actualizada la ciudad cuyo nombre es " + ciudad.getNombreCiudad());
			return true;
		} catch (Exception e) {
			logger.error("Error actualizando la ciudad");
			return false;
		}
	}

	/**
	 * Metodo para borrar una ciudad
	 * 
	 * @param nombreCiudad
	 *            Id de la ciudad que queremos borrar
	 * @throws RemoteException
	 *             Lanza una excepcion en caso de que ocurra un error
	 * @result Devuelve true cuando la ciudad se ha borrado correctamente
	 */
	public boolean deleteCiudad(String nombreCiudad) throws RemoteException {
		try {

			dao.deleteCiudad(nombreCiudad);
			logger.info("Borrada la ciudad cuyo id es " + nombreCiudad);
			return true;
		} catch (Exception e) {
			logger.error("Error al borrar una ciudad");
			return false;
		}

	}

	/**
	 * Metodo para obtener los puntos de una ciudad
	 * 
	 * @param nombreCiudad
	 *            Id de la ciudad para obtener los puntos
	 * @throws RemoteException
	 *             Lanza una excepcion en caso de que ocurra un error
	 * @result Devuelve los puntos de una ciudad
	 */
	public int getCiudadPoints(String nombreCiudad) throws RemoteException {
		int points = -1;
		try {
			points = dao.getCiudadPoints(nombreCiudad);
			logger.info("Obteniendo los puntos de la ciudad cuyo id es" + nombreCiudad);
		} catch (Exception e) {
			logger.error("Error al obtener los puntos de una ciudad cuyo id es " + nombreCiudad);
		}
		return points;
	}

	/**
	 * Metodo para logear un usuario
	 * 
	 * @param email
	 *            Email del usuario
	 * @param password
	 *            Password del usuario
	 * @throws RemoteException
	 *             Lanza una excepcion en caso de que ocurra un error
	 * @result Devuelve true cuando el login es correcto
	 */
	public boolean loginUsuario(String email, String password) throws RemoteException {
		try {
			System.out.println("Usuario llega ");
			String pass = dao.getUsuario(email);
			if (pass.equals(password)) {
				logger.info("Usuario con email " + email + " se ha logeado correctamente");
				return true;
			} else {
				logger.info("Password incorrecto");
				return false;
			}

		} catch (Exception e) {
			logger.error("Usuario con email " + email + " no existe");
			return false;
		}
	}

	/**
	 * Metodo para registrar un usuario
	 * 
	 * @param usuarioDTO
	 *            Data para registrar
	 * @throws RemoteException
	 *             Lanza una excepcion en caso de que ocurra un error
	 * @result Devuelve true cuando el usuario se registra correctamentewhen the
	 *         employee is correctly registered
	 */
	public boolean registerUsuario(UsuarioDTO usuarioDTO) throws RemoteException {
		try {
			Usuario usuario = assembler.disassembleUsuario(usuarioDTO);
			dao.storeUsuario(usuario);
			logger.info("Inserta un usuario a la base de datos llamado" + usuario.getNombre());
			return true;
		} catch (Exception e) {
			logger.error("Clave primaria duplicada: El usuario ya existe");
			return false;
		}
	}

	/**
	 * Metodo para obtener los usuarios
	 * 
	 * @throws RemoteException
	 *             Lanza una excepcion en caso de que ocurra un error
	 * @result Devuelve una lista con los usuarios
	 */
	public ArrayList<Usuario> getUsuarios() throws RemoteException {
		ArrayList<Usuario> usuarios = dao.getUsuarios();
		logger.info("El cliente ha preguntado por todos los usuarios");
		// return assembler.assembleUsuario(usuarios);
		return usuarios;
	}

	/**
	 * Metodo para actualizar un usuario
	 * 
	 * @param usuarioDTO
	 *            Data para actualizar un usuario
	 * @throws RemoteException
	 *             Lanza una excepcion en caso de que ocurra un error
	 * @result Devuelve true cuando el usuario se ha actualizado correctamente.
	 */
	public boolean updateUsuario(UsuarioDTO usuarioDTO) throws RemoteException {
		try {
			Usuario usuario = assembler.disassembleUsuario(usuarioDTO);
			dao.manageUsuario(usuario);
			logger.info("Actualizado el usuario cuyo email es  " + usuarioDTO.getEmail());
			return true;
		} catch (Exception e) {
			logger.error("Error al actualizar un usuario");
			return false;
		}
	}

	/**
	 * Metodo para borrar un usuario
	 * 
	 * @param usuario
	 *            Data para borrar un usuario
	 * @throws RemoteException
	 *             Lanza una excepcion en caso de que ocurra un error
	 * @result Devuelve true cuando borra un usuario correctamente, false si no.
	 */
	public boolean deleteUsuario(Usuario usuario) throws RemoteException {
		try {
			// Usuario usuario = assembler.disassembleUsuario(usuario);
			dao.deleteUsuario(usuario);
			logger.info("Borra un usuario cuyo email es" + usuario.getEmail());
			return true;
		} catch (Exception e) {
			logger.error("Error al borrar un usuario");
			return false;
		}
	}

	/**
	 * Metodo que se encarga de llamar al metodo devuelveUsuario de usuarioDAO,
	 * pasandole un email de usuario y devuelve el usuario completo.
	 *
	 * @param email
	 *            - String
	 * @return Usuario - Devuelve un usuario
	 * @throws RemoteException
	 *             expulsa una excepcion
	 */
	public Usuario devolverUsuario(String email) throws RemoteException {
		return ManagerDAO.usuarioDAO;
	}

	public void iniJFrame() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame serverFrame = new JFrame();
					serverFrame.setVisible(true);
					System.out.println("El servidor está activo y esperando a los clientes!");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
	}
}
