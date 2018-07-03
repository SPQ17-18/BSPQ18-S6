package es.deusto.spq.ciudades.server.jdo.data;

import java.io.Serializable;

import javax.jdo.annotations.PersistenceCapable;

/**
 * 
 * Clase ciudadusuario, de la cual obtenemos las puntuaciones de las ciudades, 
 * por los diferentes usuarios que han votado.
 *
 */

@PersistenceCapable
public class CiudadUsuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Ciudad ciudad;
	private Usuario usuario;

	public CiudadUsuario() {

	}

	public CiudadUsuario(Ciudad ciudad, Usuario usuario) {
		this.ciudad = ciudad;
		this.usuario = usuario;
	}

	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void copyCiudadUsuario(CiudadUsuario cu) {
		this.usuario = cu.getUsuario();
		this.ciudad = cu.getCiudad();
	}

}
