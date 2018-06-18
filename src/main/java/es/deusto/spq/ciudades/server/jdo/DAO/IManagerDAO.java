package es.deusto.spq.ciudades.server.jdo.DAO;

import java.util.ArrayList;

import es.deusto.spq.ciudades.server.jdo.data.Ciudad;
import es.deusto.spq.ciudades.server.jdo.data.Usuario;

public interface IManagerDAO {

	public void storeCiudad(Ciudad ciudad)throws Exception;
	public ArrayList<Ciudad> getCiudades();
	public void updateCiudad(Ciudad ciudad) throws Exception;
	public void deleteCiudad(int idCiudad);
	public int getCiudadPoints(int idCiudad);
	public Usuario getUsuario (String email);
	public void storeUsuario(Usuario usuario)throws Exception;
	public ArrayList<Usuario> getUsuarios();
	public void deleteUsuario(Usuario usuario) throws Exception;
	public void manageUsuario(Usuario usuario) throws Exception;
		
}
