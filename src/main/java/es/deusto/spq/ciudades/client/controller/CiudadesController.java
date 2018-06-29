package es.deusto.spq.ciudades.client.controller;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.Logger;

import es.deusto.spq.ciudades.server.jdo.data.CiudadDTO;
import es.deusto.spq.ciudades.server.jdo.data.Usuario;
import es.deusto.spq.ciudades.server.jdo.data.UsuarioDTO;
import es.deusto.spq.ciudades.server.remote.IRemoteFacade;

public class CiudadesController {

	final static Logger logger = Logger.getLogger(CiudadesController.class);

	private IRemoteFacade csl;

	public CiudadesController(IRemoteFacade collector) throws RemoteException {
		this.csl = collector;
	}

	/**
	 * Inserta una ciudad a la base de datos
	 * 
	 * @param ciudadDTO
	 *            Data Container.
	 * @return Devuelte true si se ha insertado correctamente, false si no.
	 */
	public boolean insertCiudad(CiudadDTO ciudadDTO) {
		boolean ciudad = false;
		try {
			ciudad = csl.insertCiudad(ciudadDTO);
		} catch (Exception e) {
			logger.error("Error insertando una nueva ciudad.");
		}
		return ciudad;
	}

	/**
	 * Obtener todas las ciudades de la base de datos
	 * 
	 * @return Ciudades.
	 */
	public List<CiudadDTO> getAllCiudades() {
		List<CiudadDTO> ciudades = null;
		try {
			ciudades = csl.getCiudades();
		} catch (RemoteException e) {
			logger.error("Error al obtener las ciudades del servidor.");
		}
		return ciudades;
	}

	/**
	 * Actualiza una ciudad
	 * 
	 * @param ciudadDTO
	 *            Data container.
	 * @return Devuelve true si se ha actualizado correctamente, false si no.
	 */
	public boolean updateCiudad(CiudadDTO ciudadDTO) {
		boolean updated = false;
		try {
			updated = csl.updateCiudad(ciudadDTO);
		} catch (RemoteException e) {
			logger.error("Error actualizando una ciudad.");
		}
		return updated;
	}

	/**
	 * Borra una ciudad.
	 * 
	 * @param idCiudad
	 *            El id de una ciudad.
	 * @return Devuelte true si se ha borrado correctamente, false si no.
	 */
	public boolean deleteCiudad(int idCiudad) {
		boolean deleted = false;
		try {
			deleted = csl.deleteCiudad(idCiudad);
		} catch (RemoteException e) {
			logger.error("Error al borrar una ciudad.");
		}
		return deleted;
	}

	/**
	 * Obtiene la puntuacion actual de una ciudad.
	 * 
	 * @param idCiudad
	 *            El id de una ciudad.
	 * @return Los puntos de una ciudad.
	 */
	public int getCiudadPoints(int idCiudad) {
		int points = -1;
		try {
			points = csl.getCiudadPoints(idCiudad);
		} catch (RemoteException e) {
			logger.error("Error al obtener los puntos de una ciudad.");
		}
		return points;
	}

	/**
	 * Identifica un usuario.
	 * 
	 * @param email
	 *            Email del usuario.
	 * @param password
	 *            Password del usuario.
	 * @return Devuelve true si ha identificado correctamente el usuario, false si
	 *         no.
	 */
	public boolean identifyUsuario(String email, String password) {
		boolean login = false;
		try {
			
			login = csl.loginUsuario(email, password);
		} catch (RemoteException e) {
			logger.error("Error al identificar un usuario.");
		}
		return login;
	}

	/**
	 * Metodo DevolverUsuario del controlador, que se encarga de llamar al metodo de
	 * devuelveUsuario (pasandole un email)
	 * 
	 * @param email
	 *            - String
	 * @return Usuario - Devuelve un usuario
	 * @throws RemoteException
	 *             lanza excepcion
	 */
	public Usuario DevolverUsuario(String email) throws RemoteException {
		return csl.devolverUsuario(email);
	}

	/**
	 * Registra un usuario.
	 * 
	 * @param usuarioDTO
	 *            Data Container.
	 * @return Devuelve true si registra correctamente al usuario, false si no.
	 */
	public boolean registerUsuario(Usuario usuario) {
		boolean registered = false;
		try {
			registered = csl.registerUsuario(usuario);
		} catch (RemoteException e) {
			logger.error("Error al registrar un usuario.");
		}
		return registered;
	}

	/**
	 * Obtiene todos los usuarios del servidor
	 * 
	 * @return usuarios. Devuelve los usuarios que se han registrado
	 */
	public List<UsuarioDTO> getAllUsuarios() {
		List<UsuarioDTO> usuarios = null;
		try {
			usuarios = csl.getUsuarios();
		} catch (RemoteException e) {
			logger.error("Error al obtener los usuarios del servidor.");
		}
		return usuarios;
	}

	/**
	 * Actualizar un usuario
	 * 
	 * @param usuarioDTO
	 *            Data Container.
	 * @return Devuelve true si se ha actualizado el usuario, false si no .
	 */
	public boolean updateUsuario(UsuarioDTO usuarioDTO) {
		boolean updated = false;
		try {
			updated = csl.updateUsuario(usuarioDTO);
		} catch (RemoteException e) {
			logger.error("Error al actualizar un usuario.");
		}
		return updated;
	}

	/**
	 * Borra un usuario
	 * 
	 * @param usuarioDTO
	 *            Data Container.
	 * @return Devuelve true si el usuario se ha borrado correctamente, false si no.
	 */
	public boolean deleteUsuario(UsuarioDTO usuarioDTO) {
		boolean deleted = false;
		try {
			deleted = csl.deleteUsuario(usuarioDTO);
		} catch (RemoteException e) {
			logger.error("Error al borrar un usuario.");
		}
		return deleted;
	}

}
