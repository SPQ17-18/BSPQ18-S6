package es.deusto.spq.ciudades.server.jdo.DAO;

import java.util.ArrayList;

import es.deusto.spq.ciudades.server.jdo.data.Ciudad;
import es.deusto.spq.ciudades.server.jdo.data.CiudadUsuario;
import es.deusto.spq.ciudades.server.jdo.data.Usuario;
import es.deusto.spq.ciudades.server.jdo.data.UsuarioDTO;

public interface IManagerDAO {

	public void storeCiudad(Ciudad ciudad)throws Exception;
	public ArrayList<Ciudad> getCiudades();
	public void updateCiudad(Ciudad ciudad) throws Exception;
	public void deleteCiudad(String nombreCiudad);
	public int getCiudadPoints(String nombreCiudad);

	public void storeUsuario(Usuario usuario)throws Exception;
	public ArrayList<Usuario> getUsuarios();
	public void deleteUsuario(Usuario usuario) throws Exception;
	public void manageUsuario(Usuario usuario) throws Exception;
	public String getUsuario(String email);
	public ArrayList<CiudadUsuario> getCiudadesPuntuadasPorUsuarios();
	
	public void puntuarCiudadUsuario(Ciudad ciudad, Usuario usuario) throws Exception;
		
}
