package es.deusto.spq.ciudades.client.gui;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

public class VentanaPuntuarCiudad extends JFrame{
	private JTextField txtPuntuacionCultura;
	private JTextField txtPuntuacionGastronomia;
	private JTextField txtPuntuacionOcio;
	private JTextField txtPuntuacionTransporte;
	private JTextField txtPuntuacionTotal;
	
	private JLabel lblPuntuacionCultura;
	private JLabel lblPuntuacionGastronomia;
	private JLabel lblPuntuacionOcio;
	private JLabel lblPuntuacionTransporte;
	private JLabel lblPuntuacionTotal;
	private JButton btnPuntuar;
	private JButton btnVolverPerfilUsuario;
	private JLabel lblCiudad;
	
	
	public VentanaPuntuarCiudad() {
		getContentPane().setLayout(null);
		
		txtPuntuacionCultura = new JTextField();
		txtPuntuacionCultura.setBounds(244, 88, 116, 22);
		getContentPane().add(txtPuntuacionCultura);
		txtPuntuacionCultura.setColumns(10);
		
		lblPuntuacionCultura = new JLabel("Puntuacion Cultura :");
		lblPuntuacionCultura.setBounds(55, 91, 116, 16);
		getContentPane().add(lblPuntuacionCultura);
		
		txtPuntuacionGastronomia = new JTextField();
		txtPuntuacionGastronomia.setBounds(244, 123, 116, 22);
		getContentPane().add(txtPuntuacionGastronomia);
		txtPuntuacionGastronomia.setColumns(10);
		
		lblPuntuacionGastronomia = new JLabel("Puntuacion Gastronomia :");
		lblPuntuacionGastronomia.setBounds(56, 126, 170, 16);
		getContentPane().add(lblPuntuacionGastronomia);
		
		txtPuntuacionOcio = new JTextField();
		txtPuntuacionOcio.setBounds(244, 156, 116, 22);
		getContentPane().add(txtPuntuacionOcio);
		txtPuntuacionOcio.setColumns(10);
		
		lblPuntuacionOcio = new JLabel("Puntuacion Ocio :");
		lblPuntuacionOcio.setBounds(55, 162, 141, 16);
		getContentPane().add(lblPuntuacionOcio);
		
		txtPuntuacionTransporte = new JTextField();
		txtPuntuacionTransporte.setBounds(244, 198, 116, 22);
		getContentPane().add(txtPuntuacionTransporte);
		txtPuntuacionTransporte.setColumns(10);
		
		lblPuntuacionTransporte = new JLabel("Puntuacion Transporte :");
		lblPuntuacionTransporte.setBounds(55, 201, 154, 16);
		getContentPane().add(lblPuntuacionTransporte);
		
		txtPuntuacionTotal = new JTextField();
		txtPuntuacionTotal.setBounds(244, 250, 116, 22);
		getContentPane().add(txtPuntuacionTotal);
		txtPuntuacionTotal.setColumns(10);
		
		lblPuntuacionTotal = new JLabel("Puntuacion total :");
		lblPuntuacionTotal.setBounds(55, 253, 141, 16);
		getContentPane().add(lblPuntuacionTotal);
		
		btnPuntuar = new JButton("Puntuar");
		btnPuntuar.setBounds(254, 295, 106, 25);
		getContentPane().add(btnPuntuar);
		
		btnVolverPerfilUsuario = new JButton("Volver al perfil");
		btnVolverPerfilUsuario.setBounds(67, 295, 129, 25);
		getContentPane().add(btnVolverPerfilUsuario);
		
		lblCiudad = new JLabel("Ciudad seleccionada : ");
		lblCiudad.setBounds(97, 29, 177, 16);
		getContentPane().add(lblCiudad);
	}
}
