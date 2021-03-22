package vista;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;


import javax.swing.table.AbstractTableModel;




import com.toedter.calendar.JDateChooser;

import controlador.ControladorAppChat;
import modelo.CatalogoUsuarios;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Mensaje;


import javax.swing.BoxLayout;

import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JButton;


import javax.swing.AbstractListModel;


import java.awt.Color;

import javax.swing.UIManager;



/*
 * Esta clase define un panel de busqueda
 * La busqueda solo se realiza entre los mensajes relacionados con el usuario actual y sus diferentes contactos
 * Se pueden aplicar diversos filtros, fecha, palabras especificas en combinacion con restricciones segun
 * los mensajes enviados por el usuario, por sus contactos indivuales o los realizados mediante el grupo.
 * Dado el concepto de lista de difusion que tiene el grupo y donde la instancia de grupo no es compartida por otros usuarios
 * en este solo se guardan los mensajes que haga el usuario, por tanto la busqueda de mensajes por contactos especificos integrantes del grupo
 * no tiene sentido ya que en primer al llegar los mensajes por privado del otro lado del canal de comunicacion, no hay mensajes
 * de esos contactos en el grupo, y por otro lado los mensajes realizados por el canal de difusion le llegan a todos los contactos por
 * igual. De todas se ha implementado la posibilidad de actualizar un panel de contactos indivuales
 * en funcion de los integrantes del grupo, aunque esta opcion este desahabilitada.
 * 
 */
public class PanelBusquedaMensaje extends JPanel {

	
	//protected static final Predicate<Contacto> ContactoIndividual = null;
	//protected static final Predicate<Contacto> Grupo = null;


	private static final long serialVersionUID = 1L;

	private JTable table;
	private JFrame ventana;
	@SuppressWarnings("unused")
	private JPanel jpanelAnterior;
	private JTextField textPalabra;
	private List<Mensaje> modelo;
	private List<Contacto> contactos;
	private JTable table_1;
	private String palabraClave;
	private modelo.Grupo grupoSeleccionado;
	private ContactoIndividual contactoSeleccionado;
	private List<ContactoIndividual> contactosUsuario;
	private List<modelo.Grupo> contactosGrupos;
	private List<Contacto> contactosUsuarioEnGrupo;
	private String[] nombreContactos;
	private JList<String> list1;
	private JList<String> list2;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;
	private boolean contactoUsuario;
	

