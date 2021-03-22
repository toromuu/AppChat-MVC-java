package vista;


import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Date;


import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.JPasswordField;

import javax.swing.JTextField;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import com.toedter.calendar.JDateChooser;

import controlador.ControladorAppChat;

/*
 * Esta clase define un panel de registro
 * El panel de registro cuenta con varios campos que el usuario debe
 * rellenar, alguno de ellos son opcionales.
 * Con el boton de registro se comprueba que todos los campos obligatorios han sido rellenados
 * correctamente y se procede al registro del usuario.
 * Un elemento interesante es el JDateChooser el cual permite desplegar un minicalendario
 * en el cual el usuario puede indicar su fecha de nacimiento.
 */

public class PanelRegistro extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JFrame ventana;
	private JPanel jpanelAnterior;
	
	// Campos textField
	private JTextField textFieldNombre;
	private JTextField textFieldTelf;
	private JDateChooser botonCalendario;
	private JTextField textFieldEmail;
	private JTextField textFieldUsuario;
	private JTextField textFieldClave1;
	private JTextField textFieldClave2;
	
	//Etiquetas de Error
	private JLabel lblNombreErr;
	private JLabel lblTelfErr;
	private JLabel lblFechaErr;
	private JLabel lblEmailErr;
	private JLabel lblUsuarioErr;
	private JLabel lblClaveErr;
	private JLabel lblMensajeError;
	
	//Botones
	private JButton BotonRegistrar;
	private JButton BotonCancelar;
	
	private DialogoRegistroOk registroOk;

	
	public PanelRegistro(JFrame frame){
		ventana=frame;
		jpanelAnterior = (JPanel) ventana.getContentPane();
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{10, 0, 0, 0, 0, 0, 0, 0, 0, 23, 0, -12, 0, 0, 0, 0, 0, 20, 1, 15, 0, 10, 0};
		gridBagLayout.rowHeights = new int[]{10, 0, 30, 0, 0, 0, 0, 0, 0, 10, 0, 0, 10, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblNombre = new JLabel("Nombre : ");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 2;
		gbc_lblNombre.gridy = 3;
		add(lblNombre, gbc_lblNombre);
		
		textFieldNombre = new JTextField();
		GridBagConstraints gbc_textFieldNombre = new GridBagConstraints();
		gbc_textFieldNombre.gridwidth = 16;
		gbc_textFieldNombre.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNombre.gridx = 3;
		gbc_textFieldNombre.gridy = 3;
		add(textFieldNombre, gbc_textFieldNombre);
		textFieldNombre.setColumns(10);
		
		JLabel lblTelfono = new JLabel("Tel\u00E9fono : ");
		GridBagConstraints gbc_lblTelfono = new GridBagConstraints();
		gbc_lblTelfono.anchor = GridBagConstraints.EAST;
		gbc_lblTelfono.insets = new Insets(0, 0, 5, 5);
		gbc_lblTelfono.gridx = 2;
		gbc_lblTelfono.gridy = 4;
		add(lblTelfono, gbc_lblTelfono);
		
		textFieldTelf = new JTextField();
		GridBagConstraints gbc_textFieldTelf = new GridBagConstraints();
		gbc_textFieldTelf.gridwidth = 16;
		gbc_textFieldTelf.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldTelf.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldTelf.gridx = 3;
		gbc_textFieldTelf.gridy = 4;
		add(textFieldTelf, gbc_textFieldTelf);
		textFieldTelf.setColumns(10);
		
		JLabel lblFechaDeNacimiento = new JLabel(" Fecha de Nacimiento : ");
		GridBagConstraints gbc_lblFechaDeNacimiento = new GridBagConstraints();
		gbc_lblFechaDeNacimiento.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaDeNacimiento.gridx = 2;
		gbc_lblFechaDeNacimiento.gridy = 5;
		add(lblFechaDeNacimiento, gbc_lblFechaDeNacimiento);
		
		botonCalendario = new JDateChooser();
		botonCalendario.setDateFormatString("dd/MM/yyyy");
		GridBagConstraints gbc_BotonCalendario = new GridBagConstraints();
		gbc_BotonCalendario.fill = GridBagConstraints.BOTH;
		gbc_BotonCalendario.gridwidth = 9;
		gbc_BotonCalendario.insets = new Insets(0, 0, 5, 5);
		gbc_BotonCalendario.gridx = 3;
		gbc_BotonCalendario.gridy = 5;
		add(botonCalendario, gbc_BotonCalendario);
		
		
		JLabel lblEmail = new JLabel("Email : ");
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.EAST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 2;
		gbc_lblEmail.gridy = 6;
		add(lblEmail, gbc_lblEmail);
		
		textFieldEmail = new JTextField();
		GridBagConstraints gbc_textFieldEmail = new GridBagConstraints();
		gbc_textFieldEmail.gridwidth = 6;
		gbc_textFieldEmail.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldEmail.gridx = 3;
		gbc_textFieldEmail.gridy = 6;
		add(textFieldEmail, gbc_textFieldEmail);
		textFieldEmail.setColumns(10);
		
		lblEmailErr = new JLabel("*");
		lblEmailErr.setForeground(Color.RED);
		lblEmailErr.setBackground(Color.WHITE);
		GridBagConstraints gbc_lblEmailErr = new GridBagConstraints();
		gbc_lblEmailErr.anchor = GridBagConstraints.WEST;
		gbc_lblEmailErr.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmailErr.gridx = 9;
		gbc_lblEmailErr.gridy = 6;
		add(lblEmailErr, gbc_lblEmailErr);
		lblEmailErr.setVisible(false);
		
		JLabel lblUsuario = new JLabel("Usuario :");
		GridBagConstraints gbc_lblUsuario = new GridBagConstraints();
		gbc_lblUsuario.anchor = GridBagConstraints.EAST;
		gbc_lblUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsuario.gridx = 2;
		gbc_lblUsuario.gridy = 7;
		add(lblUsuario, gbc_lblUsuario);
		
		textFieldUsuario = new JTextField();
		GridBagConstraints gbc_textFieldUsuario = new GridBagConstraints();
		gbc_textFieldUsuario.gridwidth = 6;
		gbc_textFieldUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldUsuario.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldUsuario.gridx = 3;
		gbc_textFieldUsuario.gridy = 7;
		add(textFieldUsuario, gbc_textFieldUsuario);
		textFieldUsuario.setColumns(10);
		
		lblUsuarioErr = new JLabel("*");
		lblUsuarioErr.setForeground(Color.RED);
		GridBagConstraints gbc_lblUsuarioErr = new GridBagConstraints();
		gbc_lblUsuarioErr.anchor = GridBagConstraints.WEST;
		gbc_lblUsuarioErr.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsuarioErr.gridx = 9;
		gbc_lblUsuarioErr.gridy = 7;
		add(lblUsuarioErr, gbc_lblUsuarioErr);
		lblUsuarioErr.setVisible(false);
		
		JLabel lblClave = new JLabel("Clave :");
		GridBagConstraints gbc_lblClave = new GridBagConstraints();
		gbc_lblClave.anchor = GridBagConstraints.EAST;
		gbc_lblClave.insets = new Insets(0, 0, 5, 5);
		gbc_lblClave.gridx = 2;
		gbc_lblClave.gridy = 8;
		add(lblClave, gbc_lblClave);
		
		textFieldClave1 = new JPasswordField();
		GridBagConstraints gbc_textFieldClave1 = new GridBagConstraints();
		gbc_textFieldClave1.gridwidth = 4;
		gbc_textFieldClave1.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldClave1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldClave1.gridx = 3;
		gbc_textFieldClave1.gridy = 8;
		add(textFieldClave1, gbc_textFieldClave1);
		textFieldClave1.setColumns(10);
		
		JLabel lblRepite = new JLabel("Repite : ");
		GridBagConstraints gbc_lblRepite = new GridBagConstraints();
		gbc_lblRepite.anchor = GridBagConstraints.EAST;
		gbc_lblRepite.insets = new Insets(0, 0, 5, 5);
		gbc_lblRepite.gridx = 8;
		gbc_lblRepite.gridy = 8;
		add(lblRepite, gbc_lblRepite);
		
		textFieldClave2 = new JPasswordField();
		GridBagConstraints gbc_textFieldClave2 = new GridBagConstraints();
		gbc_textFieldClave2.gridwidth = 8;
		gbc_textFieldClave2.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldClave2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldClave2.gridx = 11;
		gbc_textFieldClave2.gridy = 8;
		add(textFieldClave2, gbc_textFieldClave2);
		textFieldClave2.setColumns(10);
		
		// Etiquetas de Error
		lblNombreErr = new JLabel("*");
		lblNombreErr.setForeground(Color.RED);
		GridBagConstraints gbc_lblNombreErr = new GridBagConstraints();
		gbc_lblNombreErr.anchor = GridBagConstraints.WEST;
		gbc_lblNombreErr.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombreErr.gridx = 19;
		gbc_lblNombreErr.gridy = 3;
		add(lblNombreErr, gbc_lblNombreErr);
		lblNombreErr.setVisible(false);
		
		lblTelfErr = new JLabel("*");
		lblTelfErr.setForeground(Color.RED);
		GridBagConstraints gbc_lblTelfErr = new GridBagConstraints();
		gbc_lblTelfErr.anchor = GridBagConstraints.WEST;
		gbc_lblTelfErr.insets = new Insets(0, 0, 5, 5);
		gbc_lblTelfErr.gridx = 19;
		gbc_lblTelfErr.gridy = 4;
		add(lblTelfErr, gbc_lblTelfErr);
		lblTelfErr.setVisible(false);
		
		lblFechaErr = new JLabel("*");
		lblFechaErr.setForeground(Color.RED);
		GridBagConstraints gbc_lblFechaErr = new GridBagConstraints();
		gbc_lblFechaErr.anchor = GridBagConstraints.WEST;
		gbc_lblFechaErr.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaErr.gridx = 12;
		gbc_lblFechaErr.gridy = 5;
		add(lblFechaErr, gbc_lblFechaErr);
		lblFechaErr.setVisible(false);
		
		lblClaveErr = new JLabel("*");
		lblClaveErr.setForeground(Color.RED);
		GridBagConstraints gbc_lblClaveErr = new GridBagConstraints();
		gbc_lblClaveErr.anchor = GridBagConstraints.WEST;
		gbc_lblClaveErr.insets = new Insets(0, 0, 5, 5);
		gbc_lblClaveErr.gridx = 19;
		gbc_lblClaveErr.gridy = 8;
		add(lblClaveErr, gbc_lblClaveErr);
		lblClaveErr.setVisible(false);
		
		lblMensajeError = new JLabel("* Por favor, rellene los campos marcados");
		lblMensajeError.setForeground(Color.RED);
		GridBagConstraints gbc_lblMensajeError = new GridBagConstraints();
		gbc_lblMensajeError.gridwidth = 15;
		gbc_lblMensajeError.insets = new Insets(0, 0, 5, 5);
		gbc_lblMensajeError.gridx = 2;
		gbc_lblMensajeError.gridy = 9;
		add(lblMensajeError, gbc_lblMensajeError);
		lblMensajeError.setVisible(false);
		
		 registroOk= new DialogoRegistroOk();
		 registroOk.setVisible(false);
		
		ventana.setContentPane(this);
		
		BotonCancelar = new JButton("Volver");
		GridBagConstraints gbc_BotonCancelar = new GridBagConstraints();
		gbc_BotonCancelar.gridwidth = 10;
		gbc_BotonCancelar.insets = new Insets(0, 0, 5, 5);
		gbc_BotonCancelar.gridx = 2;
		gbc_BotonCancelar.gridy = 10;
		
		//BOTON CANCELAR
		add(BotonCancelar, gbc_BotonCancelar);
		BotonCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ventana.setContentPane(jpanelAnterior);
				ventana.setTitle("Login Gestor Eventos");	
				ventana.revalidate();
			}
		});
		
		//BOTON REGISTRAR
		BotonRegistrar = new JButton("Registrar");
		GridBagConstraints gbc_BotonRegistrar = new GridBagConstraints();
		gbc_BotonRegistrar.gridwidth = 3;
		gbc_BotonRegistrar.insets = new Insets(0, 0, 5, 5);
		gbc_BotonRegistrar.gridx = 14;
		gbc_BotonRegistrar.gridy = 10;
		add(BotonRegistrar, gbc_BotonRegistrar);
		
		BotonRegistrar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(comprobarDatosEntrada()) {
					registroOk.setVisible(true);
				}
			}
		});
		ventana.revalidate(); /*redibujar con el nuevo JPanel*/
		
	}
	
	private boolean comprobarDatosEntrada() {
		boolean validar = true;
		Date date = (Date) botonCalendario.getDate();
		DateFormat df = DateFormat.getDateInstance();
		if (textFieldNombre.getText().isEmpty()) {
			lblNombreErr.setVisible(true);
			validar = false;
		}
		if (textFieldTelf.getText().isEmpty()) {
			lblTelfErr.setVisible(true);
			validar = false;
		}
		if (date == null || df.format(date) == null || df.format(date).isEmpty()) {
			lblFechaErr.setVisible(true);
			validar = false;
		}
		if (textFieldEmail.getText().isEmpty()) {
			lblEmailErr.setVisible(true);
			validar = false;
		}
		if (textFieldUsuario.getText().isEmpty()) {
			lblUsuarioErr.setVisible(true);
			validar = false;
		}
		if (textFieldClave1.getText().isEmpty() || textFieldClave2.getText().isEmpty()) {
			lblClaveErr.setVisible(true);
			validar = false;
		}
		
		if(validar) {
			if (!textFieldClave1.getText().contentEquals(textFieldClave2.getText())) {
				lblMensajeError.setText("* Las claves no coinciden");
				lblMensajeError.setVisible(true);
			}
			else {
			ocultarErrores();
			if (ControladorAppChat.getUnicaInstancia().registrarUsuario(textFieldNombre.getText(), botonCalendario.getDate(), textFieldTelf.getText(),textFieldEmail.getText(),textFieldUsuario.getText(),textFieldClave1.getText())) System.out.println("reg ok");
			else System.out.println("reg no ok");
			limpiarTextos();
			}
		}
		else {
			lblMensajeError.setVisible(true);
		}
		
		return validar;
		
	}

	private void limpiarTextos() {
		textFieldNombre.setText("");
		textFieldTelf.setText("");
		botonCalendario.setDate(null);
		textFieldEmail.setText("");
		textFieldUsuario.setText("");
		textFieldClave1.setText("");
		textFieldClave2.setText("");
	}

	private void ocultarErrores() {
		lblNombreErr.setVisible(false);
		lblTelfErr.setVisible(false);
		lblFechaErr.setVisible(false);
		lblEmailErr.setVisible(false);
		lblUsuarioErr.setVisible(false);
		lblClaveErr.setVisible(false);
		lblMensajeError.setVisible(false);
		
	}
	
}
