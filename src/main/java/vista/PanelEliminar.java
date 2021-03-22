package vista;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;

import javax.swing.border.TitledBorder;


import controlador.ControladorAppChat;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Grupo;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.AbstractListModel;

/*
 * Esta clase define un panel con el objetivo de que el usuario pueda eliminar tanto los mensajes
 * como directamente el contacto seleccionado , ya sea un grupo o un contacto individual.
 * En la especificacion de la practica se planteaba un menu con dos items desplegables. Visto que los dos items 
 * tenian una funcionalidad similar, se decidio juntar dicha funcionalidad en un unico panel
 * 
 */


public class PanelEliminar extends JPanel {


	private static final long serialVersionUID = 1L;
	private JFrame ventana;
	private List<ContactoIndividual> contactos;
	private List<Grupo> grupos;
	private JList<String> list;
	private JList<String> list_1;

	public PanelEliminar(JFrame frame) {
		

		ventana=frame;
		setLayout(new BorderLayout(0, 0));
		JPanel panel = new JPanel();
		add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{20, 0, 0, 0, 0, 0, 0, 0, 20, 0};
		gbl_panel.rowHeights = new int[]{20, 0, 0, 0, 20, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Contactos Individuales", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridwidth = 5;
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 1;
		panel.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panel_1.add(scrollPane, gbc_scrollPane);
		
		
		//PARA OBTENER EL NOMBRE DEL CONTACTO ASOCIADO AL ESTADO
		contactos = ControladorAppChat.getUnicaInstancia().getUsuarioActual().obtenerContactos();
		
		//PARA CREAR EL ARRAY PARA EL MODELO DEL JLIST
		String[] nombreContactos = new String[contactos.size()];
		int i = 0;
		synchronized (nombreContactos) {
			for (Contacto contacto : contactos) {
				nombreContactos[i] = contacto.getNombre();
				i++;
			}
		}
		
		list = new JList<String>();
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
		scrollPane.setViewportView(list);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Acciones Contactos Individuales", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2.gridx = 7;
		gbc_panel_2.gridy = 1;
		panel.add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{20, 0, 10, 0, 20, 0};
		gbl_panel_2.rowHeights = new int[]{20, 0, 20, 0};
		gbl_panel_2.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JButton btnEliminarTodosMensajes = new JButton("Eliminar Todos Mensajes");
		GridBagConstraints gbc_btnEliminarTodosMensajes = new GridBagConstraints();
		gbc_btnEliminarTodosMensajes.insets = new Insets(0, 0, 5, 5);
		gbc_btnEliminarTodosMensajes.gridx = 1;
		gbc_btnEliminarTodosMensajes.gridy = 1;
		panel_2.add(btnEliminarTodosMensajes, gbc_btnEliminarTodosMensajes);
		btnEliminarTodosMensajes.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(list.getSelectedValue() != null) {
					if(!contactos.get(list.getSelectedIndex()).getMensajes().isEmpty()){
						if(ControladorAppChat.getUnicaInstancia().eliminarMensajesContacto(contactos.get(list.getSelectedIndex()))) {
							 JOptionPane.showMessageDialog(ControladorVista.frmMain, "Mensajes Eliminados Correctamente");
						}
					}
				}

			}

		});
		
		
		JButton btnEliminarContacto = new JButton("Eliminar Contacto");
		GridBagConstraints gbc_btnEliminarContacto = new GridBagConstraints();
		gbc_btnEliminarContacto.anchor = GridBagConstraints.WEST;
		gbc_btnEliminarContacto.insets = new Insets(0, 0, 5, 5);
		gbc_btnEliminarContacto.gridx = 3;
		gbc_btnEliminarContacto.gridy = 1;
		panel_2.add(btnEliminarContacto, gbc_btnEliminarContacto);
		btnEliminarContacto.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(list.getSelectedValue() != null) {
					
					ControladorAppChat.getUnicaInstancia().eliminarContacto(contactos.get(list.getSelectedIndex()),ControladorAppChat.getUnicaInstancia().getUsuarioActual() );
					JOptionPane.showMessageDialog(ControladorVista.frmMain, "Contacto Eliminado Correctamente");
					ControladorVista.ActualizarEliminar();

				}

			}

		});
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Grupos", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.gridwidth = 5;
		gbc_panel_3.insets = new Insets(0, 0, 5, 5);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 1;
		gbc_panel_3.gridy = 2;
		panel.add(panel_3, gbc_panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{0, 0};
		gbl_panel_3.rowHeights = new int[]{0, 0, 0};
		gbl_panel_3.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridheight = 2;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 0;
		panel_3.add(scrollPane_1, gbc_scrollPane_1);
		
		
		
		//PARA OBTENER EL NOMBRE DEL CONTACTO ASOCIADO AL ESTADO
		grupos = ControladorAppChat.getUnicaInstancia().getUsuarioActual().obtenerGrupos();
		
		//PARA CREAR EL ARRAY PARA EL MODELO DEL JLIST
		String[] nombreGrupos = new String[grupos.size()];
		int j = 0;
		synchronized (nombreContactos) {
			for (Contacto contacto : grupos) {
				nombreGrupos[j] = contacto.getNombre();
				j++;
			}
		}
		
		
		list_1 = new JList<String>();
		list_1.setModel(new AbstractListModel<String>() {

			private static final long serialVersionUID = 1L;
			String[] values = nombreGrupos;

			public int getSize() {
				return values.length;
			}

			public String getElementAt(int index) {
				return values[index];
			}
		});

		list_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(list_1);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Acciones Grupos", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.insets = new Insets(0, 0, 5, 5);
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 7;
		gbc_panel_4.gridy = 2;
		panel.add(panel_4, gbc_panel_4);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[]{0, 0, 10, 0, 20, 0};
		gbl_panel_4.rowHeights = new int[]{20, 0, 20, 0};
		gbl_panel_4.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_4.rowWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		panel_4.setLayout(gbl_panel_4);
		
		JButton buttonEliminarMensajesGrupo = new JButton("Eliminar Mensajes del Grupo");
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 1;
		gbc_button.gridy = 1;
		panel_4.add(buttonEliminarMensajesGrupo, gbc_button);
		
		buttonEliminarMensajesGrupo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(list_1.getSelectedValue() != null) {
					if(!contactos.get(list_1.getSelectedIndex()).getMensajes().isEmpty()){
						
						if(grupos.get(list_1.getSelectedIndex()).puedeSerEliminado(ControladorAppChat.getUnicaInstancia().getUsuarioActual())){
			
							if(ControladorAppChat.getUnicaInstancia().eliminarMensajesGrupo(grupos.get(list_1.getSelectedIndex()))) {
								JOptionPane.showMessageDialog(ControladorVista.frmMain, "Mensajes de Grupo eliminados Correctamente");
								
							}
				
						}
						else {
							JOptionPane.showMessageDialog(ControladorVista.frmMain, "NO TIENE PERMISOS DE ADMINISTRADOR: Los mensajes del grupo seleccionado no se han podido borrar");
						}
						
						
					}
				}
				
				
			}
		});
		
		JButton buttonEliminarGrupo = new JButton("Eliminar Grupo");
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.anchor = GridBagConstraints.WEST;
		gbc_button_1.insets = new Insets(0, 0, 5, 5);
		gbc_button_1.gridx = 3;
		gbc_button_1.gridy = 1;
		panel_4.add(buttonEliminarGrupo, gbc_button_1);
		buttonEliminarGrupo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					if(list_1.getSelectedValue() != null) {
						
						if(grupos.get(list_1.getSelectedIndex()).puedeSerEliminado(ControladorAppChat.getUnicaInstancia().getUsuarioActual())){
							ControladorAppChat.getUnicaInstancia().eliminarGrupo(grupos.get(list_1.getSelectedIndex()));
							JOptionPane.showMessageDialog(ControladorVista.frmMain, "Grupo Eliminado Correctamente");
							ControladorVista.ActualizarEliminar();
						}
						else {
							JOptionPane.showMessageDialog(ControladorVista.frmMain, "NO TIENE PERMISOS DE ADMINISTRADOR: Grupo no se ha podido eliminar");
						}
						
				}
				
			}
		});
		
		JButton btnVolver = new JButton("Volver");
		GridBagConstraints gbc_btnVolver = new GridBagConstraints();
		gbc_btnVolver.gridwidth = 7;
		gbc_btnVolver.insets = new Insets(0, 0, 5, 5);
		gbc_btnVolver.gridx = 1;
		gbc_btnVolver.gridy = 3;
		panel.add(btnVolver, gbc_btnVolver);
		
		btnVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ventana.setContentPane(ControladorVista.pnlChat);	
				ventana.revalidate();
				
			}
		});
		
	}

}
