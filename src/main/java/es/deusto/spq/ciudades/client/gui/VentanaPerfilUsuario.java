package es.deusto.spq.ciudades.client.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.sound.midi.Synthesizer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.log4j.Logger;

import es.deusto.spq.ciudades.client.controller.CiudadesController;
import es.deusto.spq.ciudades.server.jdo.data.Ciudad;
import es.deusto.spq.ciudades.server.jdo.data.CiudadDTO;
import es.deusto.spq.ciudades.server.jdo.data.CiudadUsuario;
import es.deusto.spq.ciudades.server.jdo.data.CiudadUsuarioDTO;
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

	private JLabel lblCiudades;
	private JButton btnVolver;
	private JButton btnRanking;
	private JPanel contentPane;
	private JScrollPane scrollPane;

	private DefaultTableModel tableModel;
	private JButton btnPuntuarCiudad;

	/**
	 * Constructor:
	 * 
	 * @param controller
	 * @param resourceBundle
	 * @param userLogeado
	 */
	public VentanaPerfilUsuario(final CiudadesController controller, final ResourceBundle resourceBundle,
			final Usuario userLogeado) {

		this.resourceBundle = resourceBundle;
		this.controller = controller;
		this.userLogeado = userLogeado;

		// Antes de mostrar resultados en las tablas comprobamos ciudades puntuadas:
		getCiudadesPuntuadasPorUsuarios();

		System.out.println(VentanaPerfilUsuario.this.userLogeado.getEmail() + " VentanaPerfilusuario");

		logger.info(resourceBundle.getString("userProfile"));

		contentPane = new JPanel();
		lblCiudades = new JLabel(resourceBundle.getString("cityList"));
		btnVerCiudades = new JButton(resourceBundle.getString("cityList"));
		btnVolver = new JButton(resourceBundle.getString("goToLoginWindow"));
		btnRanking = new JButton(resourceBundle.getString("rankingCities"));
		scrollPane = new JScrollPane();
		JTableCiudadesUsuario = new JTable();
		btnPuntuarCiudad = new JButton(resourceBundle.getString("punctuateCity"));

		lblCiudades.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCiudades.setBounds(23, 13, 113, 17);
		btnVerCiudades.setBounds(12, 373, 240, 25);
		btnVolver.setBounds(295, 373, 240, 25);
		btnRanking.setBounds(586, 373, 246, 25);
		scrollPane.setBounds(12, 39, 820, 321);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setSize(850, 500);
		setTitle(resourceBundle.getString("userProfile"));
		setContentPane(contentPane);

		contentPane.setLayout(null);
		contentPane.add(lblCiudades);
		contentPane.add(scrollPane);
		contentPane.add(btnVerCiudades);
		contentPane.add(btnVolver);
		contentPane.add(btnRanking);

		scrollPane.setViewportView(JTableCiudadesUsuario);

		btnPuntuarCiudad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaPuntuarCiudad vPuntuar = new VentanaPuntuarCiudad(controller, resourceBundle,
						VentanaPerfilUsuario.this.userLogeado, getCiudadSeleccionadaEnTabla());
				vPuntuar.setVisible(true);
				dispose();
			}
		});
		btnPuntuarCiudad.setBounds(295, 411, 240, 25);
		contentPane.add(btnPuntuarCiudad);

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

	private List<CiudadDTO> arrayCiudadesDTO;

	private void mostrarCiudadesEnTabla() {
		// Inicializamos modelo:
		tableModel = new DefaultTableModel();

		// Añadimos los nombres de las columnas a la tabla:
		tableModel.addColumn(resourceBundle.getString("cityName"));

		arrayCiudadesDTO = new ArrayList<CiudadDTO>();
		arrayCiudadesDTO = controller.getAllCiudades();

		for (int i = 0; i < arrayCiudadesDTO.size(); i++) {
			tableModel.addRow(new Object[] { arrayCiudadesDTO.get(i).getNombreCiudad() });
		}

		// Finalmente:
		// Introducimos el modelo en la tabla:
		JTableCiudadesUsuario.setModel(tableModel);
	}

	private void mostrarCiudadesOrdenasPorPuntuacionTotal() {
		// Inicializamos modelo:
		tableModel = new DefaultTableModel();
		List<CiudadDTO> arrayCiudadesDTOFinal = new ArrayList<CiudadDTO>();
		List<CiudadDTO> arrayCiudadesDTOPuntuaciones = new ArrayList<CiudadDTO>();

		// Añadimos los nombres de las columnas a la tabla:
		tableModel.addColumn(resourceBundle.getString("cityName"));
		tableModel.addColumn(resourceBundle.getString("cityCountry"));
		tableModel.addColumn(resourceBundle.getString("totalPunctuation"));

		// Primero sacamos todas las ciudades de la base de datos:
		arrayCiudadesDTO = new ArrayList<CiudadDTO>();
		arrayCiudadesDTO = controller.getAllCiudades();

		// Ahora comparamos cada ciudad registrada con las posibles ciudades votadas por
		// los usuarios:
		for (int i = 0; i < arrayCiudadesDTO.size(); i++) {
			int totalPuntuacion = 0;
			int vecesRepetidaCiudad = 0;
			for (int j = 0; j < ciudadesPuntuadas.size(); j++) {
				if (arrayCiudadesDTO.get(i).getNombreCiudad()
						.equals(ciudadesPuntuadas.get(j).getCiudad().getNombreCiudad())) {
					totalPuntuacion += ciudadesPuntuadas.get(j).getCiudad().getPuntuacionTotal();
					vecesRepetidaCiudad++;
					System.out.println("Nombre ciudad: " + ciudadesPuntuadas.get(j).getCiudad().getNombreCiudad()
							+ " Puntos: " + ciudadesPuntuadas.get(j).getCiudad().getPuntuacionTotal());
				}
			}

			// Solo la guardaos si está alguna vez repetida:
			if (vecesRepetidaCiudad > 0) {
				// Hacemos media:
				int mediaTotal = totalPuntuacion / vecesRepetidaCiudad;
				// Colocamos media total en la ciudad:
				arrayCiudadesDTO.get(i).setPuntuacionTotal(mediaTotal);
				// Guardamos en el array a mostrar:
				arrayCiudadesDTOPuntuaciones.add(arrayCiudadesDTO.get(i));
				System.out.println("Ciudad guardad en el array correctamente!");
			}

		}

		// Solo mostramos y calculamos si arrayCiudadesDTOPuntuaciones.size() es != 0:
		if (arrayCiudadesDTOPuntuaciones.isEmpty() == false) {
			for (int i = 10; i >= 1; i--) {
				for (CiudadDTO ciudad : arrayCiudadesDTOPuntuaciones) {
					if (ciudad.getPuntuacionTotal() == i) {
						arrayCiudadesDTOFinal.add(ciudad);
						System.out.println("Ordenando ciudad..." + ciudad.getNombreCiudad());
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
		} else {
			JOptionPane.showMessageDialog(null, "Lo sentimos, ningún usuario ha votado alguna ciudad!");
		}
	}

	private CiudadDTO getCiudadSeleccionadaEnTabla() {
		CiudadDTO ciudad = null;

		for (int i = 0; i < tableModel.getRowCount(); i++) {
			if (JTableCiudadesUsuario.getSelectedRow() == i) {
				for (CiudadDTO c : arrayCiudadesDTO) {
					if (c.getNombreCiudad().equals(JTableCiudadesUsuario.getValueAt(i, 0))) {
						ciudad = c;
					}
				}
			}
		}
		return ciudad;
	}

	List<CiudadUsuarioDTO> ciudadesPuntuadas = new ArrayList<CiudadUsuarioDTO>();

	private void getCiudadesPuntuadasPorUsuarios() {
		ciudadesPuntuadas = new ArrayList<CiudadUsuarioDTO>();
		ciudadesPuntuadas = controller.getCiudadesPuntuadasPorUsuarios();
	}
}
