package es.deusto.spq.ciudades.client.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanLogin extends JFrame {
	private JTextField textFieldUsuario;
	private JTextField textFieldPassword;
	public VentanLogin() {
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
		
		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEntrar.setBounds(10, 126, 89, 23);
		getContentPane().add(btnEntrar);
		
		JButton btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnRegistrarse.setBounds(259, 126, 89, 23);
		getContentPane().add(btnRegistrarse);
	}
}
