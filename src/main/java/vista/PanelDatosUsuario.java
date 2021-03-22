package vista;



import java.awt.Image;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import controlador.ControladorAppChat;
import modelo.Usuario;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.BorderLayout;
import java.awt.Dimension;

public class PanelDatosUsuario extends JPanel {

	
	
	/*
	 * Esta la clase define un panel con los datos del usuario actual
	 * En este panel el usuario puede tanto visualizar sus datos personales como 
	 * modificar alguno de ellos. 
	 * El usuario puede modificar tanto su foto de perfil a traves de Chooser que solo detecta imagenes
	 * asi como establecer una frase de estado.
	 * La imagen que se seleccione de usuario aparecera como foto de contacto en el panel de chat
	 * y el dialogo de informacion de contacto para otros usuarios.
	 */
	
	private static final long serialVersionUID = 1L;
	private static final String SALUDO_DEFECTO = "¡No tienes saludo!";
	
	private Usuario usuario = null;
	
	private JFrame ventana;
	private JPanel panel;
	private JPanel panel_1;
	private JButton btnCambiarSldo;
	private JButton btnCambiarImg;
	private JButton btnVolver;
	private JLabel lblNombre;
	private JLabel lblSaludo;
	
	//JDialogs
	private JDialog ventanaCambiarSaludo;
	
	
	private JFileChooser chooser;
	@SuppressWarnings("unused")
	private File archivoImg;
	
	//Ventana Dialogo Elegir Imagen


	//Ventana Dialogo Elergir Saludo
	private JTextField textoNuevoSaludo;
	
	private JLabel lblEligeUnSaludo;
	private JPanel panel_2;
	private JPanel panel_3;
	
