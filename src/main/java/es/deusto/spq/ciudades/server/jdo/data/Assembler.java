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
		c.setIdCiudad(ciudadDTO.getIdCiudad());
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
	 *            ArrayList de ciudades que queremos transformar a un Arraylist de CiudadDTOs
	 * @return Devuelve un ArrayList de ciudades transformadas en un ArrayList de CiudadDTOs
	 */
	public ArrayList<CiudadDTO> assembleCiudad(ArrayList<Ciudad> ciudades) {
		ArrayList<CiudadDTO> ciudadDTO = new ArrayList<CiudadDTO>();
		for (int i = 0; i < ciudades.size(); i++) {
			CiudadDTO cDTO = new CiudadDTO(ciudades.get(i).getIdCiudad(), ciudades.get(i).getNombreCiudad(), ciudades.get(i).getPais(), ciudades.get(i).getPuntuacionTotal(), ciudades.get(i).getPuntuacionOcio(), ciudades.get(i).getPuntuacionGastronomia(), ciudades.get(i).getPuntuacionCultura(), ciudades.get(i).getPuntuacionTransporte(), ciudades.get(i).getNumVotantes());
			ciudadDTO.add(cDTO);
		}
		return ciudadDTO;
	}
	
	/**
	 * Metodo para transformar un UsuarioDTO a un usuario
	 * 
	 * @param usuarioDTO
	 *            UsuarioDTO que queremos transformar
	 * @return Devuelve el UsuarioDTO transformado a usuario
	 */
	public Usuario disassembleUsuario(UsuarioDTO usuarioDTO) {
		Usuario u= new Usuario();
		u.setApellido(usuarioDTO.getApellido());
		u.setEmail(usuarioDTO.getEmail());
		u.setNombre(usuarioDTO.getNombre());
		u.setPassword(usuarioDTO.getPassword());
		return u;
	}

	
}
