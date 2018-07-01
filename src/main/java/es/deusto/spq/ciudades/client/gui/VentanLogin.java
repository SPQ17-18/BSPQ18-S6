package es.deusto.spq.ciudades.client.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import es.deusto.spq.ciudades.client.controller.CiudadesController;
import es.deusto.spq.ciudades.server.jdo.data.Assembler;
import es.deusto.spq.ciudades.server.jdo.data.Usuario;
import es.deusto.spq.ciudades.server.jdo.data.UsuarioDTO;
import es.deusto.spq.ciudades.server.remote.IRemoteFacade;

public class VentanLogin extends JFrame {

	final static Logger logger = Logger.getLogger(VentanLogin.class);

	private static final long serialVersionUID = 1L;

	private CiudadesController controller;

	public Usuario userLogeado;
	public UsuarioDTO uDTO;

	private Assembler assembler;

	private JTextField textFieldEmail;
	private JTextField textFieldPassword;
	private ButtonGroup opcionesIdioma;

	public String idiomaApp = "en";

	private JRadioButton opcionIngles;
	JRadioButton opcionCast;

	protected ResourceBundle resourceBundle = ResourceBundle.getBundle("lang/messages", Locale.forLanguageTag("en"));

	private String language = "en";
	private String country = "US";
	// protected ResourceBundle resourceBundle;

	/**
	 * Clase constructora
	 * 
	 * @param args[]
	 *            Comando con argumentos.
	 */
	public VentanLogin(String[] args, IRemoteFacade collector) {
		try {
			controller = new CiudadesController(collector);
			// userLogeado= controller.DevolverUsuario(textFieldEmail.getText().trim());

		} catch (RemoteException e) {
			logger.error("Remote exception: " + e.getMessage());
			e.printStackTrace();
		}
		language = new String(args[3]);
		country = new String(args[4]);
		Locale currentLocale = new Locale(language, country);
		// resourceBundle = ResourceBundle.getBundle("lang/messages", currentLocale);

		// resourceBundle = ResourceBundle.getBundle("lang/messages",
		// Locale.forLanguageTag(language));
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

		logger.info("VentanaLogin");

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
						userLogeado = controller.DevolverUsuario(textFieldEmail.getText());
						if (userLogeado != null) {
							logger.info("DevolverUsuario correcto!");
						}
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					VentanaPerfilUsuario vpUsuario = new VentanaPerfilUsuario(controller, resourceBundle, userLogeado);
					vpUsuario.setVisible(true);
					dispose();

				} else {
					JOptionPane.showMessageDialog(VentanLogin.this, resourceBundle.getString("wrongSignIn"),
							resourceBundle.getString("badCredentials"), JOptionPane.INFORMATION_MESSAGE);
					logger.info(resourceBundle.getString("wrongUsernamePassword") + ": "
							+ textFieldEmail.getText().trim() + " " + textFieldPassword.getText().trim());

				}

				// Comprobamos si el usuario es el ADMIN
				if (textFieldEmail.getText().trim().equals("admin")
						&& textFieldPassword.getText().trim().equals("admin")) {

				}

			}
		});
		btnEntrar.setBounds(29, 90, 114, 23);
		getContentPane().add(btnEntrar);

		JButton btnRegistrarse = new JButton(resourceBundle.getString("register"));
		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				VentanaCrearPerfil registrarse = new VentanaCrearPerfil(controller, resourceBundle);
				// uDTO = assembler.assembleUnUsuario(userLogeado);
				// Usuario uNuevo= new Usuario(text, nombre, apellido, password)
				// controller.registerUsuario(uDTO);
				// registrarse.centreWindow();
				registrarse.setVisible(true);
				dispose();
			}
		});
		btnRegistrarse.setBounds(198, 90, 114, 23);
		getContentPane().add(btnRegistrarse);

		JLabel lblIdioma = new JLabel(resourceBundle.getString("selectLanguage"));
		lblIdioma.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblIdioma.setBounds(29, 133, 114, 14);
		getContentPane().add(lblIdioma);

		opcionCast = new JRadioButton(resourceBundle.getString("spanish"));
		opcionCast.setBounds(156, 129, 127, 25);
		getContentPane().add(opcionCast);

		opcionCast.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {

				idiomaApp = "es";
				resourceBundle = ResourceBundle.getBundle("lang/messages", Locale.forLanguageTag("es"));

			}

		});

		opcionIngles = new JRadioButton(resourceBundle.getString("english"));
		opcionIngles.setBounds(156, 159, 127, 25);
		getContentPane().add(opcionIngles);

		opcionIngles.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				idiomaApp = "en";
				resourceBundle = ResourceBundle.getBundle("lang/messages", Locale.forLanguageTag("en"));

			}
		});

		opcionesIdioma = new ButtonGroup();
		opcionesIdioma.add(opcionCast);
		opcionesIdioma.add(opcionIngles);

	}

	/**
	 * Metodo que limpia los campos de la ventana login.
	 */
	private void limpiarCampos() {
		textFieldEmail.setText("");
		textFieldPassword.setText("");
	}

}