	public PanelBusquedaMensaje(JFrame frame) {
		
		//GUARDA LA VENTANA ANTERIOR
		ventana=frame;
		jpanelAnterior = (JPanel) ventana.getContentPane();
		
		//CONFIGURACION  GRAFICA SWING
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0};
		gridBagLayout.rowHeights = new int[]{20, 0, 0, 0, 0, 0, 20, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Criterios de B\u00FAsqueda", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.gridwidth = 10;
		gbc_panel_3.insets = new Insets(0, 0, 5, 5);
		gbc_panel_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_3.gridx = 1;
		gbc_panel_3.gridy = 1;
		add(panel_3, gbc_panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		panel_3.add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{10, 48, 116, 10, 0};
		gbl_panel.rowHeights = new int[]{22, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Grupos", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.insets = new Insets(0, 0, 5, 5);
		gbc_panel_5.fill = GridBagConstraints.BOTH;
		gbc_panel_5.gridx = 0;
		gbc_panel_5.gridy = 0;
		panel.add(panel_5, gbc_panel_5);
		GridBagLayout gbl_panel_5 = new GridBagLayout();
		gbl_panel_5.columnWidths = new int[]{170, 0};
		gbl_panel_5.rowHeights = new int[]{148, 0};
		gbl_panel_5.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_5.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_5.setLayout(gbl_panel_5);
		
		
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.gridx = 0;
		gbc_scrollPane_2.gridy = 0;
		
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Contactos Disponibles", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.insets = new Insets(0, 0, 5, 5);
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 1;
		gbc_panel_4.gridy = 0;
		panel.add(panel_4, gbc_panel_4);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[]{170, 0};
		gbl_panel_4.rowHeights = new int[]{148, 0};
		gbl_panel_4.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_4.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_4.setLayout(gbl_panel_4);
		
		
		scrollPane_1 = new JScrollPane();
		scrollPane_2 = new JScrollPane();
		
		
		panel_5.add(scrollPane_2, gbc_scrollPane_2);
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 0;
		panel_4.add(scrollPane_1, gbc_scrollPane_1);
		
		JPanel panel_6 = new JPanel();
		GridBagConstraints gbc_panel_6 = new GridBagConstraints();
		gbc_panel_6.insets = new Insets(0, 0, 5, 5);
		gbc_panel_6.fill = GridBagConstraints.BOTH;
		gbc_panel_6.gridx = 2;
		gbc_panel_6.gridy = 0;
		panel.add(panel_6, gbc_panel_6);
		GridBagLayout gbl_panel_6 = new GridBagLayout();
		gbl_panel_6.columnWidths = new int[]{0, 0, 140, 0};
		gbl_panel_6.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_6.columnWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel_6.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel_6.setLayout(gbl_panel_6);
		
		JLabel lblLimiteInferior = new JLabel("Limite Inferior");
		GridBagConstraints gbc_lblLimiteInferior = new GridBagConstraints();
		gbc_lblLimiteInferior.insets = new Insets(0, 0, 5, 5);
		gbc_lblLimiteInferior.gridx = 1;
		gbc_lblLimiteInferior.gridy = 0;
		panel_6.add(lblLimiteInferior, gbc_lblLimiteInferior);
		
		JLabel lblLimiteSuperior = new JLabel("Limite Superior");
		GridBagConstraints gbc_lblLimiteSuperior = new GridBagConstraints();
		gbc_lblLimiteSuperior.insets = new Insets(0, 0, 5, 0);
		gbc_lblLimiteSuperior.gridx = 2;
		gbc_lblLimiteSuperior.gridy = 0;
		panel_6.add(lblLimiteSuperior, gbc_lblLimiteSuperior);
		
		
		JLabel label_2 = new JLabel("Intervalo de Fechas:");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.anchor = GridBagConstraints.EAST;
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 0;
		gbc_label_2.gridy = 1;
		panel_6.add(label_2, gbc_label_2);
		
		JDateChooser dateChooser = new JDateChooser();
		GridBagConstraints gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser.gridx = 1;
		gbc_dateChooser.gridy = 1;
		panel_6.add(dateChooser, gbc_dateChooser);
		dateChooser.setDateFormatString("dd/MM/yyyy");
		
		JDateChooser dateChooser_1 = new JDateChooser();
		GridBagConstraints gbc_dateChooser_1 = new GridBagConstraints();
		gbc_dateChooser_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateChooser_1.insets = new Insets(0, 0, 5, 0);
		gbc_dateChooser_1.gridx = 2;
		gbc_dateChooser_1.gridy = 1;
		panel_6.add(dateChooser_1, gbc_dateChooser_1);
		dateChooser_1.setDateFormatString("dd/MM/yyyy");
		
		JCheckBox checkBoxGrupos = new JCheckBox("Grupos");
		GridBagConstraints gbc_checkBoxGrupos = new GridBagConstraints();
		gbc_checkBoxGrupos.fill = GridBagConstraints.HORIZONTAL;
		gbc_checkBoxGrupos.insets = new Insets(0, 0, 5, 5);
		gbc_checkBoxGrupos.gridx = 1;
		gbc_checkBoxGrupos.gridy = 2;
		panel_6.add(checkBoxGrupos, gbc_checkBoxGrupos);
		
		JCheckBox checkBoxContactosIndividuales = new JCheckBox("Contactos Individuales");
		GridBagConstraints gbc_checkBoxContactosIndividuales = new GridBagConstraints();
		gbc_checkBoxContactosIndividuales.fill = GridBagConstraints.HORIZONTAL;
		gbc_checkBoxContactosIndividuales.insets = new Insets(0, 0, 5, 0);
		gbc_checkBoxContactosIndividuales.gridx = 2;
		gbc_checkBoxContactosIndividuales.gridy = 2;
		panel_6.add(checkBoxContactosIndividuales, gbc_checkBoxContactosIndividuales);
		
		
		JCheckBox chckbxUsuario = new JCheckBox("Usuario");
		chckbxUsuario.setSelected(true);
		GridBagConstraints gbc_chckbxUsuario = new GridBagConstraints();
		gbc_chckbxUsuario.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxUsuario.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxUsuario.gridx = 2;
		gbc_chckbxUsuario.gridy = 3;
		panel_6.add(chckbxUsuario, gbc_chckbxUsuario);
		
		JLabel lblPalabraClave = new JLabel("Palabra Clave:");
		GridBagConstraints gbc_lblPalabraClave = new GridBagConstraints();
		gbc_lblPalabraClave.anchor = GridBagConstraints.EAST;
		gbc_lblPalabraClave.insets = new Insets(0, 0, 5, 5);
		gbc_lblPalabraClave.gridx = 0;
		gbc_lblPalabraClave.gridy = 4;
		panel_6.add(lblPalabraClave, gbc_lblPalabraClave);
		
		textPalabra = new JTextField();
		GridBagConstraints gbc_textPalabra = new GridBagConstraints();
		gbc_textPalabra.gridwidth = 2;
		gbc_textPalabra.fill = GridBagConstraints.HORIZONTAL;
		gbc_textPalabra.insets = new Insets(0, 0, 5, 0);
		gbc_textPalabra.gridx = 1;
		gbc_textPalabra.gridy = 4;
		panel_6.add(textPalabra, gbc_textPalabra);
		textPalabra.setColumns(10);
		
		JPanel panel_7 = new JPanel();
		GridBagConstraints gbc_panel_7 = new GridBagConstraints();
		gbc_panel_7.gridwidth = 3;
		gbc_panel_7.fill = GridBagConstraints.BOTH;
		gbc_panel_7.gridx = 0;
		gbc_panel_7.gridy = 6;
		panel_6.add(panel_7, gbc_panel_7);
		GridBagLayout gbl_panel_7 = new GridBagLayout();
		gbl_panel_7.columnWidths = new int[]{82, 113, 15, 0, 0, 0};
		gbl_panel_7.rowHeights = new int[]{10, 25, 0};
		gbl_panel_7.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_7.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		panel_7.setLayout(gbl_panel_7);
		
		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.gridheight = 3;
		gbc_panel_2.gridwidth = 10;
		gbc_panel_2.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 3;
		add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{0, 0};
		gbl_panel_2.rowHeights = new int[]{0, 0};
		gbl_panel_2.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		table_1 = new JTable();
		
		JButton btnLimpiarFiltros = new JButton("Limpiar Filtros");
		GridBagConstraints gbc_btnLimpiarFiltros = new GridBagConstraints();
		gbc_btnLimpiarFiltros.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnLimpiarFiltros.insets = new Insets(0, 0, 0, 5);
		gbc_btnLimpiarFiltros.gridx = 1;
		gbc_btnLimpiarFiltros.gridy = 1;
		panel_7.add(btnLimpiarFiltros, gbc_btnLimpiarFiltros);

		

		//INICIALMENTE SE MUESTRAN TODOS LOS MENSAJES
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panel_2.add(scrollPane, gbc_scrollPane);
		
		contactos = ControladorAppChat.getUnicaInstancia().getUsuarioActual().getConversaciones();
		modelo = new LinkedList<Mensaje>();
		
		for (Contacto contacto : contactos) {
			for (Mensaje mensaje : contacto.getMensajes()) {
				modelo.add(mensaje);
			}
		}

		// Se establece como modelo de la tabla todos los mensajes relacionados con el usuario
		table_1.setModel(new ContactosTabla(modelo));
		table_1.getColumnModel().getColumn(0).setPreferredWidth(272);
		table_1.getColumnModel().getColumn(1).setPreferredWidth(116);
		table_1.getColumnModel().getColumn(2).setPreferredWidth(208);
		scrollPane.setViewportView(table_1);
		
		
		//CHECK BOX
		//AGRUPACION DE BOTONES PARA SOLO UNA SELECCION -->Poco intuitivo
		/*
		ButtonGroup tipoBusqueda= new ButtonGroup();
		tipoBusqueda.add(checkBoxGrupos);
		tipoBusqueda.add(checkBoxContactosIndividuales);*/
		//tipoBusqueda.add(chckbxUsuario);

		// BOXGRUPOS
		// Las opciones de busqueda de contacto individual, por grupos y por usuario se 
		checkBoxGrupos.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				if ( checkBoxContactosIndividuales.isSelected()) {
					checkBoxContactosIndividuales.setSelected(false);
				}
				
				if(chckbxUsuario.isSelected()) {
					chckbxUsuario.setSelected(false);
				}
				
				//Jlist2 Activo
				list2.setEnabled(true);
				
				if (contactosGrupos.size() != 0) {
					list2.setSelectedIndex(0);
					grupoSeleccionado = contactosGrupos.get(0);
				}
				list1.setEnabled(false);
				/*
				// Generar JList en base al grupo seleccionado
				list1 = generarJList(scrollPane_1,"modo");
				actualizarListEvent("contactoIndividual");
				
				if ( contactosUsuario.size() != 0 ) {
					list1.setSelectedIndex(0);
					contactoSeleccionado = contactosUsuario.get(0);
				}
				
				*/
			}
		});
		
