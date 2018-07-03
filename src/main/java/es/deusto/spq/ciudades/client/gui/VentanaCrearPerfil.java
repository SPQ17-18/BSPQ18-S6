package es.deusto.spq.ciudades.client.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import es.deusto.spq.ciudades.client.controller.CiudadesController;
import es.deusto.spq.ciudades.server.jdo.data.Usuario;
import es.deusto.spq.ciudades.server.jdo.data.UsuarioDTO;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

/**
 * 
 * Ventana en la cual el cliente puede registrarse, 
 * el unico requisito es rellenar todos los campos para 
 * que el cliente puede registrarse.
 *
 */
public class VentanaCrearPerfil extends JFrame {
	private JTextField textNombre;
	private JTextField textApellido;
	private JTextField textPass;
	private JButton btnAceptar;
	private JButton btnVolver;

	private CiudadesController controller;
	private JTextField textFieldRepPass;
	private JLabel lblCorreo;
	private JTextField textCorreo;

	private ResourceBundle resourceBundle;

	final static Logger logger = Logger.getLogger(VentanaCrearPerfil.class);

	/**
	 * Clase En la que un nuevo usuaro se registra en la aplicacion
	 * 
	 * @param controler
	 */
	public VentanaCrearPerfil(final CiudadesController controller, final ResourceBundle resourceBundle) {

		this.resourceBundle = resourceBundle;
		this.controller = controller;

		logger.info(resourceBundle.getString("register"));
		setTitle(resourceBundle.getString("register"));

		setBounds(50, 50, 441, 339);
		setLocationRelativeTo(null);

		getContentPane().setLayout(null);

		JLabel lblNombre = new JLabel(resourceBundle.getString("name"));
		lblNombre.setBounds(53, 48, 190, 14);
		getContentPane().add(lblNombre);

		JLabel lblApellido = new JLabel(resourceBundle.getString("surname"));
		lblApellido.setBounds(53, 81, 190, 14);
		getContentPane().add(lblApellido);

		// no pongo la enie por posibles conflictos
		JLabel lblContraseña = new JLabel(resourceBundle.getString("password"));
		lblContraseña.setBounds(53, 147, 190, 14);
		getContentPane().add(lblContraseña);

		JLabel lblcomprobarPass = new JLabel(resourceBundle.getString("repeatPassword"));
		lblcomprobarPass.setBounds(53, 186, 184, 14);
		getContentPane().add(lblcomprobarPass);

		textNombre = new JTextField();
		textNombre.setBounds(254, 45, 86, 20);
		getContentPane().add(textNombre);
		textNombre.setColumns(10);

		textApellido = new JTextField();
		textApellido.setBounds(254, 78, 86, 20);
		getContentPane().add(textApellido);
		textApellido.setColumns(10);

		textPass = new JTextField();
		textPass.setBounds(255, 144, 85, 20);
		getContentPane().add(textPass);
		textPass.setColumns(10);

		textFieldRepPass = new JTextField();
		textFieldRepPass.setBounds(254, 183, 86, 20);
		getContentPane().add(textFieldRepPass);
		textFieldRepPass.setColumns(10);

		lblCorreo = new JLabel(resourceBundle.getString("email"));
		lblCorreo.setBounds(53, 120, 190, 14);
		getContentPane().add(lblCorreo);

		textCorreo = new JTextField();
		textCorreo.setBounds(254, 111, 86, 20);
		getContentPane().add(textCorreo);
		textCorreo.setColumns(10);

		btnAceptar = new JButton(resourceBundle.getString("register"));
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombre = textNombre.getText();
				String contrasenia = textPass.getText();
				String apellido = textApellido.getText();
				String contrasenia2 = textFieldRepPass.getText();
				// si los campos estan vacios lanzar mensaje error, existen campos vacios
				if (nombre.equals("") || contrasenia.equals("") || apellido.equals("")) {
					JOptionPane.showMessageDialog(btnAceptar, resourceBundle.getString("emptyFields"));
				} else {
					if (contrasenia.equals(contrasenia2)) {
						try {
							// usuario dto = nuevo ususario DTO
							UsuarioDTO userNuevo = new UsuarioDTO();
							userNuevo.setApellido(textApellido.getText());
							userNuevo.setNombre(textNombre.getText());
							userNuevo.setEmail(textCorreo.getText());
							userNuevo.setPassword(textPass.getText());

							controller.registerUsuario(userNuevo);

							JOptionPane.showMessageDialog(btnAceptar, resourceBundle.getString("userRegistered"));
							dispose();
							VentanLogin vLogin = new VentanLogin(controller, resourceBundle);
							vLogin.setVisible(true);

							// meter los parametros al usuario
							// metodo de introducir usuario a la BD con el controler
						} catch (Exception e2) {
							logger.error(resourceBundle.getString("noUserRegistered"));
						}

					}
				}
			}
		});
		btnAceptar.setBounds(238, 256, 114, 23);
		getContentPane().add(btnAceptar);

		btnVolver = new JButton(resourceBundle.getString("back"));
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanLogin volverAtras = new VentanLogin(controller, resourceBundle);
				volverAtras.setVisible(true);
				dispose();
			}
		});
		btnVolver.setBounds(71, 256, 114, 23);
		getContentPane().add(btnVolver);

	}

	/**
	 * Centrar la Ventana
	 */
	public void centreWindow() {
		Dimension dim = getToolkit().getScreenSize();
		Rectangle abounds = getBounds();
		setLocation((dim.width - abounds.width) / 2, (dim.height - abounds.height) / 2);
	}

}
