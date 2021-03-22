package vista;

import javax.swing.JPanel;

import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;



import controlador.ControladorAppChat;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Grupo;

import javax.swing.UIManager;
import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.JScrollPane;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFrame;


import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.AbstractListModel;


/*
 * Esta clase define un panel para la creacion de grupos.
 * Lo relevante de este panel es que esta compuesto por dos JList
 * Uno para los contactos disponibles y otro para los contactos agregados al grupo
 * Los elementos de dichos Jlist se pueden mover de un Jlist a otro mediante la seleccion del elemento y la accion de dos botones
 * Este panel se reutiliza para la modificacion de un grupo en el caso de que el usuario sea administrador
 * En este caso el Jlist de los contactos agregados al grupo aparecera con los integrantes de este
 * 
 */

public class PanelCrearGrupo extends JPanel {

	private static final String RECURSOS_VISTA_FLECHA_NEGRA_IZQUIERDA_PNG = "/recursosVista/flechaNegraIzquierda.png";
	private static final String RECURSOS_VISTA_FLECHA_NEGRA_DERECHA_PNG = "/recursosVista/flechaNegraDerecha.png";
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JFrame ventana;
	private JPanel anterior;
	private ContactoIndividual contactoSeleccionado;
	private ContactoIndividual contactoDeseleccionado;
	
	private List<ContactoIndividual> listaContactosDisponibles;
	private List<ContactoIndividual> listaContactosAnadidos;
	
	private JList<String> listAnadidos;
	private JList<String> listDisponibles;
	
	private String[] nombreContactos;
	
	public PanelCrearGrupo(JFrame frame, Grupo grupoModificar) {
		
		ventana=frame;
		anterior = (JPanel) ventana.getContentPane();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{20, 638, -73, 0};
		gridBagLayout.rowHeights = new int[]{10, 244, 10, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 1;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{10, 270, 0, 169, 0, 270, 10, 0};
		gbl_panel.rowHeights = new int[]{32, 229, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JPanel panel_5 = new JPanel();
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.anchor = GridBagConstraints.NORTH;
		gbc_panel_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_5.insets = new Insets(0, 0, 5, 5);
		gbc_panel_5.gridwidth = 5;
		gbc_panel_5.gridx = 1;
		gbc_panel_5.gridy = 0;
		panel.add(panel_5, gbc_panel_5);
		GridBagLayout gbl_panel_5 = new GridBagLayout();
		gbl_panel_5.columnWidths = new int[]{20, 0, 0, 109, 116, 0, 0, 0, 0};
		gbl_panel_5.rowHeights = new int[]{22, 0};
		gbl_panel_5.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel_5.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_5.setLayout(gbl_panel_5);
		
		JLabel label = new JLabel("Nombre del Grupo:");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.WEST;
		gbc_label.insets = new Insets(0, 0, 0, 5);
		gbc_label.gridx = 4;
		gbc_label.gridy = 0;
		panel_5.add(label, gbc_label);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 2;
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 0, 5);
		gbc_textField.anchor = GridBagConstraints.NORTH;
		gbc_textField.gridx = 5;
		gbc_textField.gridy = 0;
		panel_5.add(textField, gbc_textField);
		textField.setColumns(10);
		if (grupoModificar!= null) {
			textField.setText(grupoModificar.getNombre());
		}
			
		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 1;
		panel.add(panel_2, gbc_panel_2);
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Contactos Disponibles", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{170, 0};
		gbl_panel_2.rowHeights = new int[]{148, 0};
		gbl_panel_2.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 0;
		panel_2.add(scrollPane_1, gbc_scrollPane_1);
		
		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.gridwidth = 3;
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.insets = new Insets(0, 0, 5, 5);
		gbc_panel_3.gridx = 2;
		gbc_panel_3.gridy = 1;
		panel.add(panel_3, gbc_panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{20, 0, 20, 0};
		gbl_panel_3.rowHeights = new int[]{20, 0, 0, 0, 20, 0};
		gbl_panel_3.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.gridx = 5;
		gbc_panel_1.gridy = 1;
		panel.add(panel_1, gbc_panel_1);
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Contactos Anadidos", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{170, 0};
		gbl_panel_1.rowHeights = new int[]{148, 0};
		gbl_panel_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panel_1.add(scrollPane, gbc_scrollPane);
		
		if (grupoModificar!=null) {
			listaContactosDisponibles = ControladorAppChat.getUnicaInstancia().obtenerContactos();
			List<ContactoIndividual> listaContactosDisponiblesAux = new LinkedList<ContactoIndividual>(listaContactosDisponibles);
						
			ListIterator<ContactoIndividual> iterator = listaContactosDisponiblesAux.listIterator();
			while(iterator.hasNext()) {
				ContactoIndividual contactoIndividualAux = (ContactoIndividual) iterator.next();
				for (Contacto contactoGrupo : grupoModificar.getParticipantes()) {
					if (contactoGrupo.getNombre().equals(contactoIndividualAux.getNombre())) {
						listaContactosDisponibles.remove(contactoIndividualAux);
					}
				}
			}
		
		}
		else {
			listaContactosDisponibles = ControladorAppChat.getUnicaInstancia().obtenerContactos();
		}
		listDisponibles = generarJList(scrollPane_1,"Disponibles");
		
		if (grupoModificar!=null) {
			
			listaContactosAnadidos = new LinkedList<ContactoIndividual>();
			for (Contacto contactoIndividual : grupoModificar.getParticipantes()) {
				
				listaContactosAnadidos.add((ContactoIndividual) contactoIndividual);
			}
		}
		else {
			listaContactosAnadidos = new LinkedList<ContactoIndividual>();
		}
		
		listAnadidos = generarJList(scrollPane,"Anadidos");
		
		actualizarListEvent();

		JButton button = new JButton("");
		button.setIcon(new ImageIcon(PanelCrearGrupo.class.getResource(RECURSOS_VISTA_FLECHA_NEGRA_IZQUIERDA_PNG)));
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.fill = GridBagConstraints.HORIZONTAL;
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 1;
		gbc_button.gridy = 1;
		panel_3.add(button, gbc_button);
		
		button.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if (contactoSeleccionado!=null && !listaContactosAnadidos.contains(contactoSeleccionado)) {
					listaContactosAnadidos.add(contactoSeleccionado);
					listaContactosDisponibles.remove(contactoSeleccionado);
					listAnadidos = generarJList(scrollPane,"Anadidos");
					listDisponibles = generarJList(scrollPane_1,"Disponibles");
					actualizarListEvent();

				}else JOptionPane.showMessageDialog(ControladorVista.frmMain, "Por favor, selecciona un contacto");

			}
		});
		
