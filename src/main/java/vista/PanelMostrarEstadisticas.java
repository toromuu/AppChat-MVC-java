package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;

import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import javax.swing.JTextPane;

import javax.swing.border.TitledBorder;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.PieChart;

import org.knowm.xchart.XChartPanel;

import controlador.ControladorAppChat;
import modelo.Usuario;

import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;


/*
 * Esta clase define un panel para mostrar las estadisticas del usuario
 * Esta funcionalidad solo esta permitida si el usuario es premium, por ello 
 * desde este panel tambien se le permite la posibilidad de convertirse en premium
 * Existen dos tipos de estadisticas, el histograma que muestra la cantidad de mensajes en un mes
 * y el diagrama que detalla el uso de los 6 grupos del usuario con más actividad
 * Ambos diagramas se pueden exportar como un una imagen con extensión jpg o png
 * asi como visualizarlos directamente en la pantalla
 */

public class PanelMostrarEstadisticas extends JPanel {


	private static final String RECURSOS_VISTA_DIAGRAMA_1_PNG = "/recursosVista/diagrama (1).png";
	private static final String RECURSOS_VISTA_HISTOGRAMA_1_PNG = "/recursosVista/histograma (1).png";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame ventana;
	@SuppressWarnings("unused")
	private JPanel jpanelAnterior;
	private JPanel chartPanelHistograma;
	private JPanel chartPanelTarta;
	private JFrame frameChartHistograma;
	private JFrame frameChartTarta;
	
	private Usuario usuario = null;
	private File Directorio;
	private JFileChooser chooser;
	
	
	private DialogoConvertirsePremium dialogoConvertirsePremium;

