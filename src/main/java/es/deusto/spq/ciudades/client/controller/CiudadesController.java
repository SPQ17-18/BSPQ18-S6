package es.deusto.spq.ciudades.client.controller;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.Logger;

import es.deusto.spq.ciudades.client.remote.CiudadesServiceLocator;
import es.deusto.spq.ciudades.server.jdo.data.CiudadDTO;

public class CiudadesController {
	
	final static Logger logger = Logger.getLogger(CiudadesController.class);
	
	private CiudadesServiceLocator csl;
	
	public CiudadesController(String[] args) throws RemoteException{
		csl = new CiudadesServiceLocator();
		csl.setServices(args[0], args[1], args[2]);
	}

	/**
	 * Inserta una ciudad a la base de datos
	 * @param ciudadDTO Data Container.
	 * @return Devuelte true si se ha insertado correctamente, false si no.
	 */
	public boolean insertCiudad(CiudadDTO ciudadDTO) {
		boolean ciudad = false;
		try {
			ciudad = csl.getService().insertCiudad(ciudadDTO);
		} catch (Exception e) {
			logger.error("Error insertando una nueva ciudad.");
		}
		return ciudad;
	}
	
	/**
	 * Obtener todas las ciudades de la base de datos
	 * @return Ciudades.
	 */
	public List<CiudadDTO> getAllCiudades() {
		List<CiudadDTO> ciudades = null;
		try {
			ciudades = csl.getService().getCiudades();
		} catch (RemoteException e) {
			logger.error("Error al obtener las ciudades del servidor.");
		}
		return ciudades;
	}
}
