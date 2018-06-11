package es.deusto.spq.ciudades.server.jdo.data;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


@PersistenceCapable(detachable="true")
public class Usuario {
	
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


	/**
	 * Metodo para establecer el nombre del usuario
	 * 
	 * @param nombre
	 * 			Nombre del usuario
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
	 * 				Apellido del usuario
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
	 * 				Contraseña del usuario
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	

}
