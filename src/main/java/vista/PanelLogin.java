package vista;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JButton;
import java.awt.Dimension;

import javax.swing.SwingConstants;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import controlador.ControladorAppChat;
//import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Toolkit;


/*
 * Esta clase define el panel de login.
 * En este panel el usuario puede tanto registrarse, con lo cual se desplegara el panel de registro
 * o directamente  acceder a la aplicacion indicando su cuenta de usuario y contraseña
 * En el caso de que fueran incorrectas o no existirian se le notificara con un mensaje en rojo
 * 
 */


public class PanelLogin {

	private static final String RECURSOS_VISTA_100X100_PNG = "/recursosVista/100x100.png";
	private static final String RECURSOS_VISTA_91B50561_0574_48B1_B57F_5307E7533DA6_200X200_PNG = "/recursosVista/91b50561-0574-48b1-b57f-5307e7533da6_200x200.png";
	private JFrame frmLogin;
	private JTextField textLogin;
	private JPasswordField textPassword;


	/**
	 * Create the application.
	 */
	public PanelLogin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setMinimumSize(new Dimension(650, 460));
		frmLogin.setIconImage(Toolkit.getDefaultToolkit().getImage(PanelLogin.class.getResource(RECURSOS_VISTA_100X100_PNG)));
		frmLogin.getContentPane().setMinimumSize(new Dimension(300, 300));
		frmLogin.getContentPane().setPreferredSize(new Dimension(500, 500));
		frmLogin.setBackground(Color.WHITE);
		frmLogin.getContentPane().setBackground(Color.WHITE);
		frmLogin.setTitle("Umu Chat Login");
		frmLogin.setBounds(100, 100, 675, 460);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLogin.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel_Norte = new JPanel();
		panel_Norte.setPreferredSize(new Dimension(100, 200));
		frmLogin.getContentPane().add(panel_Norte, BorderLayout.NORTH);
		panel_Norte.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));

		JLabel lblGestorEventos = new JLabel("");
		lblGestorEventos.setSize(new Dimension(10, 10));
		lblGestorEventos.setIconTextGap(2);
		lblGestorEventos.setIcon(
				new ImageIcon(PanelLogin.class.getResource(RECURSOS_VISTA_91B50561_0574_48B1_B57F_5307E7533DA6_200X200_PNG)));
		lblGestorEventos.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblGestorEventos.setForeground(Color.DARK_GRAY);
		panel_Norte.add(lblGestorEventos);

		JPanel panel_Centro = new JPanel();
		panel_Centro.setPreferredSize(new Dimension(10, 20));
		frmLogin.getContentPane().add(panel_Centro, BorderLayout.CENTER);
		panel_Centro.setLayout(new GridLayout(4, 2, 0, 0));

		Component rigidArea_3 = Box.createRigidArea(new Dimension(20, 20));
		rigidArea_3.setPreferredSize(new Dimension(20, 10));
		panel_Centro.add(rigidArea_3);

		JPanel panel = new JPanel();
		panel_Centro.add(panel);

		JLabel lblNewLabel = new JLabel("Usuario");
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setPreferredSize(new Dimension(56, 14));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(lblNewLabel);

		textLogin = new JTextField();
		textLogin.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(textLogin);
		textLogin.setColumns(15);

		JPanel panel_1 = new JPanel();
		panel_Centro.add(panel_1);

		JLabel lblNewLabel_1 = new JLabel("Contrase\u00F1a");
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_1.add(lblNewLabel_1);

		textPassword = new JPasswordField();
		textPassword.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(textPassword);
		textPassword.setColumns(15);
		
		JPanel panel_2 = new JPanel();
		panel_Centro.add(panel_2);
		
		JLabel lblloginIncorrecto = new JLabel("*Login Incorrecto. Por favor compruebe que el Usuario o la Contraseña son correctos");
		lblloginIncorrecto.setForeground(Color.RED);
		panel_2.add(lblloginIncorrecto);
		lblloginIncorrecto.setVisible(false);

		JPanel panel_Sur = new JPanel();
		frmLogin.getContentPane().add(panel_Sur, BorderLayout.SOUTH);


		//BOTON LOGIN ENTRAR
		JButton btnLogin = new JButton("Login");
		btnLogin.setVerticalAlignment(SwingConstants.BOTTOM);
		panel_Sur.add(btnLogin);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean login = false;
				if (!ControladorAppChat.getUnicaInstancia().existeUsuario(textLogin.getText())) {
					JOptionPane.showMessageDialog(frmLogin, "El Usuario indicado no existe");
				} 
				else if (ControladorAppChat.getUnicaInstancia().comprobarContrasena(textLogin.getText(),new String(textPassword.getPassword()))){
					login = true;
					ControladorAppChat.getUnicaInstancia().setUsuarioActual(textLogin.getText());
					
				}
				if (login) {
					ControladorVista window = new ControladorVista(frmLogin.getLocation());
					//ControladorVista window = new ControladorVista();
					window.mostrarVentana();
					frmLogin.dispose();
				}
				else {
					lblloginIncorrecto.setVisible(true);
				}
			}
		});

		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		panel_Sur.add(rigidArea_1);

		// BOTON REGISTRO
		JButton btnRegistro = new JButton("Registro");
		btnRegistro.setVerticalAlignment(SwingConstants.BOTTOM);
		panel_Sur.add(btnRegistro);
		btnRegistro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblloginIncorrecto.setVisible(false);
				frmLogin.setTitle("Registro");
				new PanelRegistro(frmLogin);
			}
		});

		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		rigidArea.setPreferredSize(new Dimension(160, 40));
		panel_Sur.add(rigidArea);

		// BOTON SALIR
		JButton btnSalir = new JButton("Salir");
		btnSalir.setVerticalAlignment(SwingConstants.BOTTOM);
		panel_Sur.add(btnSalir);
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmLogin.dispose(); /* Termina la maquina virtual */
				System.exit(0); /* no sera necesario en este caso */
			}
		});
	}

	
	public void mostrarVentana() {
		frmLogin.setVisible(true);

	}
/*
	public static void main(final String[] args) throws ClassNotFoundException, InstantiationException,
		IllegalAccessException, UnsupportedLookAndFeelException {
		//HiFiLookAndFeel.setCurrentTheme(props);
		//UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PanelLogin ventana = new PanelLogin();
					ventana.mostrarVentana();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

}