		//BOX INDIVIDUAL
		checkBoxContactosIndividuales.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				if ( checkBoxGrupos.isSelected()) {
					checkBoxGrupos.setSelected(false);
				}
				
				if(chckbxUsuario.isSelected()) {
					chckbxUsuario.setSelected(false);
				}
				
				
				list1 = generarJList(scrollPane_1,"contactoIndividual");
				actualizarListEvent("contactoIndividual");
				
				if ( contactosUsuario.size() != 0 ) {
					list1.setSelectedIndex(0);
					contactoSeleccionado = contactosUsuario.get(0);
				}
	
				list2.setEnabled(false);
				grupoSeleccionado = null;
				
			}
		});
		
		
		//BOX USUARIO
		chckbxUsuario.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if ( checkBoxGrupos.isSelected()) {
					checkBoxGrupos.setSelected(false);
				}
				
				list1.setEnabled(false);
				list2.setEnabled(false);
				
				if(checkBoxContactosIndividuales.isSelected()) {
					checkBoxContactosIndividuales.setSelected(false);
				}
				
				contactoUsuario = true;
			}
		});
		
		
		// JLIST
		//SELECCION DE CONTACTO INDIVIDUAL
		
		list1 = generarJList(scrollPane_1,"contactoIndividual");
		actualizarListEvent("contactoIndividual");
		//EVENTLISTENNER
		
		if ( contactosUsuario.size() != 0 ) {
			list1.setSelectedIndex(0);
			contactoSeleccionado = contactosUsuario.get(0);
		}
		
		
		//SELECCION DE GRUPO
		list2 = generarJList(scrollPane_2,"Grupo");
		actualizarListEvent("Grupo");
		
		
		if (contactosGrupos.size() != 0) {
			list2.setSelectedIndex(0);
		}
		
		list2.setEnabled(false);
		
		
		// FUNCIONALIDAD DE LIMPIAR FILTROS
		btnLimpiarFiltros.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				textPalabra.setText("");
				
				dateChooser.setDate(null);
				dateChooser_1.setDate(null);
				
				list1.setEnabled(false);
				list2.setEnabled(false);
				
				checkBoxGrupos.setSelected(false);
				checkBoxContactosIndividuales.setSelected(false);
				chckbxUsuario.setSelected(true);
				
				contactoSeleccionado = null;
				grupoSeleccionado = null;
				contactoUsuario = false;
				
				/*list1.setSelectedIndex(contactosUsuario.size()+1);
				list2.setSelectedIndex(contactosGrupos.size()+1);
				*/
				
				contactos = ControladorAppChat.getUnicaInstancia().getUsuarioActual().getConversaciones();
				modelo = new LinkedList<Mensaje>();
				for (Contacto contacto : contactos) {
					for (Mensaje mensaje : contacto.getMensajes()) {
						modelo.add(mensaje);
					}
				}
				
				table_1.setModel(new ContactosTabla(modelo));
			}
		});
		
		JButton btnFiltrarMensajes = new JButton("Filtrar Mensajes");
		GridBagConstraints gbc_btnFiltrarMensajes = new GridBagConstraints();
		gbc_btnFiltrarMensajes.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnFiltrarMensajes.gridx = 4;
		gbc_btnFiltrarMensajes.gridy = 1;
		panel_7.add(btnFiltrarMensajes, gbc_btnFiltrarMensajes);
		
		// FILTRAR MENSAJES SEGUN LOS FILTROS SELECCIONADOS
		btnFiltrarMensajes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				palabraClave = textPalabra.getText();
			
				Date dateInferior = (Date) dateChooser.getDate();
				Date dateSuperior = (Date) dateChooser_1.getDate();
				List<Predicate<Mensaje>> listaPrMensaje = new LinkedList<Predicate<Mensaje>>();
				Predicate<Contacto> prContactos = null;
			
				// El metodo equals compara hasta los segundos, dando como resultado false donde no deberia, por ello se tiene que indicar la comparacion solo hasta los dias
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			
		
				// FECHAS
				if( dateInferior != null && dateSuperior != null  ) {
					
					if ( !dateInferior.after(dateSuperior) && !dateSuperior.before(dateInferior) ) {
						listaPrMensaje.add(d->(!dateInferior.after(d.getHora()) && !dateSuperior.before(d.getHora()) ) 
								||  ( sdf.format(dateInferior).equals(sdf.format(d.getHora())) && sdf.format(dateSuperior).equals(sdf.format(d.getHora())) ) ) ;
					}
					else {
						JOptionPane.showMessageDialog(ControladorVista.frmMain, "Limite de Fechas Cronologicamente Incorrectas");
						dateChooser.setDate(null);
						dateChooser_1.setDate(null);
					}
					
				}
				
				//PALABRA CLAVE
				if (palabraClave != null) {
					listaPrMensaje.add( m -> m.getTexto().contains(palabraClave));
				}
				
				// BUSQUEDA EN UN GRUPO, POR CONTACTO INDIVIDUAL O POR EL PROPIO USUARIO 
				/*La busqueda por contacto individual dentro de un grupo no es compatible con el concepto de lista de difusion*/
				//if(grupoSeleccionado != null && contactoSeleccionado != null  ) {
				
				if(grupoSeleccionado != null ) {
					//listaPrMensaje.add( m -> m.getTelefono() == contactoSeleccionado.getTelefono() && (grupoSeleccionado.getMensajes().contains(m)));	
					listaPrMensaje.add( m -> grupoSeleccionado.getMensajes().contains(m));
					//System.out.println("Busqueda por contacto individual dentro de un grupo");
				}
				else if (contactoSeleccionado != null) {
					listaPrMensaje.add( m -> m.getTelefono() == contactoSeleccionado.getTelefono());
				}
				
				if (contactoUsuario && (chckbxUsuario.isSelected())) {
					listaPrMensaje.add( m -> m.getTelefono() == ControladorAppChat.getUnicaInstancia().getUsuarioActual().getMovil());
					//Busqueda por usuario
				}
				

				//Solo grupo --> todos los mensajes del grupo seleccionado
				// Grupo + Individual --> Mensajes de un contacto concreto en un grupo concreto
				// Individual --> mensajes con un conctacto individual concreto
				
				
				if ( checkBoxGrupos.isSelected()) {
					prContactos = c-> c instanceof modelo.Grupo;
				}
				
				else if ( checkBoxContactosIndividuales.isSelected()) {
					prContactos = c-> c instanceof modelo.ContactoIndividual;
				}
				
				else {
					prContactos = c-> c instanceof Contacto;
				}
				
				modelo = ControladorAppChat.getUnicaInstancia().getUsuarioActual().buscarMensajes( prContactos, listaPrMensaje);
				table_1.setModel(new ContactosTabla(modelo));
			}
			
		});
		

	}



	// PARA CAMBIAR LA LISTA DE CONTACTOS EN LOS DOS JLIST DEPENDIENDO DEL CASO
	private JList<String> generarJList(JScrollPane scrollPane_1, String opcion) {
		
		JList<String> list = new JList<String>();
		
		if (opcion.equals("Grupo")) {
			contactosGrupos = ControladorAppChat.getUnicaInstancia().getUsuarioActual().obtenerGrupos() ;
			//PARA CREAR EL ARRAY PARA EL MODELO DEL JLIST
			nombreContactos = new String[contactosGrupos.size()];
			int i = 0;
			synchronized (nombreContactos) {
				for (Contacto contacto : contactosGrupos) {
					nombreContactos[i] = contacto.getNombre();
					i++;
				}
			}

		}
		else if (opcion.equals("contactoIndividual")) {
			contactosUsuario = ControladorAppChat.getUnicaInstancia().getUsuarioActual().obtenerContactos() ;
			//PARA CREAR EL ARRAY PARA EL MODELO DEL JLIST
			nombreContactos = new String[contactosUsuario.size()];
			int i = 0;
			synchronized (nombreContactos) {
				for (Contacto contacto : contactosUsuario) {
					nombreContactos[i] = contacto.getNombre();
					i++;
				}
			}
		}
		
		else if (opcion.equals("modo") && grupoSeleccionado != null) {
			//contactosUsuarioEnGrupo = ControladorAppChat.getUnicaInstancia().obtenerContactosGrupo(grupoSeleccionado) ;
			contactosUsuarioEnGrupo = grupoSeleccionado.getParticipantes();
			//PARA CREAR EL ARRAY PARA EL MODELO DEL JLIST
			nombreContactos = new String[contactosUsuarioEnGrupo.size()];
			int i = 0;
			synchronized (nombreContactos) {
				for (Contacto contacto : contactosUsuarioEnGrupo) {
					nombreContactos[i] = contacto.getNombre();
					i++;
				}
			}
			
		}

	
		//ESTABLECE EL MODELO INDICADO EN EL SCROLLPANE INDICADO	
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
	
	// A su vez si se actualiza el jlist se deben actualizar sus listenners
	private void actualizarListEvent(String opcion) {
		
		switch (opcion) {
		case "contactoIndividual":
			
			list1.addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					contactoSeleccionado =  contactosUsuario.get(list1.getSelectedIndex());
				}
			});
			
		break;
		
		case "Grupo":
			
			list2.addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					grupoSeleccionado = contactosGrupos.get(list2.getSelectedIndex());
					list1 = generarJList(scrollPane_1,"modo");
					actualizarListEvent("contactoIndividual");
					
				}
			});
		break;
	
		
		default:
		break;
		}
		
	
}

	
	// GENERAR LA TABLA DE MENSAJES
	
	public Mensaje getContacto(){
		int fila = table.getSelectedRow();
		if (fila >= 0) {
			return modelo.get(fila);
		}
		
		return null;
	}
	
	

	private class ContactosTabla extends AbstractTableModel {
		
		private static final long serialVersionUID = 1L;
		
		private List<Mensaje> mensajes;
		private String[] columnNames = {"Mensaje de Texto", "Hora de Envio", "Emisor"};

		public ContactosTabla(List<Mensaje> mensaje){
			this.mensajes = mensaje;
		}
		
		public String getColumnName(int column) {
			return columnNames[column];
		}
		
		public int getRowCount() {
			return mensajes.size();
		}
		public int getColumnCount() {
			return 3;
		}
		
		public Class<?> getColumnClass(int column) {
	        switch(column) {
	            case 0: return String.class;
	            case 1: return Date.class;
	            case 3: return String.class;
	            default: return Object.class;
	     
	        }
	    }
		
		public Object getValueAt(int row, int col) {
			String valor = "";
			//ImageIcon fotoPerfil = null;
			Mensaje mensaj = mensajes.get(row);

			switch (col) {
				case 0 : 
					
						//if (!mensaj.getTexto().equals("") && mensaj.getTexto()!=null  ) {
						// En el caso de que el mensaje se trate de un emoticono
						if ( mensaj.isEmoticono() ) {
							
							switch (mensaj.getEmoticono()) {
							case 1:
								valor = "emoji durmiendo ";
							break;
							
							case 2:
								valor = "emoji pensativo ";
							break;
							
							case 3:
								valor = "emoji sorprendido";
							break;
							
							case 4:
								valor = "emoji acalorado ";
							break;
							
							case 5:
								valor = "emoji corazon ";
							break;
							
							case 6:
								valor = "emoji gafas de sol ";
							break;
							
							case 7:
								valor = "emoji dedos ";
							break;
							
							case 8:
								valor = "emoji congelado";
							break;
							
							case 9:
								valor = "emoji llorando";
							break;
							

							}
							
							/*
							 * La intencion era mostrar el emoji en miniatura, sin embargo la libreria de emojis no lo permite
							ImageIcon imagenEmojiIcon = BubbleText.getEmoji(mensaj.getEmoticono());
							Image imagenEmoji = imagenEmojiIcon.getImage();
							Image imagenReescalada = imagenEmoji.getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH );
							return new ImageIcon(imagenReescalada);
							*/
							
						}else if (!mensaj.getTexto().equals("") && mensaj.getTexto()!=null) {
							valor = mensaj.getTexto();
						}
					
				break;
				
				case 1 : return mensaj.getHora();
					
				case 2 : valor = CatalogoUsuarios.getUnicaInstancia().getUsuario(mensaj.getTelefono()).getNombre();
						//valor = ControladorAppChat.getUnicaInstancia().obtenerContactos().	
				break;
				
		}	
			return valor;
		}
		public void setValueAt(Object value, int row, int col) { }
	}
	
}
