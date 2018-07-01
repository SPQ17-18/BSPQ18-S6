package es.deusto.spq.ciudades.server.jdo.data;

import java.io.Serializable;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
public class CiudadUsuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Ciudad ciudad;
	private Usuario usuario;

	public CiudadUsuario(Ciudad ciudad, Usuario usuario) {
		super();
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

}
