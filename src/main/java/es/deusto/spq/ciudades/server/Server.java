package es.deusto.spq.ciudades.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import es.deusto.spq.ciudades.server.jdo.DAO.IManagerDAO;
import es.deusto.spq.ciudades.server.jdo.data.Assembler;
import es.deusto.spq.ciudades.server.jdo.data.Ciudad;
import es.deusto.spq.ciudades.server.jdo.data.CiudadDTO;
import es.deusto.spq.ciudades.server.remote.IRemoteFacade;

public class Server extends UnicastRemoteObject implements IRemoteFacade {

	//
	protected Server() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(Server.class);
	
	private IManagerDAO dao;
	private Assembler assembler;
	
	
	/**
	 * Metodo para insertar una ciudad
	 * 
	 * @param ciudadDTO
	 *            Data para insertar la ciudad
	 * @throws RemoteException
	 * 				Lanza una excepcion en caso de que ocurra un error
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
	
}
