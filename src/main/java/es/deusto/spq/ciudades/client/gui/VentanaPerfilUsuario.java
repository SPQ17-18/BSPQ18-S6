package es.deusto.spq.ciudades.client.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;

public class VentanaPerfilUsuario extends JFrame {
	public VentanaPerfilUsuario() {
		getContentPane().setLayout(null);
		
		JLabel lblNombreUsuario = new JLabel("Usuario");
		lblNombreUsuario.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNombreUsuario.setBounds(39, 25, 46, 14);
		getContentPane().add(lblNombreUsuario);
	}
}