		JButton button_1 = new JButton("");
		button_1.setIcon(new ImageIcon(PanelCrearGrupo.class.getResource(RECURSOS_VISTA_FLECHA_NEGRA_DERECHA_PNG)));
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_button_1.insets = new Insets(0, 0, 5, 5);
		gbc_button_1.gridx = 1;
		gbc_button_1.gridy = 3;
		panel_3.add(button_1, gbc_button_1);
		
		
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (contactoDeseleccionado!=null && !listaContactosDisponibles.contains(contactoDeseleccionado)) {
				listaContactosDisponibles.add(contactoDeseleccionado);
				listaContactosAnadidos.remove(contactoDeseleccionado);
				
				listAnadidos = generarJList(scrollPane,"Anadidos");
				listDisponibles = generarJList(scrollPane_1,"Disponibles");
				
				actualizarListEvent();
				
				}else JOptionPane.showMessageDialog(ControladorVista.frmMain, "Por favor, selecciona un contacto");
			
			}
		});
		
			
		JButton btnCrearGrupo = new JButton("Crear Grupo");
		GridBagConstraints gbc_btnCrearGrupo = new GridBagConstraints();
		gbc_btnCrearGrupo.insets = new Insets(0, 0, 0, 5);
		gbc_btnCrearGrupo.gridx = 2;
		gbc_btnCrearGrupo.gridy = 2;
		panel.add(btnCrearGrupo, gbc_btnCrearGrupo);
		
		btnCrearGrupo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String nombreGrupo;
				int numero;
				if(textField.getText() == "") {
					numero = (int) (Math.random() * 10) + 1;
					nombreGrupo = "Nombre de Grupo Asignado: "+ Integer.toString(numero);
				}
				else nombreGrupo = textField.getText();
				
				List<Contacto> aux = new LinkedList<Contacto>(listaContactosAnadidos);
				
				if (grupoModificar!=null) {
					ControladorAppChat.getUnicaInstancia().crearGrupo(grupoModificar,nombreGrupo, ControladorAppChat.getUnicaInstancia().getUsuarioActual().getMovil(), aux);
					JOptionPane.showMessageDialog(ControladorVista.frmMain, "Grupo Modificado Correctamente");
					ControladorVista.ActualizarVentanasImplicanContactos();
					
				}
				else if (ControladorAppChat.getUnicaInstancia().getUsuarioActual().grupoRegistrado(nombreGrupo)!=null && grupoModificar==null) {
					JOptionPane.showMessageDialog(ControladorVista.frmMain, "Error, ya se ha creado un grupo con ese nombre");
				}
				else {
					ControladorAppChat.getUnicaInstancia().crearGrupo(null,nombreGrupo, ControladorAppChat.getUnicaInstancia().getUsuarioActual().getMovil(), aux);
					JOptionPane.showMessageDialog(ControladorVista.frmMain, "Grupo Creado Correctamente");
					ControladorVista.ActualizarVentanasImplicanContactos();
			
				}
			}
		});
		
		JButton btnCancelar = new JButton("Volver");
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancelar.gridx = 3;
		gbc_btnCancelar.gridy = 2;
		panel.add(btnCancelar, gbc_btnCancelar);
		
		btnCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ventana.setContentPane(anterior);
				ventana.revalidate();
				
			}
		});

		

	}
	
	/*
	 * La actualizacion visual de un Jlist porque un elemento se ha movido de un Jlist a otro
	 * Implica la creacion de dos nuevos Jlist y la actualizacion de sus propios listenners
	 */
	
	// PARA CAMBIAR LA LISTA DE CONTACTOS EN LOS DOS JLIST DEPENDIENDO DEL CASO
	private JList<String> generarJList(JScrollPane scrollPane_1,String opcion) {
		
		List<ContactoIndividual> contactosAux = null;
		
		if (opcion.equals("Disponibles")) {
			contactosAux = listaContactosDisponibles;
		}
		else if (opcion.equals("Anadidos")) {
			
			contactosAux = listaContactosAnadidos;
		}
		
		
		//PARA CREAR EL ARRAY PARA EL MODELO DEL JLIST
		nombreContactos = new String[contactosAux.size()];
		int i = 0;
		synchronized (nombreContactos) {
			for (Contacto contacto : contactosAux) {
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
				
		scrollPane_1.setViewportView(list);
		return list;
		
	}


	private void actualizarListEvent() {
		
			listDisponibles.addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					contactoSeleccionado = listaContactosDisponibles.get(listDisponibles.getSelectedIndex());
				}
			});
			
			listAnadidos.addListSelectionListener(new ListSelectionListener() {
				
				@Override
				public void valueChanged(ListSelectionEvent e) {
					contactoDeseleccionado = listaContactosAnadidos.get(listAnadidos.getSelectedIndex());
				}
			});
	}
	
}
