package es.deusto.spq.ciudades.client.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaCrearPerfil extends JFrame {
	private JTextField textUsuario;
	private JTextField textCorreo;
	private JTextField textPass;
	private JButton btnAceptar;
	private JButton btnVolver;
	public VentanaCrearPerfil() {
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
		
		textUsuario = new JTextField();
		textUsuario.setBounds(232, 28, 86, 20);
		getContentPane().add(textUsuario);
		textUsuario.setColumns(10);
		
		textCorreo = new JTextField();
		textCorreo.setBounds(232, 53, 86, 20);
		getContentPane().add(textCorreo);
		textCorreo.setColumns(10);
		
		textPass = new JTextField();
		textPass.setBounds(232, 78, 86, 20);
		getContentPane().add(textPass);
		textPass.setColumns(10);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAceptar.setBounds(335, 129, 89, 23);
		getContentPane().add(btnAceptar);
		
		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnVolver.setBounds(10, 129, 89, 23);
		getContentPane().add(btnVolver);
	}

}
