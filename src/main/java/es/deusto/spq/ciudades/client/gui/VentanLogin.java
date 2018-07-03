package es.deusto.spq.ciudades.client.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import es.deusto.spq.ciudades.client.controller.CiudadesController;
import es.deusto.spq.ciudades.server.jdo.data.Assembler;
import es.deusto.spq.ciudades.server.jdo.data.Usuario;
import es.deusto.spq.ciudades.server.jdo.data.UsuarioDTO;
import es.deusto.spq.ciudades.server.remote.IRemoteFacade;

/**
 * 
 * Ventana en la cual un cliente puede inciar sesion, permite la opcion
 * de registrarse ademas de traducir la aplicacion o a castellano o a ingles.
 *
 */
public class VentanLogin extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	final static Logger logger = Logger.getLogger(VentanLogin.class);

	private CiudadesController controller;

	public Usuario userLogeado;
	public UsuarioDTO uDTO;

	private Assembler assembler;

	private JTextField textFieldEmail;
	private JTextField textFieldPassword;

	public VentanLogin vLogin;

	private JComboBox idioma;
	private String[] ListIdiomas = { "Espanol", "English" };
	public String language = "es";
	public String country = "ES";
	public ResourceBundle resourceBundle = ResourceBundle.getBundle("lang/messages", Locale.forLanguageTag("es"));

	/**
	 * Clase constructora
	 * 
	 * @param args[]
	 *            Comando con argumentos.
	 */
	public VentanLogin(String[] args, IRemoteFacade collector) {
		try {
			controller = new CiudadesController(collector);

		} catch (RemoteException e) {
			logger.error("Remote exception: " + e.getMessage());
			e.printStackTrace();
		}
		language = new String(args[3]);
		country = new String(args[4]);

		resourceBundle = ResourceBundle.getBundle("lang/messages", Locale.forLanguageTag("ES"));
		iniciarComponentes();
	}

	/**
	 * Clase constructora
	 * 
	 * @param controller
	 *            Controller de la aplicacion.
	 * @param resourceBundle
	 *            Strings de un determinado lenguaje.
	 * @wbp.parser.constructor
	 */
	public VentanLogin(CiudadesController controller, ResourceBundle resourceBundle) {
		this.controller = controller;
		this.resourceBundle = resourceBundle;
		iniciarComponentes();
	}

	public void iniciarComponentes() {

		logger.info(resourceBundle.getString("login"));

		setBounds(500, 100, 365, 250);
		setLocationRelativeTo(null);

		setTitle(resourceBundle.getString("login"));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		JLabel lblEmail = new JLabel(resourceBundle.getString("email"));
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEmail.setBounds(29, 25, 146, 14);
		getContentPane().add(lblEmail);

		JLabel lblContraseña = new JLabel(resourceBundle.getString("password"));
		lblContraseña.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblContraseña.setBounds(29, 59, 115, 14);
		getContentPane().add(lblContraseña);

		textFieldEmail = new JTextField();
		textFieldEmail.setBounds(187, 23, 125, 20);
		getContentPane().add(textFieldEmail);
		textFieldEmail.setColumns(10);

		textFieldPassword = new JTextField();
		textFieldPassword.setBounds(187, 57, 125, 20);
		getContentPane().add(textFieldPassword);
		textFieldPassword.setColumns(10);

		idioma = new JComboBox(ListIdiomas);
		idioma.setBounds(99, 164, 150, 20);
		getContentPane().add(idioma);

		JButton btnEntrar = new JButton(resourceBundle.getString("signIn"));
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.info("Boton aceptar login");

				// Comprobacion campos vacios
				if (textFieldEmail.getText().equals("") || textFieldPassword.getText().equals("")) {
					JOptionPane.showMessageDialog(null, resourceBundle.getString("emptyFields"),
							"ERROR! Existen campos vacios", JOptionPane.ERROR_MESSAGE);
					limpiarCampos();
				}
				// Comprobamos si el usuario esta en la bd
				else if (controller.identifyUsuario(textFieldEmail.getText(),
						String.valueOf(textFieldPassword.getText()))) {
					JOptionPane.showMessageDialog(VentanLogin.this,
							resourceBundle.getString("greetings") + " " + textFieldEmail.getText().trim() + "! "
									+ resourceBundle.getString("welcomeMessage") + " ",
							resourceBundle.getString("login"), JOptionPane.INFORMATION_MESSAGE);
					logger.info(resourceBundle.getString("successfullyLoggedUser"));

					try {
						Usuario user = controller.DevolverUsuario(textFieldEmail.getText());
						if (user != null) {
							logger.info("DevolverUsuario correcto!");
							System.out.println(user.getEmail() + " VentanaLogin");
							VentanaPerfilUsuario vpUsuario = new VentanaPerfilUsuario(controller, resourceBundle, user);
							vpUsuario.setVisible(true);
							dispose();
						}
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(VentanLogin.this, resourceBundle.getString("wrongSignIn"),
							resourceBundle.getString("badCredentials"), JOptionPane.INFORMATION_MESSAGE);
					logger.info(resourceBundle.getString("wrongUsernamePassword") + ": " + textFieldEmail.getText()
							+ " " + textFieldPassword.getText());

				}

			}
		});
		btnEntrar.setBounds(29, 90, 114, 23);
		getContentPane().add(btnEntrar);

		JButton btnRegistrarse = new JButton(resourceBundle.getString("register"));
		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				VentanaCrearPerfil registrarse = new VentanaCrearPerfil(controller, resourceBundle);
				registrarse.setVisible(true);
				dispose();
			}
		});
		btnRegistrarse.setBounds(198, 90, 114, 23);
		getContentPane().add(btnRegistrarse);

		JButton btnTraducir = new JButton(resourceBundle.getString("translate"));
		btnTraducir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (idioma.getSelectedItem().toString().equals("Espanol")) {
					language = "es";
					country = "ES";
					resourceBundle = ResourceBundle.getBundle("lang/messages", Locale.forLanguageTag("ES"));
					dispose();
					VentanLogin vLogin = new VentanLogin(controller, resourceBundle);
					vLogin.setVisible(true);
					logger.info(language);

				} else if (idioma.getSelectedItem().toString().equals("English")) {
					language = "en";
					country = "US";
					resourceBundle = ResourceBundle.getBundle("lang/messages", Locale.US);
					dispose();
					VentanLogin vLogin = new VentanLogin(controller, resourceBundle);
					vLogin.setVisible(true);
					logger.info(language);

				} else {
					JOptionPane.showMessageDialog(null, resourceBundle.getString("error_msg"));
					logger.error("No se ha podido traducir");
				}

			}

		});
		btnTraducir.setBounds(81, 126, 189, 25);
		getContentPane().add(btnTraducir);

	}

	/**
	 * Metodo que limpia los campos de la ventana login.
	 */
	private void limpiarCampos() {
		textFieldEmail.setText("");
		textFieldPassword.setText("");
	}

}
