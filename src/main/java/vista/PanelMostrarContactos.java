package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Image;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JFileChooser;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;


import controlador.ControladorAppChat;
import modelo.CatalogoUsuarios;
import modelo.ContactoIndividual;

import javax.swing.border.TitledBorder;


/*
 * Esta clase define un panel para mostrar todos los contactos agregados del usuario
 * El principal elemento del panel es una tabla que contiene toda la informacion de los contactos 
 * e incluso su imagen de perfil ( aunque redimensionada).
 * Si el usuario es premium , tiene la posibilidad de exportar un pdf con toda informacion de sus contactos
 * en la localizacion que indique mediante un Chooser que solo admite directorios.
 */

public class PanelMostrarContactos extends JPanel {
	
	private static final long serialVersionUID = 1L;
	//JDialogs
	private File Directorio;
	private JFileChooser chooser;
	private JFrame ventana;
	private JTable table;
	private List<ContactoIndividual> modelo;
	
	public PanelMostrarContactos(JFrame frame) {
		setLayout(new BorderLayout(0, 0));
		ventana=frame;
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{10, 0, 10, 0};
		gbl_panel.rowHeights = new int[]{10, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Lista de Contactos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.gridheight = 6;
		gbc_panel_2.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 1;
		panel.add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{0, 0};
		gbl_panel_2.rowHeights = new int[]{0, 0};
		gbl_panel_2.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panel_2.add(scrollPane, gbc_scrollPane);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane.setViewportView(scrollPane_1);
		
	
		table = new JTable();
		modelo = ControladorAppChat.getUnicaInstancia().obtenerContactos();
		table.setModel(new ContactosTabla(modelo));
		chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		scrollPane_1.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridheight = 2;
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 7;
		panel.add(panel_1, gbc_panel_1);
		
		JButton button_1 = new JButton("generarPDF");
		button_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (ControladorAppChat.getUnicaInstancia().getUsuarioActual().isPremium()) {
					//PanelPrincipal.dialogorutaPDF.setVisible(true);
					chooser.showOpenDialog(ventana);
					Directorio = chooser.getSelectedFile();
					ControladorAppChat.getUnicaInstancia().crearPdf(Directorio.toString());
				}
				else {
					ControladorVista.dialogoConvertirsePremium.setVisible(true);
				}
					
				
			}
		});
		
		panel_1.add(button_1);
		JButton  btnVolver= new JButton("Volver");
		 btnVolver.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventana.setContentPane(ControladorVista.pnlChat);
				ventana.revalidate();
			}
		});
		
		panel_1.add(btnVolver);
		ventana=frame;
	}
	
	public ContactoIndividual getContacto(){
		int fila = table.getSelectedRow();
		if (fila >= 0) {
			return modelo.get(fila);
		}
		return null;
	}
	
	
	
	private class ContactosTabla extends AbstractTableModel {
		
		private static final long serialVersionUID = 1L;
		
		private List<ContactoIndividual> contactos;
		private String[] columnNames = {"Nombre","Imagen","Teléfono","Grupos Compartidos"};
		
		public ContactosTabla(List<ContactoIndividual> contactos){
			this.contactos = contactos;
		}
		
		public String getColumnName(int column) {
			return columnNames[column];
		}
		
		public int getRowCount() {
			return contactos.size();
		}
		public int getColumnCount() {
			return 4;
		}
		
		public Class<?> getColumnClass(int column) {
	        switch(column) {
	            case 0: return String.class;
	            case 1: return ImageIcon.class;
	            case 2: return Integer.class;
	            case 3: return String.class;
	            default: return Object.class;
	     
	        }
	    }
		
		public Object getValueAt(int row, int col) {
			String valor = "";
			//ImageIcon fotoPerfil = null;
			ContactoIndividual contac = contactos.get(row);
			
			
			switch (col) {
				case 0 : valor = contac.getNombre(); 
				break;
				
				case 1 : 
						//OBTENEMOS LA IMAGEN DEL USUARIO
						ImageIcon imagenUsuarioIcon = new ImageIcon(CatalogoUsuarios.getUnicaInstancia().getUsuario(contac.getTelefono()).getImagen());
						//LA REESCALAMOS
						Image imagenUsuario = imagenUsuarioIcon.getImage();
						Image imagenReescalada = imagenUsuario.getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH );
						//MOSTRAMOS LA IMAGEN REESCALADA
						//ImagenPerfil.setIcon();
						return new ImageIcon(imagenReescalada);
						//ImageIcon.class = new ImageIcon(imagenReescalada);
						
						
						//valor = CatalogoUsuarios.getUnicaInstancia().getUsuario(contac.getTelefono()).getImagen();
				
				case 2 : valor = Integer.toString(contac.getTelefono());
				break;
				
				case 3 : //valor = CatalogoUsuarios.getUnicaInstancia().getUsuario(contac.getTelefono()).getEmail();
					valor = ControladorAppChat.getUnicaInstancia().obtenerGruposContacto(contac).toString();
				break;
		}
			
			return valor;
		}
		
		
		public void setValueAt(Object value, int row, int col) { }
		
	}
	

}


