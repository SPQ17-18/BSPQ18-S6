package es.deusto.spq.ciudades.client.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import es.deusto.spq.ciudades.client.controller.CiudadesController;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;

public class VentanLogin extends JFrame {
	
	final static Logger logger = Logger.getLogger(VentanLogin.class);
	
	private static final long serialVersionUID = 1L;
	
	private CiudadesController controller;
	

	private JTextField textFieldUsuario;
	private JTextField textFieldPassword;
	
	protected ResourceBundle ResourceBundle;
	
	public VentanLogin(CiudadesController controller, final ResourceBundle resourceBundle) {
		
		logger.info("VentanaLogin");
		
		
		
		
		setBounds(500, 100, 365, 200);
		
		
		setTitle("Login");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUsuario.setBounds(29, 25, 53, 14);	
		getContentPane().add(lblUsuario);
		
		JLabel lblContraseña = new JLabel("Password:");
		lblContraseña.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblContraseña.setBounds(29, 58, 63, 14);
		getContentPane().add(lblContraseña);
		
		textFieldUsuario = new JTextField();
		textFieldUsuario.setBounds(156, 24, 125, 20);
		getContentPane().add(textFieldUsuario);
		textFieldUsuario.setColumns(10);
		
		textFieldPassword = new JTextField();
		textFieldPassword.setBounds(156, 57, 125, 20);
		getContentPane().add(textFieldPassword);
		textFieldPassword.setColumns(10);
		
		JButton btnEntrar = new JButton("Iniciar sesión");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(controller.identifyUsuario(String.valueOf(textFieldUsuario.getText()), String.valueOf(textFieldPassword.getText()))) {
				
					
				}
				
				
				
				
				
				
				
				
				
				
				
				if (textFieldUsuario.getText()=="admin"&&textFieldPassword.getText()=="admin") {
					//abrir  la ventana del administrados para introducir ciudades nuevas
				}else {
					//coger los Strings y comprobar BD, si no estas logg.message no se encuentra usuario.
					
				}
			}
		});
		btnEntrar.setBounds(10, 126, 89, 23);
		getContentPane().add(btnEntrar);
		
		JButton btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaCrearPerfil registrarse = new VentanaCrearPerfil(controller.identifyUsuario(email, password));
				registrarse.setVisible(true);
				dispose();
			}
		});
		btnRegistrarse.setBounds(234, 126, 114, 23);
		getContentPane().add(btnRegistrarse);
	}

	/**
	 * Metodo que limpia los campos de la ventana login.
	 */
	private void limpiarCampos() {
		textFieldUsuario.setText("");
		textFieldPassword.setText("");
	}

	/**
	 * Metodo get que devuelve el controlador.
	 * @return controller
	 */
	public CiudadesController getController() {
		return controller;
	}

	/**
	 * Metodo set que cambia el controlador. 
	 * @param controller controlador
	 */
	public void setController(CiudadesController controller) {
		this.controller = controller;
	}
	
}
