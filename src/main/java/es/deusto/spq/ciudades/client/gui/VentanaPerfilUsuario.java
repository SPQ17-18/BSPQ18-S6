package es.deusto.spq.ciudades.client.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.log4j.Logger;

import es.deusto.spq.ciudades.client.controller.CiudadesController;
import es.deusto.spq.ciudades.server.jdo.data.CiudadDTO;
import es.deusto.spq.ciudades.server.jdo.data.Usuario;

public class VentanaPerfilUsuario extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(VentanaPerfilUsuario.class.getName());

	private JTable JTableCiudadesUsuario;

	private JButton btnVerCiudades;

	private ResourceBundle resourceBundle;
	private CiudadesController controller;
	private Usuario userLogeado;

	private JLabel lblCiudadesPuntuadas;
	private JButton btnVolver;
	private JButton btnRanking;
	private JPanel contentPane;
	private JScrollPane scrollPane;

	private DefaultTableModel tableModel;

	/**
	 * Constructor:
	 * 
	 * @param controller
	 * @param resourceBundle
	 * @param userLogeado
	 */
	public VentanaPerfilUsuario(final CiudadesController controller, final ResourceBundle resourceBundle,
			Usuario userLogeado) {

		this.resourceBundle = resourceBundle;
		this.controller = controller;
		this.userLogeado = userLogeado;

		logger.info(resourceBundle.getString("userProfile"));

		contentPane = new JPanel();
		lblCiudadesPuntuadas = new JLabel(resourceBundle.getString("punctuatedCities"));
		btnVerCiudades = new JButton("Ver Ciudades");
		btnVolver = new JButton("Volver a inicio");
		btnRanking = new JButton("Ver ranking ciudades");
		scrollPane = new JScrollPane();
		JTableCiudadesUsuario = new JTable();

		lblCiudadesPuntuadas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCiudadesPuntuadas.setBounds(230, 9, 72, 17);
		btnVerCiudades.setBounds(12, 373, 240, 25);
		btnVolver.setBounds(295, 373, 240, 25);
		btnRanking.setBounds(586, 373, 246, 25);
		scrollPane.setBounds(12, 39, 820, 321);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setSize(850, 500);
		setTitle("Ventana Perfil del Usuario");
		setContentPane(contentPane);

		contentPane.setLayout(null);
		contentPane.add(lblCiudadesPuntuadas);
		contentPane.add(scrollPane);
		contentPane.add(btnVerCiudades);
		contentPane.add(btnVolver);
		contentPane.add(btnRanking);

		scrollPane.setViewportView(JTableCiudadesUsuario);

		btnVerCiudades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mostrarCiudadesEnTabla();
			}
		});

		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanLogin vLogin = new VentanLogin(controller, resourceBundle);
				vLogin.setVisible(true);
				dispose();
			}
		});

		btnRanking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mostrarCiudadesOrdenasPorPuntuacionTotal();
			}
		});
	}

	private void mostrarCiudadesEnTabla() {
		// Inicializamos modelo:
		tableModel = new DefaultTableModel();

		// Añadimos los nombres de las columnas a la tabla:
		tableModel.addColumn("Nombre Ciudad");
		tableModel.addColumn("Puntuacion total de ciudad");

		List<CiudadDTO> arrayCiudadesDTO = new ArrayList<CiudadDTO>();
		arrayCiudadesDTO = controller.getAllCiudades();

		for (int i = 0; i < arrayCiudadesDTO.size(); i++) {
			tableModel.addRow(new Object[] { arrayCiudadesDTO.get(i).getNombreCiudad(),
					arrayCiudadesDTO.get(i).getPuntuacionTotal() });
		}

		// Finalmente:
		// Introducimos el modelo en la tabla:
		JTableCiudadesUsuario.setModel(tableModel);
	}

	private void mostrarCiudadesOrdenasPorPuntuacionTotal() {
		// Inicializamos modelo:
		tableModel = new DefaultTableModel();

		// Añadimos los nombres de las columnas a la tabla:
		tableModel.addColumn("Nombre Ciudad");
		tableModel.addColumn("Pais de la ciudad");
		tableModel.addColumn("Puntuacion total de ciudad");

		List<CiudadDTO> arrayCiudadesDTO = new ArrayList<CiudadDTO>();
		arrayCiudadesDTO = controller.getAllCiudades();

		List<CiudadDTO> arrayCiudadesDTOFinal = new ArrayList<CiudadDTO>();
		for (int i = 10; i >= 1; i--) {
			for (CiudadDTO ciudad : arrayCiudadesDTO) {
				if (ciudad.getPuntuacionTotal() == i) {
					arrayCiudadesDTOFinal.add(ciudad);
				}
			}
		}

		for (int i = 0; i < arrayCiudadesDTOFinal.size(); i++) {
			tableModel.addRow(new Object[] { arrayCiudadesDTOFinal.get(i).getNombreCiudad(),
					arrayCiudadesDTOFinal.get(i).getPais(), arrayCiudadesDTOFinal.get(i).getPuntuacionTotal() });
		}

		// Finalmente:
		// Introducimos el modelo en la tabla:
		JTableCiudadesUsuario.setModel(tableModel);
	}
}
