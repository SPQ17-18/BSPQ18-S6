package es.deusto.spq.ciudades.server.jdo.data;


public class UsuarioDTO {

	private String email;
	private String nombre;
	private String apellido;
	private String password;

	/**
	 * Constructor vacio
	 */
	public UsuarioDTO() {
		
	}

	
	/**
	 * Constructor para el usuarioDTO
	 * 
	 * @param email
	 * 				Email del usuarioDTO 
	 * @param nombre
	 * 				Nombre del usuarioDTO
	 * @param apellido
	 * 				Apellido del usuarioDTO
	 * @param password
	 * 				Contrasenia del usuarioDTO
	 */
	public UsuarioDTO(String email, String nombre, String apellido, String password) {
		super();
		this.email = email;
		this.nombre = nombre;
		this.apellido = apellido;
		this.password = password;
	}


	
	/**
	 * Metodo para obtener el email del usuarioDTO
	 * 
	 * @return Devuelve el email del usuarioDTO
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * Metodo para establecer el mail del usuarioDTO
	 * 
	 * @param email
	 * 			Email del usuarioDTO
	 * 
	 */
	public void setEmail(String email) {
		this.email = email;
	}


	/**
	 * Metodo para obtener el nombre del usuarioDTO 
	 * 
	 * @return Devuelve el nombre del usuarioDTO
	 */
	public String getNombre() {
		return nombre;
	}


	/**
	 * Metodo para establecer el nombre del usuarioDTO
	 * 
	 * @param nombre
	 * 			Nombre del usuarioDTO
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	/**
	 * Metodo para obtener el apellido del usuarioDTO
	 * 
	 * 
	 * @return Devuelve el apellido del usuarioDTO
	 * 
	 */
	public String getApellido() {
		return apellido;
	}


	/**
	 * Metodo para establecer el apellido del usuarioDTO 
	 * 
	 * @param apellido
	 * 				Apellido del usuarioDTO
	 */
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}


	/**
	 * Metodo para obtener la contraseña del usuarioDTO
	 * 
	 * @return Devuelve la contraseña del usuarioDTO
	 */
	public String getPassword() {
		return password;
	}


	/**
	 * Metodo para establecer la contraseña del usuarioDTO
	 * 
	 * @param password
	 * 				Contraseña del usuarioDTO
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	
	
	
}
