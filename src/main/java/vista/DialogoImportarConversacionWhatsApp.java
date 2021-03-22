package vista;

import java.awt.BorderLayout;


import javax.swing.JButton;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;


import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Color;

import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import controlador.ControladorAppChat;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.UIManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JRadioButton;
import java.awt.Component;


import javax.swing.Box;
import java.awt.Dimension;


/*
 * Esta clase se encarga de desplegar una ventana para importar conversaciones de whatsapp.
 * Se pueden importar conversaciones de las plataformas IOS y Android , esta utima con dos formatos distintos.
 * Es responsabilidad del usuario que el fichero seleccionado se corresponda con las opciones de formato seleccionadadas.
 * asi como que la conversacion importada este relacionada con él y alguno de sus contactos registrados.
 */
public class DialogoImportarConversacionWhatsApp extends JDialog {


	private static final long serialVersionUID = 7614050731714885082L;
	private final JPanel contentPanel = new JPanel();
	private JRadioButton rdbtnIOS;
	private JRadioButton rdbtnAndroid;
	private JRadioButton rdbtnAndroid1formato;
	private JRadioButton rdbtnAndroid2formato;
	private JRadioButton rdbtnIOSformato;
	private String fichero;
	private JFileChooser chooser;
	private final static String IOS = "IOS";
	private final static String ANDROID = "Android";
	private final static String FORMATO1 = "d/M/yy H:mm";
	private final static String FORMATO2 = "d/M/yyyy H:mm";
	private final static String FORMATO3 = "d/M/yy H:mm:ss";
	

	public static void main() {
		try {
			DialogoImportarConversacionWhatsApp dialog = new DialogoImportarConversacionWhatsApp();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DialogoImportarConversacionWhatsApp() {
		setMinimumSize(new Dimension(680, 590));
		
		ButtonGroup plataforma= new ButtonGroup();
		ButtonGroup formatoChat= new ButtonGroup();
		
		setTitle("Importar Chat WhatsApp");
		setIconImage(Toolkit.getDefaultToolkit().getImage(DialogoImportarConversacionWhatsApp.class.getResource("/recursosVista/100x100.png")));
		setBounds(100, 100, 680, 590);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{20, 0, 0, 0, 20, 0};
		gbl_contentPanel.rowHeights = new int[]{20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblServicioPremium = new JLabel("");
			lblServicioPremium.setFont(new Font("Rubik", Font.BOLD, 13));
			lblServicioPremium.setIcon(new ImageIcon(DialogoImportarConversacionWhatsApp.class.getResource("/recursosVista/importar.png")));
			GridBagConstraints gbc_lblServicioPremium = new GridBagConstraints();
			gbc_lblServicioPremium.insets = new Insets(0, 0, 5, 5);
			gbc_lblServicioPremium.gridx = 2;
			gbc_lblServicioPremium.gridy = 1;
			contentPanel.add(lblServicioPremium, gbc_lblServicioPremium);
		}
		{
			JTextPane txtpnPorSolo = new JTextPane();
			txtpnPorSolo.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Informaci\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			txtpnPorSolo.setEditable(false);
			txtpnPorSolo.setText("Se pueden importar conversaciones de WhatsApp a UmuChat, para ello: \r\n1º Seleccione el fichero de conversacion a exportar\r\n2º Seleccione el formato de la conversacion que va a exportar segun su plataforma\r\n");
			GridBagConstraints gbc_txtpnPorSolo = new GridBagConstraints();
			gbc_txtpnPorSolo.gridheight = 3;
			gbc_txtpnPorSolo.insets = new Insets(0, 0, 5, 5);
			gbc_txtpnPorSolo.fill = GridBagConstraints.BOTH;
			gbc_txtpnPorSolo.gridx = 2;
			gbc_txtpnPorSolo.gridy = 2;
			contentPanel.add(txtpnPorSolo, gbc_txtpnPorSolo);
		}
		{
			JPanel panel = new JPanel();
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.insets = new Insets(0, 0, 5, 5);
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 2;
			gbc_panel.gridy = 6;
			contentPanel.add(panel, gbc_panel);
			{
				JLabel label = new JLabel("1º");
				panel.add(label);
			}
			{
				JButton btnSeleccionarFichero = new JButton("Seleccionar Fichero Conversacion");
				btnSeleccionarFichero.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						//La seleccion del fichero solo permite ficheros con extension .txt para evitar errores
						chooser = new JFileChooser();
						FileFilter imageFilter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
						chooser.setFileFilter(imageFilter);
						chooser.showOpenDialog(ControladorVista.frmMain);
						
						File fich = chooser.getSelectedFile();
						
						if( fich  != null){
							fichero =  fich.getAbsolutePath();
							JOptionPane.showMessageDialog(ControladorVista.frmMain, "Fichero seleccionado correctamente");
						}else {
								JOptionPane.showMessageDialog(ControladorVista.frmMain, "El fichero no se ha podido seleccionar");
						}
						
					}
				});
				panel.add(btnSeleccionarFichero);
			}
			{
				Component horizontalStrut = Box.createHorizontalStrut(20);
				horizontalStrut.setMinimumSize(new Dimension(50, 0));
				horizontalStrut.setMaximumSize(new Dimension(50, 32767));
				panel.add(horizontalStrut);
			}
			{
				JLabel label = new JLabel("2º");
				panel.add(label);
			}
			{
				rdbtnAndroid = new JRadioButton("Android");
				panel.add(rdbtnAndroid);
				
			}
			{
				rdbtnIOS = new JRadioButton("IOS");
				panel.add(rdbtnIOS);
				
			}

		}
		
