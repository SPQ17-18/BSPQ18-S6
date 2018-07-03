package es.deusto.spq.ciudades.server.jdo.data;

import java.io.Serializable;

import javax.jdo.annotations.PersistenceCapable;

/**
 * 
 * Clase para conocer la puntuacion de las ciudades, sin importar el usuario que las vota.
 * Esta clase es necesaria para hacer la media de las puntuaciones y asi sacar la puntuacion 
 * total
 *
 */
@PersistenceCapable
public class Puntuacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombreCiudad;
	private int puntuacionTotal;

	public int getPuntuacionOcio() {
		return puntuacionOcio;
	}

	public void setPuntuacionOcio(int puntuacionOcio) {
		this.puntuacionOcio = puntuacionOcio;
	}

	public int getPuntuacionGastronomia() {
		return puntuacionGastronomia;
	}

	public void setPuntuacionGastronomia(int puntuacionGastronomia) {
		this.puntuacionGastronomia = puntuacionGastronomia;
	}

	public int getPuntuacionCultura() {
		return puntuacionCultura;
	}

	public void setPuntuacionCultura(int puntuacionCultura) {
		this.puntuacionCultura = puntuacionCultura;
	}

	public int getPuntuacionTransporte() {
		return puntuacionTransporte;
	}

	public void setPuntuacionTransporte(int puntuacionTransporte) {
		this.puntuacionTransporte = puntuacionTransporte;
	}

	private int puntuacionOcio;
	private int puntuacionGastronomia;
	private int puntuacionCultura;
	private int puntuacionTransporte;

	public Puntuacion() {

	}

	public Puntuacion(String nombreCiudad, int puntuacionTotal, int puntuacionOcio, int puntuacionGastronomia,
			int puntuacionCultura, int puntuacionTransporte) {
		this.nombreCiudad = nombreCiudad;
		this.puntuacionTotal = puntuacionTotal;
		this.puntuacionOcio = puntuacionOcio;
		this.puntuacionGastronomia = puntuacionGastronomia;
		this.puntuacionCultura = puntuacionCultura;
		this.puntuacionTransporte = puntuacionTransporte;
	}

	public String getNombreCiudad() {
		return nombreCiudad;
	}

	public void setNombreCiudad(String nombreCiudad) {
		this.nombreCiudad = nombreCiudad;
	}

	public int getPuntuacionTotal() {
		return puntuacionTotal;
	}

	public void setPuntuacionTotal(int puntuacionTotal) {
		this.puntuacionTotal = puntuacionTotal;
	}

	public void copyPuntuacion(Puntuacion p) {
		this.nombreCiudad = p.getNombreCiudad();
		this.puntuacionTotal = p.getPuntuacionTotal();
		this.puntuacionCultura = p.getPuntuacionCultura();
		this.puntuacionGastronomia = p.getPuntuacionGastronomia();
		this.puntuacionOcio = p.getPuntuacionOcio();
		this.puntuacionTransporte = p.getPuntuacionTransporte();
	}

}
