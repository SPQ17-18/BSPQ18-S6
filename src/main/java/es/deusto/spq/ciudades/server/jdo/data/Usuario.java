package es.deusto.spq.ciudades.server.jdo.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable = "true")
public class Usuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@PrimaryKey
	private String email;
	private String nombre;
	private String apellido;
	private String password;

	@Persistent(defaultFetchGroup = "true", mappedBy = "usuario", dependentElement = "true")
	@Join
	private List<Ciudad> ciudadesVisitadas = new ArrayList<>();

	/**
	 * Constructor vacio
	 */
	public Usuario() {

	}

	/**
	 * Constructor para el usuario
	 * 
	 * @param email
	 *            Email del usuario
	 * @param nombre
	 *            Nombre del usuario
	 * @param apellido
	 *            Apellido del usuario
	 * @param password
	 *            Contrasenia del usuario
	 */
	public Usuario(String email, String nombre, String apellido, String password) {
		super();
		this.email = email;
		this.nombre = nombre;
		this.apellido = apellido;
		this.password = password;
	}

	/**
	 * Metodo para obtener el email del usuario
	 * 
	 * @return Devuelve el email del usuario
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Metodo para establecer el mail del usuario
	 * 
	 * @param email
	 *            Email del usuario
	 * 
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Metodo para obtener el nombre del usuario
	 * 
	 * @return Devuelve el nombre del usuario
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Metodo para establecer el nombre del usuario
	 * 
	 * @param nombre
	 *            Nombre del usuario
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Metodo para obtener el apellido del usuario
	 * 
	 * 
	 * @return Devuelve el apellido del usuario
	 * 
	 */
	public String getApellido() {
		return apellido;
	}

	/**
	 * Metodo para establecer el apellido del usuario
	 * 
	 * @param apellido
	 *            Apellido del usuario
	 */
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	/**
	 * Metodo para obtener la contraseña del usuario
	 * 
	 * @return Devuelve la contraseña del usuario
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Metodo para establecer la contraseña del usuario
	 * 
	 * @param password
	 *            Contraseña del usuario
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Metodo para obtener las ciudades que ha puntuado/visitado un usuario
	 * 
	 * @return Devuelve la lista de ciudades que ha puntuado un usuario
	 */
	public List<Ciudad> getCiudadesPuntuadas() {
		return ciudadesVisitadas;
	}

	/**
	 * Metodo para establecer las ciudades puntuadas de un usuario
	 * 
	 * @param ciudades
	 *            Ciudades que han sido puntuadas por un usuario
	 */
	public void setCiudades(List<Ciudad> ciudades) {
		this.ciudadesVisitadas = ciudades;
	}

	/**
	 * Metodo para añadir una ciudad a un usuario
	 * 
	 * @param c
	 *            Ciudad que queremos añadir
	 */
	public void addCiudad(Ciudad c) {
		ciudadesVisitadas.add(c);
		c.setUsuario(this);
	}

	/**
	 * Metodo para copiar un usuario de la base de datos
	 * 
	 * @param u
	 *            Usuario del cual copiamos sus datos
	 */
	public void copyUsuario(Usuario u) {
		this.apellido = u.getApellido();
		this.email = u.getEmail();
		this.nombre = u.getNombre();
		this.password = u.getPassword();
		for (int i = 0; i < u.getCiudadesPuntuadas().size(); i++) {
			this.ciudadesVisitadas.add(new Ciudad());
			this.ciudadesVisitadas.get(i).copyCiudad(u.getCiudadesPuntuadas().get(i));
		}
	}

}