	public PanelDatosUsuario(JFrame frame){
		
		ventana=frame;
		
		//CONFIGURACION DE ELEMENTOS SWING
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{50, 920, 50, 0};
		gridBagLayout.rowHeights = new int[]{50, 227, 50, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 5, 5);
		gbc_panel_3.gridx = 1;
		gbc_panel_3.gridy = 1;
		add(panel_3, gbc_panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		panel_1 = new JPanel();
		panel_3.add(panel_1, BorderLayout.EAST);
		panel_1.setBorder(new TitledBorder(null, "Informaci\u00F3n Personal", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{30, 0, 30, 0};
		gbl_panel_1.rowHeights = new int[]{70, 0, 20, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		lblSaludo = new JLabel("");
		GridBagConstraints gbc_lblSaludo = new GridBagConstraints();
		gbc_lblSaludo.insets = new Insets(0, 0, 0, 5);
		gbc_lblSaludo.anchor = GridBagConstraints.WEST;
		gbc_lblSaludo.gridx = 1;
		gbc_lblSaludo.gridy = 3;
		panel_1.add(lblSaludo, gbc_lblSaludo);
		
		lblSaludo.setText("Saludo: " + "¡No tienes saludo aun!");
		
		panel = new JPanel();
		panel_3.add(panel, BorderLayout.CENTER);
		panel.setBorder(new TitledBorder(null, "Foto Perfil", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JLabel ImagenPerfil = new JLabel("");
		panel.add(ImagenPerfil);
		
		panel_2 = new JPanel();
		panel_3.add(panel_2, BorderLayout.SOUTH);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{50, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 50};
		gbl_panel_2.rowHeights = new int[]{30, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE, 0.0};
		gbl_panel_2.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		btnCambiarImg = new JButton("Cambiar Imagen");
		GridBagConstraints gbc_btnCambiarImg = new GridBagConstraints();
		gbc_btnCambiarImg.gridwidth = 14;
		gbc_btnCambiarImg.insets = new Insets(0, 0, 0, 5);
		gbc_btnCambiarImg.gridx = 1;
		gbc_btnCambiarImg.gridy = 0;
		panel_2.add(btnCambiarImg, gbc_btnCambiarImg);
		
		ventanaCambiarSaludo = new JDialog(ventana, "Cambiar Saludo");
		ventanaCambiarSaludo.setSize(265,180);
		ventanaCambiarSaludo.setLocation(ventana.getLocation());
		ventanaCambiarSaludo.setMinimumSize(new Dimension(420, 180));
		
		
		
		GridBagLayout gridBagLayoutVentanaSldo = new GridBagLayout();
		gridBagLayoutVentanaSldo.columnWidths = new int[]{5, 0, 0, 5, 0};
		gridBagLayoutVentanaSldo.rowHeights = new int[]{10, 0, 20, 0, 20, 0};
		gridBagLayoutVentanaSldo.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayoutVentanaSldo.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		ventanaCambiarSaludo.getContentPane().setLayout(gridBagLayoutVentanaSldo);
		
		lblEligeUnSaludo = new JLabel("Saludo nuevo: ");
		GridBagConstraints gbc_lblEligeUnSaludo = new GridBagConstraints();
		gbc_lblEligeUnSaludo.insets = new Insets(0, 0, 5, 5);
		gbc_lblEligeUnSaludo.gridx = 1;
		gbc_lblEligeUnSaludo.gridy = 1;
		ventanaCambiarSaludo.getContentPane().add(lblEligeUnSaludo, gbc_lblEligeUnSaludo);
		
		textoNuevoSaludo = new JTextField();
		GridBagConstraints gbc_textSaludo = new GridBagConstraints();
		gbc_textSaludo.insets = new Insets(0, 0, 5, 5);
		gbc_textSaludo.fill = GridBagConstraints.HORIZONTAL;
		gbc_textSaludo.gridx = 2;
		gbc_textSaludo.gridy = 1;
		gbc_textSaludo.gridwidth = 4;
		textoNuevoSaludo.setText("");
		ventanaCambiarSaludo.getContentPane().add(textoNuevoSaludo, gbc_textSaludo);
		
		JPanel panelVS = new JPanel();
		GridBagConstraints gbc_panelVS = new GridBagConstraints();
		gbc_panelVS.gridwidth = 2;
		gbc_panelVS.insets = new Insets(0, 0, 5, 5);
		gbc_panelVS.fill = GridBagConstraints.BOTH;
		gbc_panelVS.gridx = 1;
		gbc_panelVS.gridy = 3;
		ventanaCambiarSaludo.getContentPane().add(panelVS, gbc_panelVS);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		usuario = ControladorAppChat.getUnicaInstancia().getUsuarioActual();
		
		lblNombre = new JLabel("Nombre: " + usuario.getNombre());
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.WEST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 1;
		panel_1.add(lblNombre, gbc_lblNombre);
		
		
		// FUNCIONALIDAD DEL PANEL
			
		if (usuario.getImagen() == null) {
			ImagenPerfil.setIcon(new ImageIcon(PanelDatosUsuario.class.getResource("/recursosVista/100x100user.png")));
		}
		else {
			File tempFile = new File(usuario.getImagen());
			 
		    if (tempFile.exists()) {
			//OBTENEMOS LA IMAGEN DEL USUARIO
			ImageIcon imagenUsuarioIcon = new ImageIcon(usuario.getImagen());
			//LA REESCALAMOS
			Image imagenUsuario = imagenUsuarioIcon.getImage();
			Image imagenReescalada = imagenUsuario.getScaledInstance(300, 300, java.awt.Image.SCALE_SMOOTH );
			//MOSTRAMOS LA IMAGEN REESCALADA
			ImagenPerfil.setIcon(new ImageIcon(imagenReescalada));
		    }
		    else {
		    	ImagenPerfil.setIcon(new ImageIcon(PanelDatosUsuario.class.getResource("/recursosVista/100x100user.png")));
		    }
		}
		if (usuario.getEstadoText() == null) {
			lblSaludo.setText("Saludo: " + PanelDatosUsuario.SALUDO_DEFECTO);
		}
		else {
			lblSaludo.setText("Saludo: " + usuario.getEstadoText());
		}
		
		
		
		chooser = new JFileChooser();
		FileFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());

			//Attaching Filter to JFileChooser object
		chooser.setFileFilter(imageFilter);
		
		
		btnCambiarImg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					chooser.showOpenDialog(ventana);
					archivoImg = chooser.getSelectedFile();
					
					if( (archivoImg = chooser.getSelectedFile()) == null){
						JOptionPane.showMessageDialog(ControladorVista.frmMain, "La imagen no se ha podido cambiar");
					
					}
					else {
						
						ControladorAppChat.getUnicaInstancia().cambiarEstado(chooser.getSelectedFile().getPath(),usuario.getEstadoText());
						//OBTENEMOS LA IMAGEN DEL USUARIO
						ImageIcon imagenUsuarioIcon = new ImageIcon(chooser.getSelectedFile().getPath());
						//LA REESCALAMOS
						Image imagenUsuario = imagenUsuarioIcon.getImage();
						Image imagenReescalada = imagenUsuario.getScaledInstance(300, 300, java.awt.Image.SCALE_SMOOTH );
						//MOSTRAMOS LA IMAGEN REESCALADA
						ImagenPerfil.setIcon(new ImageIcon(imagenReescalada));
						ControladorVista.ActualizarImagenDatosPersonales(imagenReescalada);
						ControladorVista.ActualizarImagenContacto();
						//ImagenPerfil.setIcon(new ImageIcon(chooser.getSelectedFile().getPath()));
						//JOptionPane.showMessageDialog(PanelPrincipal.frmMain, "La imagen no se ha podido cambiar");
						chooser.setSelectedFile(null);
					}	
			}
		});
		
		btnVolver = new JButton("Volver");
		GridBagConstraints gbc_btnVolver = new GridBagConstraints();
		gbc_btnVolver.insets = new Insets(0, 0, 0, 5);
		gbc_btnVolver.gridx = 16;
		gbc_btnVolver.gridy = 0;
		panel_2.add(btnVolver, gbc_btnVolver);
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ventana.setContentPane(ControladorVista.pnlChat);	
				ventana.revalidate();
			}
		});
		
		
			
		btnCambiarSldo = new JButton("Cambiar Saludo");
		GridBagConstraints gbc_btnCambiarSldo = new GridBagConstraints();
		gbc_btnCambiarSldo.anchor = GridBagConstraints.EAST;
		gbc_btnCambiarSldo.insets = new Insets(0, 0, 0, 5);
		gbc_btnCambiarSldo.gridwidth = 7;
		gbc_btnCambiarSldo.gridx = 17;
		gbc_btnCambiarSldo.gridy = 0;
		panel_2.add(btnCambiarSldo, gbc_btnCambiarSldo);
		btnCambiarSldo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ventanaCambiarSaludo.setVisible(true);
			}
		});
		
		JButton btnAceptarVS = new JButton("Aceptar");
		panelVS.add(btnAceptarVS);
		btnAceptarVS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ControladorAppChat.getUnicaInstancia().cambiarEstado(usuario.getImagen(),textoNuevoSaludo.getText());
				textoNuevoSaludo.setText("");
				lblSaludo.setText("Saludo: " + usuario.getEstadoText());
				ventanaCambiarSaludo.dispose();
				
			}
		});
		
		JButton btnCancelarVS = new JButton("Cancelar");
		panelVS.add(btnCancelarVS);
		btnCancelarVS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textoNuevoSaludo.setText("");
				ventanaCambiarSaludo.dispose();
			}
		});
		
		
	}

	
}
