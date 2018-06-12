package es.deusto.spq.ciudades.server.jdo.DAO;

import java.util.ArrayList;

import es.deusto.spq.ciudades.server.jdo.data.Ciudad;

public interface IManagerDAO {

	public void storeCiudad(Ciudad ciudad)throws Exception;
	public ArrayList<Ciudad> getCiudades();
}
