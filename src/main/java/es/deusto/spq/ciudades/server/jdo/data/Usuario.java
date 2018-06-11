package es.deusto.spq.ciudades.server.jdo.data;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;


@PersistenceCapable(detachable="true")
public class Usuario {
	
	@PrimaryKey
	private String email;
	private String nombre;
	private String apellido;
	private String password;
	
	/**
	 * Constructor vacio
	 */
	public Usuario() {
		
	}

	
	/**
	 * Constructor para el usuario
	 * 
	 * @param email
	 * 				Email del usuario 
	 * @param nombre
	 * 				Nombre del usuario
	 * @param apellido
	 * 				Apellido del usuario
	 * @param password
	 * 				Contrasenia del usuario
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
	 * 			Email del usuario
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


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getApellido() {
		return apellido;
	}


	public void setApellido(String apellido) {
		this.apellido = apellido;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	
	

}
