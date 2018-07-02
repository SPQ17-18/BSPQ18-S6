package es.deusto.spq.ciudades.client.controller;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.Logger;

import es.deusto.spq.ciudades.server.jdo.data.Ciudad;
import es.deusto.spq.ciudades.server.jdo.data.CiudadDTO;
import es.deusto.spq.ciudades.server.jdo.data.CiudadUsuario;
import es.deusto.spq.ciudades.server.jdo.data.CiudadUsuarioDTO;
import es.deusto.spq.ciudades.server.jdo.data.PuntuacionDTO;
import es.deusto.spq.ciudades.server.jdo.data.Usuario;
import es.deusto.spq.ciudades.server.jdo.data.UsuarioDTO;
import es.deusto.spq.ciudades.server.remote.IRemoteFacade;

public class CiudadesController {

	final static Logger logger = Logger.getLogger(CiudadesController.class);

	private IRemoteFacade iRF;

	public CiudadesController(IRemoteFacade collector) throws RemoteException {
		this.iRF = collector;
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
			ciudad = iRF.insertCiudad(ciudadDTO);
		} catch (Exception e) {
			logger.error("Error insertando una nueva ciudad.");
		}
		return ciudad;
	}

	public boolean insertarPuntuacion(PuntuacionDTO puntuacionDTO) {
		boolean puntuada = false;
		try {
			puntuada = iRF.puntuarCiudad(puntuacionDTO);
		} catch (Exception e) {
			logger.error("Error insertando una nueva ciudad.");
		}
		return puntuada;
	}

	/**
	 * Obtener todas las ciudades de la base de datos
	 * 
	 * @return Ciudades.
	 */
	public List<CiudadDTO> getAllCiudades() {
		List<CiudadDTO> ciudades = null;
		try {
			ciudades = iRF.getCiudades();
		} catch (RemoteException e) {
			logger.error("Error al obtener las ciudades del servidor.");
		}
		return ciudades;
	}

	public List<CiudadUsuarioDTO> getCiudadesPuntuadasPorUsuarios() {
		List<CiudadUsuarioDTO> ciudades = null;
		try {
			ciudades = iRF.getCiudadesPuntuadasPorUsuarios();
		} catch (RemoteException e) {
			logger.error("Error al obtener las getCiudadesPuntuadasPorUsuarios del servidor.");
		}
		return ciudades;
	}

	public List<PuntuacionDTO> getPuntuaciones() {
		List<PuntuacionDTO> puntuaciones = null;
		try {
			puntuaciones = iRF.getPuntuaciones();
		} catch (RemoteException e) {
			logger.error("Error al obtener las getPuntuaciones del servidor.");
		}
		return puntuaciones;
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
			updated = iRF.updateCiudad(ciudadDTO);
		} catch (RemoteException e) {
			logger.error("Error actualizando una ciudad.");
		}
		return updated;
	}

	/**
	 * Borra una ciudad.
	 * 
	 * @param nombreCiudad
	 *            El id de una ciudad.
	 * @return Devuelte true si se ha borrado correctamente, false si no.
	 */
	public boolean deleteCiudad(String nombreCiudad) {
		boolean deleted = false;
		try {
			deleted = iRF.deleteCiudad(nombreCiudad);
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
	public int getCiudadPoints(String nombreCiudad) {
		int points = -1;
		try {
			points = iRF.getCiudadPoints(nombreCiudad);
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

			login = iRF.loginUsuario(email, password);
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
		return iRF.devolverUsuario(email);
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
			registered = iRF.registerUsuario(usuario);
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
	public List<Usuario> getAllUsuarios() {
		List<Usuario> usuarios = null;
		try {
			usuarios = iRF.getUsuarios();
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
			updated = iRF.updateUsuario(usuarioDTO);
		} catch (RemoteException e) {
			logger.error("Error al actualizar un usuario.");
		}
		return updated;
	}

	/**
	 * Borra un usuario
	 * 
	 * @param usuario
	 *            Data Container.
	 * @return Devuelve true si el usuario se ha borrado correctamente, false si no.
	 */
	public boolean deleteUsuario(Usuario usuario) {
		boolean deleted = false;
		try {
			deleted = iRF.deleteUsuario(usuario);
		} catch (RemoteException e) {
			logger.error("Error al borrar un usuario.");
		}
		return deleted;
	}

	public boolean puntuarCiudadUsuario(Ciudad ciudad, Usuario usuario) {
		boolean dev = false;
		try {
			System.out.println(usuario.getEmail() + " ciudades controller!");
			dev = iRF.puntuarCiudadUsuario(ciudad, usuario);
		} catch (RemoteException e) {
			logger.error("Error al guradar puntuación de una ciudad!.");
		}
		return dev;
	}

}
