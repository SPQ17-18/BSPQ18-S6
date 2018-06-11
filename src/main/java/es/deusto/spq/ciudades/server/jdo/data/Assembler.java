package es.deusto.spq.ciudades.server.jdo.data;

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

	
}
