package es.deusto.spq.ciudades.client.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import es.deusto.spq.ciudades.client.controller.CiudadesController;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaCrearPerfil extends JFrame {
	private JTextField textUsuario;
	private JTextField textCorreo;
	private JTextField textPass;
	private JButton btnAceptar;
	private JButton btnVolver;
	
	private CiudadesController controler;
	private JTextField textFieldRepPass;
	
	/**Clase En la que un nuevo usuaro se registra en la aplicacion
	 * @param controler
	 */
	public VentanaCrearPerfil(CiudadesController controler) {
		
		this.controler = controler;
		
		setBounds(50, 50, 356, 228);
		getContentPane().setLayout(null);
		
		JLabel lblUsuario = new JLabel("Introduzca usuario:");
		lblUsuario.setBounds(59, 31, 101, 14);
		getContentPane().add(lblUsuario);
		
		JLabel lblCorreo = new JLabel("Introduzca correo:");
		lblCorreo.setBounds(59, 56, 101, 14);
		getContentPane().add(lblCorreo);
		
		//no pongo la enie por posibles conflictos
		JLabel lblContraseña = new JLabel("Introduzca Password:");
		lblContraseña.setBounds(59, 81, 121, 14);
		getContentPane().add(lblContraseña);
		
		JLabel lblcomprobarPass = new JLabel("Repita password:");
		lblcomprobarPass.setBounds(59, 105, 101, 14);
		getContentPane().add(lblcomprobarPass);
		
		textUsuario = new JTextField();
		textUsuario.setBounds(189, 28, 86, 20);
		getContentPane().add(textUsuario);
		textUsuario.setColumns(10);
		
		textCorreo = new JTextField();
		textCorreo.setBounds(189, 53, 86, 20);
		getContentPane().add(textCorreo);
		textCorreo.setColumns(10);
		
		textPass = new JTextField();
		textPass.setBounds(189, 78, 86, 20);
		getContentPane().add(textPass);
		textPass.setColumns(10);
		
		textFieldRepPass = new JTextField();
		textFieldRepPass.setBounds(189, 102, 86, 20);
		getContentPane().add(textFieldRepPass);
		textFieldRepPass.setColumns(10);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String usuario = textUsuario.getText();
				String contrasenia = textPass.getText();
				String correo = textCorreo.getText();
				String contrasenia2 = textFieldRepPass.getText();
				//si los campos estan vacios lanzar mensaje erro, existen campos vacios
				if (usuario.equals("")||contrasenia.equals("")||correo.equals("")) {
					JOptionPane.showMessageDialog(btnAceptar, "falta algun campo por rellenar");
				}else {
					if (contrasenia.equals(contrasenia2)) {
						try {
							// usuario dto = nuevo ususario DTO
							// meter los parametros al usuario
							//metodo de introducir usuario a la BD con el controler 
						} catch (Exception e2) {
							
						}
						
						
					}
				}
			}
		});
		btnAceptar.setBounds(241, 155, 89, 23);
		getContentPane().add(btnAceptar);
		
		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanLogin volverAtras = new VentanLogin();
				volverAtras.setVisible(true);
				dispose();
			}
		});
		btnVolver.setBounds(10, 155, 89, 23);
		getContentPane().add(btnVolver);
		


	}
}
