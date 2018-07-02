package es.deusto.spq.ciudades.client.gui;

import javax.swing.JFrame;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import es.deusto.spq.ciudades.client.controller.CiudadesController;
import es.deusto.spq.ciudades.server.jdo.data.Assembler;
import es.deusto.spq.ciudades.server.jdo.data.Ciudad;
import es.deusto.spq.ciudades.server.jdo.data.CiudadDTO;
import es.deusto.spq.ciudades.server.jdo.data.PuntuacionDTO;
import es.deusto.spq.ciudades.server.jdo.data.Usuario;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.Synthesizer;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.JScrollBar;
import javax.swing.JSlider;
import javax.swing.JSeparator;
import javax.swing.JSpinner;

public class VentanaPuntuarCiudad extends JFrame {

	private static final long serialVersionUID = 1L;

	final static Logger logger = Logger.getLogger(VentanaPuntuarCiudad.class);

	private JLabel lblPuntuacionCultura;
	private JLabel lblPuntuacionGastronomia;
	private JLabel lblPuntuacionOcio;
	private JLabel lblPuntuacionTransporte;
	private JButton btnPuntuar;
	private JButton btnVolverPerfilUsuario;
	private JLabel lblCiudad;

	private CiudadesController controller;
	private ResourceBundle resourcebundle;
	private Usuario user;

	private JSpinner spnCultura, spnGastronomia, spnOcio, spnTransporte;
	private JLabel nombreCiudadAPuntuar;

	public VentanaPuntuarCiudad(final CiudadesController controller, final ResourceBundle resourcebundle,
			final Usuario user, final CiudadDTO ciudad) {

		this.controller = controller;
		this.resourcebundle = resourcebundle;
		this.user = user;

		System.out.println(VentanaPuntuarCiudad.this.user.getEmail() + " VentanaPuntuarCiudad");

		SpinnerNumberModel model1 = new SpinnerNumberModel(5, 1, 10, 1);
		SpinnerNumberModel model2 = new SpinnerNumberModel(5, 1, 10, 1);
		SpinnerNumberModel model3 = new SpinnerNumberModel(5, 1, 10, 1);
		SpinnerNumberModel model4 = new SpinnerNumberModel(5, 1, 10, 1);

		nombreCiudadAPuntuar = new JLabel(ciudad.getNombreCiudad());
		lblPuntuacionCultura = new JLabel(resourcebundle.getString("culturePunctuation") + " :");
		lblPuntuacionGastronomia = new JLabel(resourcebundle.getString("gastronomyPunctuation") + " :");
		lblPuntuacionOcio = new JLabel(resourcebundle.getString("leisurePunctuation") + " :");
		lblPuntuacionTransporte = new JLabel(resourcebundle.getString("transportPunctuation") + " :");
		btnPuntuar = new JButton(resourcebundle.getString("punctuateCity") + " :");
		btnVolverPerfilUsuario = new JButton(resourcebundle.getString("goToUserProfile"));
		lblCiudad = new JLabel(resourcebundle.getString("city") + " :");
		spnCultura = new JSpinner(model1);
		spnGastronomia = new JSpinner(model2);
		spnOcio = new JSpinner(model3);
		spnTransporte = new JSpinner(model4);

		getContentPane().setLayout(null);
		setTitle(ciudad.getNombreCiudad());
		setBounds(222, 100, 409, 451);
		setLocationRelativeTo(null);

		getContentPane().add(lblPuntuacionCultura);
		getContentPane().add(lblPuntuacionGastronomia);
		getContentPane().add(lblPuntuacionOcio);
		getContentPane().add(lblPuntuacionTransporte);
		getContentPane().add(btnPuntuar);
		getContentPane().add(btnVolverPerfilUsuario);
		getContentPane().add(lblCiudad);
		getContentPane().add(spnCultura);
		getContentPane().add(spnGastronomia);
		getContentPane().add(spnOcio);
		getContentPane().add(spnTransporte);
		getContentPane().add(nombreCiudadAPuntuar);

		lblPuntuacionCultura.setBounds(55, 91, 171, 16);
		lblPuntuacionGastronomia.setBounds(56, 126, 170, 16);
		lblPuntuacionOcio.setBounds(55, 162, 171, 16);
		lblPuntuacionTransporte.setBounds(55, 201, 171, 16);
		btnPuntuar.setBounds(221, 337, 158, 25);
		btnVolverPerfilUsuario.setBounds(12, 337, 184, 25);
		lblCiudad.setBounds(55, 29, 129, 16);
		spnCultura.setBounds(241, 91, 51, 33);
		spnGastronomia.setBounds(241, 126, 51, 33);
		spnOcio.setBounds(241, 163, 51, 33);
		spnTransporte.setBounds(241, 201, 51, 33);
		nombreCiudadAPuntuar.setBounds(196, 29, 129, 16);

		btnVolverPerfilUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				VentanaPerfilUsuario vUsuario = new VentanaPerfilUsuario(controller, resourcebundle, user);
				setLocationRelativeTo(null);
				vUsuario.setVisible(true);
			}
		});

		btnPuntuar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Assembler assembler = new Assembler();
				// Guardamos la ciudad que ha votado el usuario:
				Ciudad _ciudad = assembler.disassembleCiudad(ciudad);
				controller.puntuarCiudadUsuario(_ciudad, user);

				// Calculamos la puntuación media de todas las demás puntuaciones y la
				// incorparamos al objeto:
				int puntacionMediaTotal = (Integer) spnOcio.getValue() + (Integer) spnGastronomia.getValue()
						+ (Integer) spnCultura.getValue() + (Integer) spnTransporte.getValue();
				puntacionMediaTotal /= 4;

				// Guardamos las diferentes puntuaciones de la ciudad votada por el usuario:
				PuntuacionDTO puntuacionDTO = new PuntuacionDTO(_ciudad.getNombreCiudad(), puntacionMediaTotal,
						(Integer) spnOcio.getValue(), (Integer) spnGastronomia.getValue(),
						(Integer) spnCultura.getValue(), (Integer) spnTransporte.getValue());

				controller.insertarPuntuacion(puntuacionDTO);
				logger.info("Su puntuacion se ha registrado con exito");
				btnVolverPerfilUsuario.doClick();
			}
		});

	}
}
