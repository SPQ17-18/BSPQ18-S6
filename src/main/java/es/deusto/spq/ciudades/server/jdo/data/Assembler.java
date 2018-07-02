package es.deusto.spq.ciudades.server.jdo.data;

import java.util.ArrayList;

import es.deusto.spq.ciudades.server.jdo.DAO.ManagerDAO;

/**
 * Clase destinada para transformar objetos DTO
 *
 */
public class Assembler {

	/**
	 * Manager para la base de datos
	 */
	private ManagerDAO dao;

	/**
	 * Constructor para el assembler con ManagerDAO, la base de datos
	 */
	public Assembler() {
		this.dao = new ManagerDAO();
	}

	/**
	 * Metodo para transformar CiudadDTO a Ciudad
	 * 
	 * @param ciudadDTO
	 *            CiudadDTO para transformarlo a ciudad
	 * @return Devuelve CiudadDTO transformado en ciudad
	 */
	public Ciudad disassembleCiudad(CiudadDTO ciudadDTO) {
		Ciudad c = new Ciudad();
		c.setNombreCiudad(ciudadDTO.getNombreCiudad());
		c.setNumVotantes(ciudadDTO.getNumVotantes());
		c.setPais(ciudadDTO.getPais());
		c.setPuntuacionCultura(ciudadDTO.getPuntuacionCultura());
		c.setPuntuacionGastronomia(ciudadDTO.getPuntuacionGastronomia());
		c.setPuntuacionGastronomia(ciudadDTO.getPuntuacionGastronomia());
		c.setPuntuacionOcio(ciudadDTO.getPuntuacionOcio());
		c.setPuntuacionTotal(ciudadDTO.getPuntuacionTotal());
		c.setPuntuacionTransporte(ciudadDTO.getPuntuacionTransporte());
		return c;
	}

	/**
	 * Metodo para transformar Ciudad a CiudadDTO
	 * 
	 * @param ciudades
	 *            ArrayList de ciudades que queremos transformar a un Arraylist de
	 *            CiudadDTOs
	 * @return Devuelve un ArrayList de ciudades transformadas en un ArrayList de
	 *         CiudadDTOs
	 */
	public ArrayList<CiudadDTO> assembleCiudad(ArrayList<Ciudad> ciudades) {
		ArrayList<CiudadDTO> ciudadDTO = new ArrayList<CiudadDTO>();
		for (int i = 0; i < ciudades.size(); i++) {
			CiudadDTO cDTO = new CiudadDTO(ciudades.get(i).getNombreCiudad(), ciudades.get(i).getPais(),
					ciudades.get(i).getPuntuacionTotal(), ciudades.get(i).getPuntuacionOcio(),
					ciudades.get(i).getPuntuacionGastronomia(), ciudades.get(i).getPuntuacionCultura(),
					ciudades.get(i).getPuntuacionTransporte(), ciudades.get(i).getNumVotantes());
			ciudadDTO.add(cDTO);
		}
		return ciudadDTO;
	}

	public ArrayList<CiudadUsuarioDTO> assembleCiudadUsuario(ArrayList<CiudadUsuario> ciudadesUsaurio) {
		ArrayList<CiudadUsuarioDTO> ciudadUsuarioDTO = new ArrayList<CiudadUsuarioDTO>();
		for (int i = 0; i < ciudadesUsaurio.size(); i++) {
			CiudadUsuarioDTO cDTO = new CiudadUsuarioDTO(ciudadesUsaurio.get(i).getCiudad(),
					ciudadesUsaurio.get(i).getUsuario());
			ciudadUsuarioDTO.add(cDTO);
		}
		return ciudadUsuarioDTO;
	}

	/**
	 * Metodo para transformar UsuarioDTO a Usuario
	 * 
	 * @param usuarioDTO
	 *            UsuarioDTO que queremos transformar
	 * @return Devuelve los datos del UsuarioDTO transformado a Usuario
	 */
	public Usuario disassembleUsuario(UsuarioDTO usuarioDTO) {
		Usuario u = new Usuario();
		u.setApellido(usuarioDTO.getApellido());
		u.setEmail(usuarioDTO.getEmail());
		u.setNombre(usuarioDTO.getNombre());
		u.setPassword(usuarioDTO.getPassword());
		return u;
	}

	/**
	 * Metodo para transformar Usuarios a UsuarioDTOs
	 * 
	 * @param usuarios
	 *            ArrayList de usuarios a transformar
	 * @return Devuelve el ArrayList de usuarios transformados a un ArrayList de
	 *         UsuarioDTOs
	 * 
	 */
	public ArrayList<UsuarioDTO> assembleUsuario(ArrayList<Usuario> usuarios) {
		ArrayList<UsuarioDTO> usuarioDTO = new ArrayList<UsuarioDTO>();
		for (int i = 0; i < usuarios.size(); i++) {
			UsuarioDTO uDTO = new UsuarioDTO(usuarios.get(i).getApellido(), usuarios.get(i).getEmail(),
					usuarios.get(i).getNombre(), usuarios.get(i).getPassword());
			usuarioDTO.add(uDTO);
		}
		return usuarioDTO;
	}

	/**
	 * Metodo para transformar Usuario a UsuarioDTO
	 * 
	 * @param usuario
	 *            usuario a transformar
	 * @return Devuelve un usuario transformados a un UsuarioDTO
	 * 
	 */
	public UsuarioDTO assembleUnUsuario(Usuario usuario) {
		UsuarioDTO usuarioDTO = new UsuarioDTO();

		UsuarioDTO uDTO = new UsuarioDTO(usuario.getApellido(), usuario.getEmail(), usuario.getNombre(),
				usuario.getPassword());

		return usuarioDTO;
	}

}
