package es.deusto.spq.ciudades.server.remote;

import java.rmi.RemoteException;
import java.util.ArrayList;

import es.deusto.spq.ciudades.server.jdo.data.CiudadDTO;

public interface IRemoteFacade {

	public boolean insertCiudad (CiudadDTO ciudadDTO) throws RemoteException;
	public ArrayList<CiudadDTO> getCiudades() throws RemoteException;
}
