package es.deusto.spq.ciudades.server.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import es.deusto.spq.ciudades.server.jdo.data.Ciudad;
import es.deusto.spq.ciudades.server.jdo.data.CiudadDTO;
import es.deusto.spq.ciudades.server.jdo.data.CiudadUsuario;
import es.deusto.spq.ciudades.server.jdo.data.CiudadUsuarioDTO;
import es.deusto.spq.ciudades.server.jdo.data.Puntuacion;
import es.deusto.spq.ciudades.server.jdo.data.PuntuacionDTO;
import es.deusto.spq.ciudades.server.jdo.data.Usuario;
import es.deusto.spq.ciudades.server.jdo.data.UsuarioDTO;

/**
 * 
 * Interfaz de la fachada la cual relaciona el cliente y el servidor.
 * 
 * Esta interfaz se comunica con el controller del cliente y a su vez con el propio servidor.
 *
 */
public interface IRemoteFacade extends Remote{

	public boolean insertCiudad (CiudadDTO ciudadDTO) throws RemoteException;
	public ArrayList<CiudadDTO> getCiudades() throws RemoteException;
	public boolean updateCiudad(CiudadDTO ciudadDTO) throws RemoteException;
	public boolean deleteCiudad (String nombreCiudad) throws RemoteException;
	public int getCiudadPoints (String nombreCiudad) throws RemoteException;
	public boolean loginUsuario(String email, String password) throws RemoteException;
	public Usuario devolverUsuario(String email) throws RemoteException;
	public boolean registerUsuario (UsuarioDTO usuario) throws RemoteException;
	public ArrayList<Usuario> getUsuarios() throws RemoteException;
	public boolean updateUsuario (UsuarioDTO usuarioDTO) throws RemoteException;
	public boolean deleteUsuario (Usuario usuario) throws RemoteException;
	public boolean puntuarCiudadUsuario(Ciudad ciudad, Usuario usuario) throws RemoteException;
	public ArrayList<CiudadUsuarioDTO> getCiudadesPuntuadasPorUsuarios()  throws RemoteException;
	public boolean puntuarCiudad(PuntuacionDTO puntuacionDTO) throws RemoteException;
	public ArrayList<PuntuacionDTO> getPuntuaciones() throws RemoteException;
	
	
}
