package vista;

import java.awt.BorderLayout;


import javax.swing.JButton;
import javax.swing.JDialog;


import javax.swing.JPanel;


import controlador.ControladorAppChat;
import modelo.ContactoIndividual;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.awt.Dimension;

/*
 * Esta clase se encarga de desplegar una ventana flotante y recoger los datos necesarios
 * que posteriormente el controlador Appchat usara para la creacion de un contacto.
 * Esta clase es reutilizada a la hora de modificar un contacto individual, no permitiendo alterar
 * algunos campos como el numero de telefono. 
 */
public class DialoCrearContacto extends JDialog {
	

	private static final long serialVersionUID = 6867645302356433396L;
	private JTextField textFieldNombre;
	private JTextField textFieldTelf;
	private JTextField textFieldEmail;
	private JLabel lblNombreErr;
	private JLabel lblTelfErr;
	private JLabel lblMensajeError;
	private ControladorAppChat controlador = ControladorAppChat.getUnicaInstancia();

	public static void main(ContactoIndividual contactomodificar) {
		try {
			DialoCrearContacto dialog = new DialoCrearContacto(contactomodificar);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DialoCrearContacto(ContactoIndividual contactomodificar) {
		setMinimumSize(new Dimension(420, 420));
		if (contactomodificar!=null) {
			setTitle("Modificar Contacto");
		}
		setTitle("Registro Contacto");
		setIconImage(Toolkit.getDefaultToolkit().getImage(DialoCrearContacto.class.getResource("/recursosVista/100x100.png")));
		
		setBounds(100, 100, 440, 422);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel panel = new JPanel();
			getContentPane().add(panel, BorderLayout.CENTER);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{20, 0, 0, 0, 25, 20, 0};
			gbl_panel.rowHeights = new int[]{20, 0, 0, 0, 0, 0, 0, 0, 20, 0};
			gbl_panel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			{
				JPanel panel_1 = new JPanel();
				GridBagConstraints gbc_panel_1 = new GridBagConstraints();
				gbc_panel_1.insets = new Insets(0, 0, 5, 5);
				gbc_panel_1.fill = GridBagConstraints.BOTH;
				gbc_panel_1.gridx = 2;
				gbc_panel_1.gridy = 1;
				panel.add(panel_1, gbc_panel_1);
				{
					JLabel label = new JLabel("");
					label.setIcon(new ImageIcon(DialoCrearContacto.class.getResource("/recursosVista/profile-user.png")));
					panel_1.add(label);
				}
			}
			{
				JLabel label = new JLabel("Nombre : ");
				GridBagConstraints gbc_label = new GridBagConstraints();
				gbc_label.anchor = GridBagConstraints.EAST;
				gbc_label.insets = new Insets(0, 0, 5, 5);
				gbc_label.gridx = 1;
				gbc_label.gridy = 3;
				panel.add(label, gbc_label);
			}
			{
				textFieldNombre = new JTextField();
				textFieldNombre.setColumns(10);
				GridBagConstraints gbc_textField = new GridBagConstraints();
				gbc_textField.gridwidth = 2;
				gbc_textField.insets = new Insets(0, 0, 5, 5);
				gbc_textField.fill = GridBagConstraints.HORIZONTAL;
				gbc_textField.gridx = 2;
				gbc_textField.gridy = 3;
				panel.add(textFieldNombre, gbc_textField);
				if (contactomodificar != null) {
					textFieldNombre.setText(contactomodificar.getNombre());
				}
				
			}
			{
				lblNombreErr = new JLabel("*");
				lblNombreErr.setForeground(Color.RED);
				GridBagConstraints gbc_lblNombreErr = new GridBagConstraints();
				gbc_lblNombreErr.insets = new Insets(0, 0, 5, 5);
				gbc_lblNombreErr.gridx = 4;
				gbc_lblNombreErr.gridy = 3;
				panel.add(lblNombreErr, gbc_lblNombreErr);
				lblNombreErr.setVisible(false);
			}
			{
				JLabel label = new JLabel("Teléfono : ");
				GridBagConstraints gbc_label = new GridBagConstraints();
				gbc_label.anchor = GridBagConstraints.EAST;
				gbc_label.insets = new Insets(0, 0, 5, 5);
				gbc_label.gridx = 1;
				gbc_label.gridy = 4;
				panel.add(label, gbc_label);
			}
			{
				textFieldTelf = new JTextField();
				textFieldTelf.setColumns(10);
				GridBagConstraints gbc_textFieldTelf = new GridBagConstraints();
				gbc_textFieldTelf.insets = new Insets(0, 0, 5, 5);
				gbc_textFieldTelf.fill = GridBagConstraints.HORIZONTAL;
				gbc_textFieldTelf.gridx = 2;
				gbc_textFieldTelf.gridy = 4;
				panel.add(textFieldTelf, gbc_textFieldTelf);
				if (contactomodificar != null) {
					textFieldTelf.setText(Integer.toString(contactomodificar.getTelefono()));
					textFieldTelf.setEditable(false);
				}
				
			}
			{
				lblTelfErr = new JLabel("*");
				lblTelfErr.setForeground(Color.RED);
				GridBagConstraints gbc_lblTelfErr = new GridBagConstraints();
				gbc_lblTelfErr.insets = new Insets(0, 0, 5, 5);
				gbc_lblTelfErr.gridx = 3;
				gbc_lblTelfErr.gridy = 4;
				panel.add(lblTelfErr, gbc_lblTelfErr);
				lblTelfErr.setVisible(false);
			}
			{
				JLabel label = new JLabel("Email : ");
				GridBagConstraints gbc_label = new GridBagConstraints();
				gbc_label.anchor = GridBagConstraints.EAST;
				gbc_label.insets = new Insets(0, 0, 5, 5);
				gbc_label.gridx = 1;
				gbc_label.gridy = 5;
				panel.add(label, gbc_label);
			}
			{
				textFieldEmail = new JTextField();
				textFieldEmail.setColumns(10);
				GridBagConstraints gbc_textFieldEmail = new GridBagConstraints();
				gbc_textFieldEmail.gridwidth = 2;
				gbc_textFieldEmail.insets = new Insets(0, 0, 5, 5);
				gbc_textFieldEmail.fill = GridBagConstraints.HORIZONTAL;
				gbc_textFieldEmail.gridx = 2;
				gbc_textFieldEmail.gridy = 5;
				panel.add(textFieldEmail, gbc_textFieldEmail);
				
				if (contactomodificar != null) {
					// El email no se puede modificar ya que esta asociado a un usuario registrado, se obtiene a partir de este usuario, no se establece aqui
					textFieldEmail.setEditable(false);
				}
			}
			{
				lblMensajeError = new JLabel("* Por favor, rellene los campos marcados");
				lblMensajeError.setForeground(Color.RED);
				GridBagConstraints gbc_lblMensajeError = new GridBagConstraints();
				gbc_lblMensajeError.gridwidth = 3;
				gbc_lblMensajeError.insets = new Insets(0, 0, 5, 5);
				gbc_lblMensajeError.gridx = 2;
				gbc_lblMensajeError.gridy = 6;
				panel.add(lblMensajeError, gbc_lblMensajeError);
				lblMensajeError.setVisible(false);
			}
			{
				JPanel panel_1 = new JPanel();
				GridBagConstraints gbc_panel_1 = new GridBagConstraints();
				gbc_panel_1.insets = new Insets(0, 0, 5, 5);
				gbc_panel_1.fill = GridBagConstraints.BOTH;
				gbc_panel_1.gridx = 2;
				gbc_panel_1.gridy = 7;
				panel.add(panel_1, gbc_panel_1);
				{
					JButton btnRegistrar = new JButton("Registrar");
					panel_1.add(btnRegistrar);
					btnRegistrar.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e) {
							comprobarDatosEntrada(contactomodificar);
						}

					});
				}
				{
					JButton btnNewButton = new JButton("Cancelar");
					panel_1.add(btnNewButton);
					btnNewButton.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e) {
							dispose();
						}
					});
				}
			}
		}
	}

	/*
	 * Verifica que los campos necesarios no estan vacios e invoca al controlador 
	 * para crear el contacto 
	 */
	private void comprobarDatosEntrada(ContactoIndividual contactomodificar) {
		boolean validar = false;
		
		if (textFieldNombre.getText().isEmpty()) {
			lblNombreErr.setVisible(true);
			validar = true;
		}
		if (textFieldTelf.getText().isEmpty()) {
			lblTelfErr.setVisible(true);
			validar = true;
		}

		if(!validar) {
			ocultarErrores();
			/* Se utiliza el parametro contacto a modificar para determinar si se esta modificando un contacto.
			/  o se esta creando un nuevo contacto
			*/ 
			if (contactomodificar != null) {
				ControladorAppChat.getUnicaInstancia().modificarContactoIndividual(contactomodificar, ControladorAppChat.getUnicaInstancia().getUsuarioActual(), textFieldNombre.getText());
				JOptionPane.showMessageDialog(ControladorVista.frmMain, "Contacto Modificado Correctamente");
				ControladorVista.ActualizarVentanasImplicanContactos();
			}else {
				if(this.controlador.existeTelf(Integer.parseInt(textFieldTelf.getText())) && !this.controlador.esxisteContacto(Integer.parseInt(textFieldTelf.getText()))){
					ControladorAppChat.getUnicaInstancia().crearContactoIndividual(textFieldNombre.getText(), Integer.parseInt(textFieldTelf.getText()));
					JOptionPane.showMessageDialog(ControladorVista.frmMain, "Contacto Registrado Correctamente");
					ControladorVista.ActualizarVentanasImplicanContactos();
					}
				else JOptionPane.showMessageDialog(ControladorVista.frmMain, "Error el número de telefono indicado no existe");
				limpiarTextos();
				
			}
	
		}
		
		else {
			lblMensajeError.setVisible(true);
		}
		
	}

	private void limpiarTextos() {
		textFieldNombre.setText("");
		textFieldTelf.setText("");
		textFieldEmail.setText("");
	}

	private void ocultarErrores() {
		lblNombreErr.setVisible(false);
		lblTelfErr.setVisible(false);
		lblMensajeError.setVisible(false);
	}
	
}