	public PanelMostrarEstadisticas(JFrame frame) {
		ButtonGroup tipoGrafica= new ButtonGroup();
		
		
		
		chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		dialogoConvertirsePremium = new DialogoConvertirsePremium();
		dialogoConvertirsePremium.setVisible(false);
		
		ButtonGroup formato= new ButtonGroup();
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{50, 0, 0, 0, 0, 50, 0};
		gbl_panel.rowHeights = new int[]{50, 0, 0, 0, 0, 0, 0, 0, 0, 0, 40, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(null, "Informaci\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.gridheight = 3;
		gbc_panel_5.gridwidth = 4;
		gbc_panel_5.insets = new Insets(0, 0, 5, 5);
		gbc_panel_5.fill = GridBagConstraints.VERTICAL;
		gbc_panel_5.gridx = 1;
		gbc_panel_5.gridy = 1;
		panel.add(panel_5, gbc_panel_5);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.Y_AXIS));
		
		JPanel panel_6 = new JPanel();
		panel_5.add(panel_6);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setMinimumSize(new Dimension(50, 0));
		panel_6.add(horizontalStrut);
		
		JTextPane txtpnEstaFuncionalidadSolo = new JTextPane();
		panel_6.add(txtpnEstaFuncionalidadSolo);
		txtpnEstaFuncionalidadSolo.setText("!! Esta funcionalidad solo esta disponible para los Usuarios Premium. ¡¡\r\n\r\n + Se puede generar una gráfica estadística de los mensajes enviados \r\n + Dicha gráfica puede tener el formato de \"Histograma\" o \"Pastel\".\r\n + Puede ser mostrada por pantalla o exportada en los formatos \"jpg\" o \"png\"");
		txtpnEstaFuncionalidadSolo.setEditable(false);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalStrut_1.setMinimumSize(new Dimension(50, 0));
		panel_6.add(horizontalStrut_1);
		
		JPanel panel_7 = new JPanel();
		panel_5.add(panel_7);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		horizontalStrut_2.setMinimumSize(new Dimension(50, 0));
		panel_7.add(horizontalStrut_2);
		
		JButton btnAunNo = new JButton("¿ Aun no eres Premium ?");
		panel_7.add(btnAunNo);
		btnAunNo.setIcon(new ImageIcon(PanelMostrarEstadisticas.class.getResource("/recursosVista/premium.png")));
		
		btnAunNo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogoConvertirsePremium.setVisible(true);
			}
		});
		
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		horizontalStrut_3.setMinimumSize(new Dimension(50, 0));
		panel_7.add(horizontalStrut_3);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridwidth = 4;
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 4;
		panel.add(panel_1, gbc_panel_1);
		
		JLabel label = new JLabel("Tipo de Grafica: ");
		panel_1.add(label);
		
		JLabel lblHistograma = new JLabel("");
		lblHistograma.setIcon(new ImageIcon(PanelMostrarEstadisticas.class.getResource(RECURSOS_VISTA_HISTOGRAMA_1_PNG)));
		GridBagConstraints gbc_lblHistograma = new GridBagConstraints();
		gbc_lblHistograma.gridheight = 3;
		gbc_lblHistograma.insets = new Insets(0, 0, 5, 5);
		gbc_lblHistograma.gridx = 2;
		gbc_lblHistograma.gridy = 5;
		panel.add(lblHistograma, gbc_lblHistograma);
		
		JLabel lblPastel = new JLabel("");
		lblPastel.setIcon(new ImageIcon(PanelMostrarEstadisticas.class.getResource(RECURSOS_VISTA_DIAGRAMA_1_PNG)));
		GridBagConstraints gbc_lblPastel = new GridBagConstraints();
		gbc_lblPastel.gridheight = 3;
		gbc_lblPastel.insets = new Insets(0, 0, 5, 5);
		gbc_lblPastel.gridx = 3;
		gbc_lblPastel.gridy = 5;
		panel.add(lblPastel, gbc_lblPastel);
		
		
		JRadioButton radioHistograma = new JRadioButton("Histrograma");
		radioHistograma.setSelected(true);
		panel_1.add(radioHistograma);
		
	
		
		JRadioButton radioPastel = new JRadioButton("Pastel");
		radioPastel.setAlignmentX(0.5f);
		panel_1.add(radioPastel);
		lblPastel.setVisible(false);
		
		
		tipoGrafica.add(radioHistograma);
		tipoGrafica.add(radioPastel);
		

		radioHistograma.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				lblHistograma.setVisible(true);
				lblPastel.setVisible(false);
			}
		});
		
		radioPastel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				lblHistograma.setVisible(false);
				lblPastel.setVisible(true);
			}
		});

		
		
		
		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.gridwidth = 4;
		gbc_panel_2.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 8;
		panel.add(panel_2, gbc_panel_2);
		
		JLabel lblFormatoExportacin = new JLabel("Formato Exportación: ");
		panel_2.add(lblFormatoExportacin);
		
		JRadioButton radioJPG = new JRadioButton("jpg");
		radioJPG.setSelected(true);
		panel_2.add(radioJPG);
		
		JRadioButton radioPNG = new JRadioButton("png");
		radioPNG.setAlignmentX(0.5f);
		panel_2.add(radioPNG);
		formato.add(radioJPG);
		formato.add(radioPNG);
		
		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.gridwidth = 4;
		gbc_panel_3.insets = new Insets(0, 0, 5, 5);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 1;
		gbc_panel_3.gridy = 9;
		panel.add(panel_3, gbc_panel_3);
		
		

		JButton buttonMostrarPantalla = new JButton("Mostrar por Pantalla");
		panel_3.add(buttonMostrarPantalla);
		buttonMostrarPantalla.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				usuario = ControladorAppChat.getUnicaInstancia().getUsuarioActual();
				
				if (usuario.isPremium()) {
					//buttonMostrarPantalla.setEnabled(true);
					//buttonGenerarArchivo.setEnabled(true);
					
					
					if (radioHistograma.isSelected()) {
						try {
							
							if(frameChartHistograma== null) {
								frameChartHistograma = new JFrame("Grafica Histograma");
								frameChartHistograma.setMinimumSize(new Dimension(420, 420));
							}
							else {
								frameChartHistograma.dispose();
								frameChartHistograma = new JFrame("Grafica Histograma");
								frameChartHistograma.setMinimumSize(new Dimension(420, 420));
								
							}
							
							
							CategoryChart chart = ControladorAppChat.getUnicaInstancia().mostrarHistograma();
							
						    // Add content to the window.
						    XChartPanel<CategoryChart> xChartPanel = new XChartPanel<CategoryChart>(chart);
							chartPanelHistograma = xChartPanel;
						    frameChartHistograma.add(chartPanelHistograma);

						    // Display the window.
						   
						    frameChartHistograma.revalidate();
						    frameChartHistograma.repaint();
						    frameChartHistograma.setVisible(true);
						    
							
							
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else {
						try {
							if( frameChartTarta == null) {
								frameChartTarta = new JFrame("Grafica Tarta");
								frameChartTarta.setMinimumSize(new Dimension(420, 420));
							}
							else {
								frameChartTarta.dispose();
								frameChartTarta = new JFrame("Grafica Tarta");
								frameChartTarta.setMinimumSize(new Dimension(420, 420));
							}
							
							
							
							PieChart chart = ControladorAppChat.getUnicaInstancia().mostrarDiagramaDeTarta();
							 // Add content to the window.
						    XChartPanel<PieChart> xChartPanel = new XChartPanel<PieChart>(chart);
							chartPanelTarta = xChartPanel;
							
						    frameChartTarta.add(chartPanelTarta);

						    // Display the window.
						    
						    //frameChartTarta.pack();
						    //frameChartTarta.repaint();
						    
						    frameChartTarta.revalidate();
						    frameChartTarta.repaint();
						    
						    frameChartTarta.setVisible(true);	
							
		
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				
				else { //SI NO ES PREMIUM
					//buttonMostrarPantalla.setEnabled(false);
					//buttonGenerarArchivo.setEnabled(false);
					dialogoConvertirsePremium.setVisible(true);
				}
				
			}
		});
		
	
		JButton buttonGenerarArchivo = new JButton("Generar Archivo");
		panel_3.add(buttonGenerarArchivo);
		buttonGenerarArchivo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				usuario = ControladorAppChat.getUnicaInstancia().getUsuarioActual();
				
				if (usuario.isPremium()) {
					
						
						chooser.showOpenDialog(ventana);
						Directorio = chooser.getSelectedFile();
						
						if (Directorio == null) {
							JOptionPane.showMessageDialog(ControladorVista.frmMain, "Por favor seleccione un directorio valido");
						}
						else {
							
						
						String Ruta = Directorio.toString();
								
						//JPG
						if (radioJPG.isSelected()) {
							
							
							//HISTOGRAMA
							if (radioHistograma.isSelected()) {
								Ruta =  Ruta + "\\Histograma.jpg";
								try {
									ControladorAppChat.getUnicaInstancia().crearHistograma(Ruta,"JPG");
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							
							//TARTA
							else {
								Ruta =  Ruta + "\\Tarta.jpg";
								try {
									ControladorAppChat.getUnicaInstancia().crearDiagramaDeTarta(Ruta,"JPG");
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							
							
								
						}
						
						///PNG
						else {
							//HISTOGRAMA
							if (radioHistograma.isSelected()) {
								Ruta =  Ruta + "\\Histograma.png";
								try {
									ControladorAppChat.getUnicaInstancia().crearHistograma(Ruta,"PNG");
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							
							//TARTA
							else {
								Ruta =  Ruta + "\\Tarta.png";
								try {
									ControladorAppChat.getUnicaInstancia().crearDiagramaDeTarta(Ruta,"PNG");
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							
						}
						
				}
				}
				
				// SI NO ES PREMIUM
				else {
					dialogoConvertirsePremium.setVisible(true);
				}
				
			}
		});
		
		
		
		JButton btnCancelar = new JButton("Volver");
		panel_3.add(btnCancelar);
		btnCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ventana.setContentPane(ControladorVista.pnlChat);
				ventana.revalidate();
			}
		});
		
		
		ventana=frame;
		jpanelAnterior = (JPanel) ventana.getContentPane();
		
	}

}
