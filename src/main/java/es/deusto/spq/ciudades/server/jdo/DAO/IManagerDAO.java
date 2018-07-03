package es.deusto.spq.ciudades.server.jdo.DAO;

import java.util.ArrayList;

import es.deusto.spq.ciudades.server.jdo.data.Ciudad;
import es.deusto.spq.ciudades.server.jdo.data.CiudadUsuario;
import es.deusto.spq.ciudades.server.jdo.data.Puntuacion;
import es.deusto.spq.ciudades.server.jdo.data.Usuario;
import es.deusto.spq.ciudades.server.jdo.data.UsuarioDTO;

/**
 * 
 * Interfaz del managerDAO, aqui es donde se declaran las funciones propias de la base de datos 
 *
 */
public interface IManagerDAO {

	public void storeCiudad(Ciudad ciudad)throws Exception;
	public ArrayList<Ciudad> getCiudades();
	public void updateCiudad(Ciudad ciudad) throws Exception;
	public void deleteCiudad(String nombreCiudad);
	public int getCiudadPoints(String nombreCiudad);
	public void storePuntuacion(Puntuacion puntuacion) throws Exception;

	public void storeUsuario(Usuario usuario)throws Exception;
	public ArrayList<Usuario> getUsuarios();
	public void deleteUsuario(Usuario usuario) throws Exception;
	public void manageUsuario(Usuario usuario) throws Exception;
	public String getUsuario(String email);
	public ArrayList<CiudadUsuario> getCiudadesPuntuadasPorUsuarios();
	public ArrayList<Puntuacion> getPuntuaciones();
	
	public void puntuarCiudadUsuario(Ciudad ciudad, Usuario usuario) throws Exception;
		
}
