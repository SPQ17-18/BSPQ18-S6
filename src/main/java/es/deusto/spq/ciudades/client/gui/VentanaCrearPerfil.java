package es.deusto.spq.ciudades.client.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import es.deusto.spq.ciudades.client.controller.CiudadesController;
import es.deusto.spq.ciudades.server.jdo.data.UsuarioDTO;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaCrearPerfil extends JFrame {
	private JTextField textNombre;
	private JTextField textApellido;
	private JTextField textPass;
	private JButton btnAceptar;
	private JButton btnVolver;
	
	private CiudadesController controler;
	private JTextField textFieldRepPass;
	private JLabel lblCorreo;
	private JTextField textCorreo;
	
	final static Logger logger = Logger.getLogger(VentanaCrearPerfil.class);

	
	/**Clase En la que un nuevo usuaro se registra en la aplicacion
	 * @param controler
	 */
	public VentanaCrearPerfil(CiudadesController controler) {
		
		this.controler = controler;
		
		setBounds(50, 50, 356, 246);
		getContentPane().setLayout(null);
		
		JLabel lblNombre = new JLabel("Introduzca nombre:");
		lblNombre.setBounds(59, 31, 101, 14);
		getContentPane().add(lblNombre);
		
		JLabel lblApellido = new JLabel("Introduzca apelido:");
		lblApellido.setBounds(59, 56, 101, 14);
		getContentPane().add(lblApellido);
		
		//no pongo la enie por posibles conflictos
		JLabel lblContraseña = new JLabel("Introduzca Password:");
		lblContraseña.setBounds(59, 106, 121, 14);
		getContentPane().add(lblContraseña);
		
		JLabel lblcomprobarPass = new JLabel("Repita password:");
		lblcomprobarPass.setBounds(59, 131, 101, 14);
		getContentPane().add(lblcomprobarPass);
		
		textNombre = new JTextField();
		textNombre.setBounds(189, 28, 86, 20);
		getContentPane().add(textNombre);
		textNombre.setColumns(10);
		
		textApellido = new JTextField();
		textApellido.setBounds(189, 53, 86, 20);
		getContentPane().add(textApellido);
		textApellido.setColumns(10);
		
		textPass = new JTextField();
		textPass.setBounds(190, 103, 86, 20);
		getContentPane().add(textPass);
		textPass.setColumns(10);
		
		textFieldRepPass = new JTextField();
		textFieldRepPass.setBounds(189, 128, 86, 20);
		getContentPane().add(textFieldRepPass);
		textFieldRepPass.setColumns(10);
		
		lblCorreo = new JLabel("Introduzca correo:");
		lblCorreo.setBounds(59, 81, 101, 14);
		getContentPane().add(lblCorreo);
		
		textCorreo = new JTextField();
		textCorreo.setBounds(189, 78, 86, 20);
		getContentPane().add(textCorreo);
		textCorreo.setColumns(10);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombre = textNombre.getText();
				String contrasenia = textPass.getText();
				String apellido = textApellido.getText();
				String contrasenia2 = textFieldRepPass.getText();
				//si los campos estan vacios lanzar mensaje erro, existen campos vacios
				if (nombre.equals("")||contrasenia.equals("")||apellido.equals("")) {
					JOptionPane.showMessageDialog(btnAceptar, "falta algun campo por rellenar");
				}else {
					if (contrasenia.equals(contrasenia2)) {
						try {
							// usuario dto = nuevo ususario DTO
							UsuarioDTO userIntro = new UsuarioDTO();
							userIntro.setApellido(textApellido.getText());
							userIntro.setNombre(textNombre.getText());
							userIntro.setEmail(textCorreo.getText());
							userIntro.setPassword(textPass.getText());
							
							
							if (VentanaCrearPerfil.this.controler.registerUsuario(userIntro)) {
								logger.info("Registrado correctamente");
							}
							//userIntro.setApellido(apellido);
							// meter los parametros al usuario
							//metodo de introducir usuario a la BD con el controler 
						} catch (Exception e2) {
							
						}
						
						
					}
				}
			}
		});
		btnAceptar.setBounds(241, 173, 89, 23);
		getContentPane().add(btnAceptar);
		
		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanLogin volverAtras = new VentanLogin();
				volverAtras.setVisible(true);
				dispose();
			}
		});
		btnVolver.setBounds(10, 173, 89, 23);
		getContentPane().add(btnVolver);
		

		


	}
}