		plataforma.add(rdbtnAndroid);
		plataforma.add(rdbtnIOS);
		
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Formato de chat WhatsApp", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.insets = new Insets(0, 0, 5, 5);
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 2;
			gbc_panel.gridy = 7;
			contentPanel.add(panel, gbc_panel);
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			{
				JPanel PanelDescuento = new JPanel();
				panel.add(PanelDescuento);
				{
					rdbtnAndroid1formato = new JRadioButton("21/10/19 19:44     - Usuario: Ejemplo Android 1\r\n");
					PanelDescuento.add(rdbtnAndroid1formato);
				}
			}
			{
				JPanel panel_1 = new JPanel();
				panel.add(panel_1);
				{
					 rdbtnIOSformato = new JRadioButton("[21/10/19 19:44:41] Usuario: Ejemplo IOS       \r\n");
					panel_1.add(rdbtnIOSformato);
				}
			}
			formatoChat.add(rdbtnIOSformato);
			{
				JPanel panelPrecio = new JPanel();
				panel.add(panelPrecio);
				{
					 rdbtnAndroid2formato = new JRadioButton("21/10/2019 19:44 - Usuario: Ejemplo Android 2");
					panelPrecio.add(rdbtnAndroid2formato);
				}
			}
		}
		
		// Se han creado un grupo de botones para la seleccion unica
		// Cuando se selecciona una plataforma u otra, se ocultan los formatos que no estan disponibles para dicha plataforma
		formatoChat.add(rdbtnAndroid1formato);
		formatoChat.add(rdbtnAndroid2formato);
		rdbtnAndroid.setSelected(true);
		rdbtnAndroid1formato.setSelected(true);
		rdbtnIOSformato.setVisible(false);
		
		rdbtnAndroid.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (rdbtnAndroid.isSelected()){
					
					rdbtnIOSformato.setVisible(false);
					rdbtnAndroid1formato.setSelected(true);
					rdbtnAndroid1formato.setVisible(true);
					rdbtnAndroid2formato.setVisible(true);					
				}
				
			}
		});
		
		
		rdbtnIOS.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 if (rdbtnIOS.isSelected()) {
						rdbtnIOSformato.setVisible(true);
						rdbtnIOSformato.setSelected(true);
						rdbtnAndroid1formato.setVisible(false);
						rdbtnAndroid2formato.setVisible(false);
					}
			}
		});
		
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.insets = new Insets(0, 0, 5, 5);
			gbc_panel.fill = GridBagConstraints.HORIZONTAL;
			gbc_panel.gridx = 2;
			gbc_panel.gridy = 9;
			contentPanel.add(panel, gbc_panel);
			{
				JButton btnImportar = new JButton("Importar Chat");
				panel.add(btnImportar);
				btnImportar.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						String formato;
						String plataforma;
						
						if (rdbtnAndroid.isSelected()){
							plataforma = ANDROID;
							if(rdbtnAndroid1formato.isSelected()) {
								formato = FORMATO1;
							}else {
								formato = FORMATO2 ;
							}
							
						
						}else{
							plataforma = IOS;
							formato = FORMATO3;
						}

						if (fichero!=null) {
							ControladorAppChat.getUnicaInstancia().exportarChat(fichero,plataforma, formato);
							ControladorVista.ActualizarPnlChat();
						}
						dispose();
					}
				});
			}
		}
	}

}


