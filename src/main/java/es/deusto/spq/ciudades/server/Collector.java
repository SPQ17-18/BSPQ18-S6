package es.deusto.spq.ciudades.server;

import java.awt.EventQueue;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.swing.JFrame;

import org.apache.log4j.Logger;

import es.deusto.spq.ciudades.server.jdo.DAO.IManagerDAO;
import es.deusto.spq.ciudades.server.jdo.DAO.ManagerDAO;
import es.deusto.spq.ciudades.server.jdo.data.Assembler;
import es.deusto.spq.ciudades.server.jdo.data.Ciudad;
import es.deusto.spq.ciudades.server.jdo.data.CiudadDTO;
import es.deusto.spq.ciudades.server.jdo.data.Usuario;
import es.deusto.spq.ciudades.server.jdo.data.UsuarioDTO;
import es.deusto.spq.ciudades.server.remote.IRemoteFacade;

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
	 * @param idCiudad
	 *            Id de la ciudad que queremos borrar
	 * @throws RemoteException
	 *             Lanza una excepcion en caso de que ocurra un error
	 * @result Devuelve true cuando la ciudad se ha borrado correctamente
	 */
	public boolean deleteCiudad(int idCiudad) throws RemoteException {
		try {

			dao.deleteCiudad(idCiudad);
			logger.info("Borrada la ciudad cuyo id es " + idCiudad);
			return true;
		} catch (Exception e) {
			logger.error("Error al borrar una ciudad");
			return false;
		}

	}

	/**
	 * Metodo para obtener los puntos de una ciudad
	 * 
	 * @param idCiudad
	 *            Id de la ciudad para obtener los puntos
	 * @throws RemoteException
	 *             Lanza una excepcion en caso de que ocurra un error
	 * @result Devuelve los puntos de una ciudad
	 */
	public int getCiudadPoints(int idCiudad) throws RemoteException {
		int points = -1;
		try {
			points = dao.getCiudadPoints(idCiudad);
			logger.info("Obteniendo los puntos de la ciudad cuyo id es" + idCiudad);
		} catch (Exception e) {
			logger.error("Error al obtener los puntos de una ciudad cuyo id es " + idCiudad);
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
			Usuario u = dao.getUsuario(email);
			if (u.getPassword().equals(password)) {
				logger.info("Usuario con email " + email + " se ha logeado correctamente");
				return true;
			} else {
				logger.info("Password incorrecto");
				return false;
			}

		} catch (Exception e) {
			logger.error("Usuario con email " + email + "no existe");
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
	public boolean registerUsuario(Usuario usuario) throws RemoteException {
		try {
			//Usuario usuario = assembler.disassembleUsuario(usuarioDTO);
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
	public ArrayList<UsuarioDTO> getUsuarios() throws RemoteException {
		ArrayList<Usuario> usuarios = dao.getUsuarios();
		logger.info("El cliente ha preguntado por los usuarios");
		return assembler.assembleUsuario(usuarios);
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
	 * @param usuarioDTO
	 *            Data para borrar un usuario
	 * @throws RemoteException
	 *             Lanza una excepcion en caso de que ocurra un error
	 * @result Devuelve true cuando borra un usuario correctamente, false si no.
	 */
	public boolean deleteUsuario(UsuarioDTO usuarioDTO) throws RemoteException {
		try {
			Usuario usuario = assembler.disassembleUsuario(usuarioDTO);
			dao.deleteUsuario(usuario);
			logger.info("Borra un usuario cuyo email es" + usuarioDTO.getEmail());
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
		return dao.getUsuario(email);
	}

	@Override
	public Usuario getUsuario(String email) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
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
