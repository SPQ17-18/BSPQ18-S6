package es.deusto.spq.ciudades.client.gui;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaRankingCiudades extends JFrame {
	private JTable JTableRankCiudad;
	public VentanaRankingCiudades() {
		getContentPane().setLayout(null);
		
		JLabel lblTitulo = new JLabel("Top 10 Ranking ");
		lblTitulo.setFont(new Font("Arial Black", Font.BOLD | Font.ITALIC, 17));
		lblTitulo.setBounds(122, 11, 163, 40);
		getContentPane().add(lblTitulo);
		
		JTableRankCiudad = new JTable();
		JTableRankCiudad.setBounds(30, 62, 371, 152);
		getContentPane().add(JTableRankCiudad);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnVolver.setBounds(170, 227, 89, 23);
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
