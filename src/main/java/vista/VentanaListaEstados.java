package vista;


import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;



import java.awt.Dimension;





import javax.swing.JLabel;

import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


import controlador.ControladorAppChat;
import modelo.Contacto;
import modelo.ContactoIndividual;


import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.List;
import java.util.Map;



import javax.swing.BoxLayout;


import javax.swing.JScrollPane;

import javax.swing.ListSelectionModel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.border.TitledBorder;
import java.awt.Toolkit;
 
/*
 * Esta clase define una ventana flotante en la cual se muestra
 * un listado con todos los contactos individuales y su saludo
 */


public class VentanaListaEstados {

	private static final String RECURSOS_VISTA_100X100_PNG = "/recursosVista/100x100.png";
	private JFrame frmMain;

	public VentanaListaEstados() {
		initialize();
	}

	private void initialize() {
		frmMain = new JFrame();
		frmMain.setMinimumSize(new Dimension(500, 320));
		frmMain.setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaListaEstados.class.getResource(RECURSOS_VISTA_100X100_PNG)));
		frmMain.setLocation(ControladorVista.frmMain.getLocation());
		frmMain.setTitle("Lista Estados");
		frmMain.setBounds(100, 100, 500, 320);
		frmMain.getContentPane().setLayout(new BoxLayout(frmMain.getContentPane(), BoxLayout.X_AXIS));
		
		JPanel panel_1 = new JPanel();
		frmMain.getContentPane().add(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{20, 0, 0, 0, 0, 0, 0, 0, 20, 0};
		gbl_panel_1.rowHeights = new int[]{20, 0, 0, 20, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.gridwidth = 5;
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridheight = 2;
		gbc_panel_3.insets = new Insets(0, 0, 5, 5);
		gbc_panel_3.gridx = 1;
		gbc_panel_3.gridy = 1;
		panel_1.add(panel_3, gbc_panel_3);
		panel_3.setBorder(new TitledBorder(null, "Contacto", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_3.add(scrollPane, BorderLayout.CENTER);
		
		
		//PARA OBTENER EL NOMBRE DEL CONTACTO ASOCIADO AL ESTADO
		List<ContactoIndividual> contactos = ControladorAppChat.getUnicaInstancia().getUsuarioActual().obtenerContactos();
		
		//PARA CREAR EL ARRAY PARA EL MODELO DEL JLIST
		String[] nombreContactos = new String[contactos.size()];
		int i = 0;
		synchronized (nombreContactos) {
			for (Contacto contacto : contactos) {
				nombreContactos[i] = contacto.getNombre();
				i++;
			}
		}
		
		
		JList<String> list = new JList<String>();
		list.setModel(new AbstractListModel<String>() {

			private static final long serialVersionUID = 1L;
			String[] values = nombreContactos;

			public int getSize() {
				return values.length;
			}

			public String getElementAt(int index) {
				return values[index];
			}
		});

		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//OBTENER EL MAPA DE ESTADOS
		Map<Contacto,String> estados = ControladorAppChat.getUnicaInstancia().obtenerListaEstados();
		
		scrollPane.setViewportView(list);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Estado", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.gridheight = 2;
		gbc_panel_2.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 7;
		gbc_panel_2.gridy = 1;
		panel_1.add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{10, 0, 0, 0, 0, 0, 0, 10, 0};
		gbl_panel_2.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JLabel lblTexto = new JLabel("Texto:");
		GridBagConstraints gbc_lblTexto = new GridBagConstraints();
		gbc_lblTexto.insets = new Insets(0, 0, 5, 5);
		gbc_lblTexto.gridx = 1;
		gbc_lblTexto.gridy = 1;
		panel_2.add(lblTexto, gbc_lblTexto);
		
		JLabel lblNewLabel = new JLabel("");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.gridwidth = 5;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 1;
		panel_2.add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblFecha = new JLabel("Fecha:");
		GridBagConstraints gbc_lblFecha = new GridBagConstraints();
		gbc_lblFecha.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblFecha.insets = new Insets(0, 0, 5, 5);
		gbc_lblFecha.gridx = 1;
		gbc_lblFecha.gridy = 2;
		panel_2.add(lblFecha, gbc_lblFecha);
		
		JLabel lblNewLabel_1 = new JLabel("xD");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.gridwidth = 4;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 2;
		gbc_lblNewLabel_1.gridy = 2;
		panel_2.add(lblNewLabel_1, gbc_lblNewLabel_1);

        	for(int j = 0; j < 100; j++){
                JPanel panelx = new JPanel();
                panelx.add(new JLabel("Hello"));
                panelx.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
                GridBagConstraints gbcx = new GridBagConstraints();
                gbcx.gridwidth = GridBagConstraints.REMAINDER;
                gbcx.weightx = 1;
                gbcx.fill = GridBagConstraints.HORIZONTAL;
               
                
        	}
		
        	list.addListSelectionListener(new ListSelectionListener() {
    			@Override
    			public void valueChanged(ListSelectionEvent e) {
    				lblNewLabel.setText(estados.get(contactos.get(list.getSelectedIndex())));
    			}
    		});
	
	}
	public JFrame getFrmMain() {
		return frmMain;
	}

	public void setFrmMain(JFrame frmMain) {
		this.frmMain = frmMain;
	}

	public void mostrarVentana() {
		frmMain.setVisible(true);
		
	}

}
	
	
