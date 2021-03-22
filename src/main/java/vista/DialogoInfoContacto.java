package vista;

import java.awt.BorderLayout;


import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;

import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Grupo;

import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Esta clase despliega una ventana con la información correspondiente al contacto seleccionado en el chat
 * Imagen, nombre, numero e email.
 * Si se trata de un contacto individual, esta informacion se puede modificar.
 */
public class DialogoInfoContacto extends JDialog {
	
	private static final String RECURSOS_VISTA_100X100_PNG = "/recursosVista/100x100.png";
	private static final String RECURSOS_VISTA_GRUPOICON100_PNG = "/recursosVista/grupoicon100.png";
	private static final String RECURSOS_VISTA_ROJOUSER64_PNG = "/recursosVista/rojouser64.png";
	private static final long serialVersionUID = 96208025478095853L;
	private final JPanel contentPanel = new JPanel();

	public static void main() {
		try {
			DialogoInfoContacto dialog = new DialogoInfoContacto();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DialogoInfoContacto() {
		
		setTitle("Información Contacto");
		setIconImage(Toolkit.getDefaultToolkit().getImage(DialogoInfoContacto.class.getResource(RECURSOS_VISTA_100X100_PNG)));
		setMinimumSize(new Dimension(500, 500));
		setBounds(100, 100, 516, 500);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setPreferredSize(new Dimension(350, 350));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{20, 0, 0, 0, 0, 0, 20, 0};
		gbl_contentPanel.rowHeights = new int[]{20, 0, 0, 0, 0, 0, 20, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, 1.0, 0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Imagen Contacto", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridheight = 3;
		gbc_panel.gridwidth = 5;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 1;
		contentPanel.add(panel, gbc_panel);
		
		JLabel lblImagenContacto = new JLabel("");
		//PanelChat auxPrimeraInstancia = ControladorVista.getPanelChat();
		//if ( auxPrimeraInstancia != null ) {
			
			if (  ControladorVista.getImagenContactoActual() != null ) {
				//OBTENEMOS LA IMAGEN DEL USUARIO
				ImageIcon imagenUsuarioIcon = new ImageIcon(ControladorVista.getImagenContactoActual());
				//LA REESCALAMOS
				Image imagenUsuario = imagenUsuarioIcon.getImage();
				Image imagenReescalada = imagenUsuario.getScaledInstance(300, 300, java.awt.Image.SCALE_SMOOTH );
				//MOSTRAMOS LA IMAGEN REESCALADA
				lblImagenContacto.setIcon(new ImageIcon(imagenReescalada));	
			}
			
			else {
				// Muestra la imagen del usuario asociado al contacto, si no se ha establecido  ninguna antes se muestra una por defecto
				if (ControladorVista.getContactoActual() instanceof ContactoIndividual) {
					lblImagenContacto.setIcon(new ImageIcon(DialogoInfoContacto.class.getResource( RECURSOS_VISTA_ROJOUSER64_PNG )));
				}
				else {
					lblImagenContacto.setIcon(new ImageIcon(DialogoInfoContacto.class.getResource( RECURSOS_VISTA_GRUPOICON100_PNG )));
				}
				
			}
			
			panel.add(lblImagenContacto);
			JPanel panel_1 = new JPanel();
			panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Info Contacto", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
			GridBagConstraints gbc_panel_1 = new GridBagConstraints();
			gbc_panel_1.gridwidth = 5;
			gbc_panel_1.insets = new Insets(0, 0, 5, 5);
			gbc_panel_1.fill = GridBagConstraints.BOTH;
			gbc_panel_1.gridx = 1;
			gbc_panel_1.gridy = 5;
			contentPanel.add(panel_1, gbc_panel_1);
			GridBagLayout gbl_panel_1 = new GridBagLayout();
			gbl_panel_1.columnWidths = new int[]{10, 0, 0, 0, 0, 10, 0};
			gbl_panel_1.rowHeights = new int[]{10, 0, 0, 16, 10, 0};
			gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_panel_1.rowWeights = new double[]{0.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
			panel_1.setLayout(gbl_panel_1);
			
			JLabel label = new JLabel("Nombre:");
			label.setFont(new Font("Tahoma", Font.BOLD, 13));
			GridBagConstraints gbc_label = new GridBagConstraints();
			gbc_label.anchor = GridBagConstraints.NORTHEAST;
			gbc_label.insets = new Insets(0, 0, 5, 5);
			gbc_label.gridx = 1;
			gbc_label.gridy = 1;
			panel_1.add(label, gbc_label);
			
			JLabel lblNombreCampo = new JLabel(ControladorVista.getNombreContactoActual());
			GridBagConstraints gbc_lblNombreCampo = new GridBagConstraints();
			gbc_lblNombreCampo.anchor = GridBagConstraints.WEST;
			gbc_lblNombreCampo.gridwidth = 3;
			gbc_lblNombreCampo.insets = new Insets(0, 0, 5, 5);
			gbc_lblNombreCampo.gridx = 2;
			gbc_lblNombreCampo.gridy = 1;
			panel_1.add(lblNombreCampo, gbc_lblNombreCampo);
			
			
			JLabel lblMovil = new JLabel("Movil:");
			lblMovil.setFont(new Font("Tahoma", Font.BOLD, 13));
			GridBagConstraints gbc_lblMovil = new GridBagConstraints();
			gbc_lblMovil.anchor = GridBagConstraints.EAST;
			gbc_lblMovil.insets = new Insets(0, 0, 5, 5);
			gbc_lblMovil.gridx = 1;
			gbc_lblMovil.gridy = 2;
			panel_1.add(lblMovil, gbc_lblMovil);
			
			JLabel lblMovilCampo = new JLabel(ControladorVista.getTelefonoContactoActual());
			GridBagConstraints gbc_lblMovilCampo = new GridBagConstraints();
			gbc_lblMovilCampo.anchor = GridBagConstraints.WEST;
			gbc_lblMovilCampo.gridwidth = 3;
			gbc_lblMovilCampo.insets = new Insets(0, 0, 5, 5);
			gbc_lblMovilCampo.gridx = 2;
			gbc_lblMovilCampo.gridy = 2;
			panel_1.add(lblMovilCampo, gbc_lblMovilCampo);
			
			JLabel lblEmail = new JLabel("Email:");
			lblEmail.setFont(new Font("Tahoma", Font.BOLD, 13));
			GridBagConstraints gbc_lblEmail = new GridBagConstraints();
			gbc_lblEmail.anchor = GridBagConstraints.EAST;
			gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
			gbc_lblEmail.gridx = 1;
			gbc_lblEmail.gridy = 3;
			panel_1.add(lblEmail, gbc_lblEmail);
			
			
			JLabel lblCorreoCampo = new JLabel(ControladorVista.getEmailContactoActual());
			GridBagConstraints gbc_lblCorreoCampo = new GridBagConstraints();
			gbc_lblCorreoCampo.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblCorreoCampo.gridwidth = 3;
			gbc_lblCorreoCampo.insets = new Insets(0, 0, 5, 5);
			gbc_lblCorreoCampo.gridx = 2;
			gbc_lblCorreoCampo.gridy = 3;
			panel_1.add(lblCorreoCampo, gbc_lblCorreoCampo);
			
			JButton btnModificarInformacion = new JButton("Modificar Informacion");
			GridBagConstraints gbc_btnModificarInformacion = new GridBagConstraints();
			gbc_btnModificarInformacion.insets = new Insets(0, 0, 0, 5);
			gbc_btnModificarInformacion.gridx = 2;
			gbc_btnModificarInformacion.gridy = 4;
			panel_1.add(btnModificarInformacion, gbc_btnModificarInformacion);
			
			Contacto contactoModificar =  ControladorVista.getContactoActual();
			
			//Si es un grupo no se podra modificar.A no ser que sea admin, pero ya hay otra pestaña para ello 
			if (contactoModificar instanceof Grupo ) {
				btnModificarInformacion.setEnabled(false);
			}
			
			btnModificarInformacion.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					ContactoIndividual contactoAux = (ContactoIndividual) contactoModificar;
					DialoCrearContacto modificarUsuario = new DialoCrearContacto(contactoAux);
					modificarUsuario.setVisible(true);
					ControladorVista.dialogoInfoContacto.setVisible(false);
				}
			});
			
		}
		

		
	//}

}
