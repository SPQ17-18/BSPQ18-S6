package es.deusto.spq.ciudades.server.jdo.data;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable = "true")

public class Ciudad {
	
	@PrimaryKey
	private int idCiudad;
	
	private String nombreCiudad;
	private String pais;
	private int puntuacionTotal;
	private int puntuacionOcio;
	private int puntuacionGastronomia;
	private int puntuacionCultura;
	private int puntuacionTransporte;
	private int numVotantes;
	
	@Persistent(defaultFetchGroup = "true")
	private Usuario usuario;
	
	/**
	 * Constructor vacio
	 */
	public Ciudad() {
		
	}
	
	
	
	/**
	 * Constructor para la ciudad
	 * 
	 * @param idCiudad
	 * 				Identificador de la ciudad
	 * @param nombreCiudad
	 * 				Nombre de la ciudad
	 * @param pais
	 * 				Pais de la ciudad
	 * @param puntuacionTotal
	 * 				Puntuacion total de la ciudad
	 * @param puntuacionOcio
	 * 				Puntuacion en el aspecto de ocio de la ciudad
	 * @param puntuacionGastronomia
	 * 				Puntuacion en el aspecto de gastronomia de la ciudad
	 * @param puntuacionCultura
	 * 				Puntuacion en el aspecto de cultura de la ciudad 
	 * @param puntuacionTransporte
	 * 				Puntuacion en el aspecto transporte de la ciudad
	 * @param numVotantes
	 * 				Numero total de visitantes de la ciudad
	 */
	public Ciudad(int idCiudad, String nombreCiudad, String pais, int puntuacionTotal, int puntuacionOcio,
			int puntuacionGastronomia, int puntuacionCultura, int puntuacionTransporte, int numVotantes) {
		super();
		this.idCiudad = idCiudad;
		this.nombreCiudad = nombreCiudad;
		this.pais = pais;
		this.puntuacionTotal = puntuacionTotal;
		this.puntuacionOcio = puntuacionOcio;
		this.puntuacionGastronomia = puntuacionGastronomia;
		this.puntuacionCultura = puntuacionCultura;
		this.puntuacionTransporte = puntuacionTransporte;
		this.numVotantes = numVotantes;
	}



	/**
	 * Metodo para obtener el identificador de la ciudad
	 * 
	 * @return Devuelve el identificador de la ciudad
	 */
	public int getIdCiudad() {
		return idCiudad;
	}
	
	/**
	 * Metodo para establecer el identificador de la ciudad
	 * 
	 * @param idCiudad
	 * 				Identificador de la ciudad
	 */
	public void setIdCiudad(int idCiudad) {
		this.idCiudad = idCiudad;
	}
	
	/**
	 * Metodo para obtener el nombre de la ciudad
	 *  
	 * @return Devuelve el nombre de la ciudad
	 * 			
	 */
	public String getNombreCiudad() {
		return nombreCiudad;
	}
	
	/**
	 * Metodo para establecer el nombre de la ciudad
	 * 
	 * 
	 * @param nombreCiudad
	 * 				Nombre de la ciudad
	 */
	public void setNombreCiudad(String nombreCiudad) {
		this.nombreCiudad = nombreCiudad;
	}
	
	/**
	 * Metodo para obtener el nombre del pais de la ciudad
	 * 
	 * @return Devuelve el nombre del pais de la ciudad
	 */
	public String getPais() {
		return pais;
	}
	
	/**
	 * Metodo para establecer el pais de la ciudad
	 * 
	 * @param pais
	 * 			El nombre del pais de la ciudad
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}
	
	/**
	 * Metodo para obtener la puntuacion total de la ciudad
	 * 
	 * @return Devuelve la puntuacion total de la ciudad
	 */
	public int getPuntuacionTotal() {
		return puntuacionTotal;
	}
	
	/**
	 * Metodo para establecer la puntuacion total de la ciudad
	 * 
	 * @param puntuacionTotal
	 * 			La puntuacion total de la ciudad
	 */
	public void setPuntuacionTotal(int puntuacionTotal) {
		this.puntuacionTotal = puntuacionTotal;
	}
	
