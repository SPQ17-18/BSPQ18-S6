package es.deusto.spq.ciudades.client.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.ScrollPane;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import es.deusto.spq.ciudades.client.controller.CiudadesController;
import es.deusto.spq.ciudades.server.jdo.data.Ciudad;
import es.deusto.spq.ciudades.server.jdo.data.Usuario;
import es.deusto.spq.ciudades.server.jdo.data.UsuarioDTO;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;

public class VentanaPerfilUsuario extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	static Logger logger = Logger.getLogger(VentanaPerfilUsuario.class.getName());
	
	private VentanLogin vLogin;
	private JTable JTableCiudadesUsuario;
	
	private JButton btnPuntuar;


	private ResourceBundle resourceBundle;
	private CiudadesController controller;


	
	
	public VentanaPerfilUsuario(final CiudadesController controller, final ResourceBundle resourceBundle, Usuario userLogeado) {
		
		this.resourceBundle= resourceBundle;
		this.controller= controller;
		vLogin.userLogeado= userLogeado;

		logger.info(resourceBundle.getString("userProfile"));
		setTitle(resourceBundle.getString("userProfile"));
		
		this.setBounds(500, 100, 465, 350);
		
		
		getContentPane().setLayout(null);
		
		JLabel lblCiudadesPuntuadas = new JLabel(resourceBundle.getString("punctuatedCities"));
		lblCiudadesPuntuadas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCiudadesPuntuadas.setBounds(37, 41, 140, 14);
		getContentPane().add(lblCiudadesPuntuadas);
		
		//List<Ciudad> ciudadesPuntuadas = user.getCiudadesPuntuadas();

		
		JTableCiudadesUsuario = new JTable();
		JTableCiudadesUsuario.setBounds(37, 68, 357, 118);
		getContentPane().add(JTableCiudadesUsuario);
		

		
		btnPuntuar = new JButton(resourceBundle.getString("punctuateCity"));
		btnPuntuar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		
		
		
		
		JScrollPane scrollPaneTabla = new JScrollPane();
		scrollPaneTabla.setBounds(0, 0, 2, 2);
		getContentPane().add(scrollPaneTabla);
		btnPuntuar.setBounds(278, 255, 146, 23);
		getContentPane().add(btnPuntuar);
		
	
		
		JButton btnVolver = new JButton(resourceBundle.getString("goToLoginWindow"));
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				VentanLogin vLogin = new VentanLogin(controller, resourceBundle);
				vLogin.centreWindow();
				vLogin.setVisible(true);
				dispose();
			}
		});
		btnVolver.setBounds(12, 219, 193, 23);
		getContentPane().add(btnVolver);
		
		JButton btnRanking = new JButton(resourceBundle.getString("cityList"));
		btnRanking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				VentanaRankingCiudades vRanking = new VentanaRankingCiudades();
				vRanking.centreWindow();
				vRanking.setVisible(true);
				dispose();
			}
		});
		btnRanking.setBounds(105, 255, 146, 23);
		getContentPane().add(btnRanking);
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
