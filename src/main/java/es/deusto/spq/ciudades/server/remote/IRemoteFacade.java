package es.deusto.spq.ciudades.server.remote;

import java.rmi.RemoteException;
import java.util.ArrayList;

import es.deusto.spq.ciudades.server.jdo.data.CiudadDTO;
import es.deusto.spq.ciudades.server.jdo.data.UsuarioDTO;

public interface IRemoteFacade {

	public boolean insertCiudad (CiudadDTO ciudadDTO) throws RemoteException;
	public ArrayList<CiudadDTO> getCiudades() throws RemoteException;
	public boolean updateCiudad(CiudadDTO ciudadDTO) throws RemoteException;
	public boolean deleteCiudad (int idCiudad) throws RemoteException;
	public int getCiudadPoints (int idCiudad) throws RemoteException;
	public boolean loginUsuario(String email, String password) throws RemoteException;
	public boolean registerUsuario (UsuarioDTO usuarioDTO) throws RemoteException;
	public ArrayList<UsuarioDTO> getUsuarios() throws RemoteException;
	public boolean updateUsuario (UsuarioDTO usuarioDTO) throws RemoteException;
	public boolean deleteUsuario (UsuarioDTO usuarioDTO) throws RemoteException;
	
}