	/**
	 * Metodo para obtener la puntuacion en el aspecto ocio de la ciudad
	 * 
	 * @return Devuelve la puntuacion en el aspecto ocio de la ciudad 
	 */
	public int getPuntuacionOcio() {
		return puntuacionOcio;
	}
	
	/**
	 * Metodo para establecer la puntuacion en el aspecto ocio de la ciudad
	 * 
	 * @param puntuacionOcio
	 * 					Puntuacion en el aspecto ocio de la ciudad
	 */
	public void setPuntuacionOcio(int puntuacionOcio) {
		this.puntuacionOcio = puntuacionOcio;
	}
	
	/**
	 * Metodo para obtener la puntuacion en el aspecto de la gastronomia de la ciudad
	 * 
	 * @return Devuelve la puntuacion en el aspecto de la gastronomia de la ciudad
	 */
	public int getPuntuacionGastronomia() {
		return puntuacionGastronomia;
	}
	
	/**
	 * Metodo para establecer la puntuacion en el aspecto de la gastronomia de la ciudad
	 * 
	 * @param puntuacionGastronomia
	 * 					Puntuacion en el aspecto de la gastronomia de la ciudad
	 */
	public void setPuntuacionGastronomia(int puntuacionGastronomia) {
		this.puntuacionGastronomia = puntuacionGastronomia;
	}
	
	/**
	 * Metodo para obtener la puntuacion en el aspecto de la cultura de la ciudad
	 * 
	 * @return Devuelve la puntuacion de la cultura de la ciudad
	 */
	public int getPuntuacionCultura() {
		return puntuacionCultura;
	}
	
	/**
	 * Metodo para establecer la puntuacion en el aspecto de la cultura de la ciudad
	 * 
	 * @param puntuacionCultura
	 * 				Puntuacion en el aspecto cultura de la ciudad
	 */
	public void setPuntuacionCultura(int puntuacionCultura) {
		this.puntuacionCultura = puntuacionCultura;
	}
	
	/**
	 * Metodo para obtener la puntuacion en el aspecto de transporte de la ciudad
	 * 
	 * @return Devuelve la puntuacion en el aspecto transporte de la ciudad
	 * 				
	 */
	public int getPuntuacionTransporte() {
		return puntuacionTransporte;
	}
	
	/**
	 * Metodo para establecer la puntuacion en el aspecto transporte de la ciudad
	 * 
	 * @param puntuacionTransporte
	 * 					Puntuacion en el aspecto transporte de la ciudad
	 */
	public void setPuntuacionTransporte(int puntuacionTransporte) {
		this.puntuacionTransporte = puntuacionTransporte;
	}
	
	/**
	 * Metodo para obtener el numero de votantes de una ciudad.
	 * Un usuario solo puede votar una vez a una ciudad, a no ser que borre su puntuacion.
	 * 
	 * @return Devuelve el numero de votantes que tiene una ciudad
	 * 				
	 */
	public int getNumVotantes() {
		return numVotantes;
	}
	
	/**
	 * Metodo para establecer el numero de votantes de una ciudad
	 * 
	 * @param numVotantes
	 * 				Numero votantes de una ciudad
	 */
	public void setNumVotantes(int numVotantes) {
		this.numVotantes = numVotantes;
	}
	
	/**
	 * Metodo para establecer un usuario
	 * 
	 * @param usuario
	 *            Member who bought the ticket
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	/**
	 * Metodo para copiar la ciudad a la base de datos
	 * 
	 * @param c
	 *            Ciudad que queremos copiar
	 */
	public void copyCiudad(Ciudad c) {
		this.idCiudad=c.getIdCiudad();
		this.nombreCiudad=c.getNombreCiudad();
		this.numVotantes=c.getNumVotantes();
		this.pais=c.getPais();
		this.puntuacionCultura=c.getPuntuacionCultura();
		this.puntuacionGastronomia=c.getPuntuacionGastronomia();
		this.puntuacionOcio=c.getPuntuacionOcio();
		this.puntuacionTotal=c.getPuntuacionTotal();
		this.puntuacionTransporte= c.getPuntuacionTransporte();
	}
	
	
	
	
	

}
