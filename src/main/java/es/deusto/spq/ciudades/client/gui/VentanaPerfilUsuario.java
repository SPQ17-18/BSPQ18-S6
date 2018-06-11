package es.deusto.spq.ciudades.client.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaPerfilUsuario extends JFrame {
	private JTable JTableCiudadesUsuario;
	public VentanaPerfilUsuario() {
		getContentPane().setLayout(null);
		
		JLabel lblNombreUsuario = new JLabel("Usuario");
		lblNombreUsuario.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNombreUsuario.setBounds(39, 25, 53, 14);
		getContentPane().add(lblNombreUsuario);
		
		JTableCiudadesUsuario = new JTable();
		JTableCiudadesUsuario.setBounds(37, 68, 357, 100);
		getContentPane().add(JTableCiudadesUsuario);
		

		
		JButton btnPuntuar = new JButton("Puntuar");
		btnPuntuar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		JScrollPane scrollPaneTabla = new JScrollPane();
		scrollPaneTabla.setBounds(0, 0, 2, 2);
		getContentPane().add(scrollPaneTabla);
		btnPuntuar.setBounds(335, 227, 89, 23);
		getContentPane().add(btnPuntuar);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnVolver.setBounds(10, 227, 89, 23);
		getContentPane().add(btnVolver);
		
		JButton btnRanking = new JButton("Ver ranking ciudades");
		btnRanking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnRanking.setBounds(194, 227, 131, 23);
		getContentPane().add(btnRanking);
	}
}
