package vista;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;


import java.awt.Image;

import javax.swing.JScrollPane;
import tds.BubbleText;
import java.awt.Dimension;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controlador.ControladorAppChat;
import modelo.CatalogoUsuarios;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Grupo;
import modelo.Mensaje;
import modelo.Usuario;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* 
 * Esta clase define un panel de chat.
 * Es en este panel donde el usuario puede seleccionar entre sus distintos contactos agregados
 * visualizar y enviar mensajes y emoticonos a estos. 
 * Esta integrado pos dos elementos principales. 
 * Un JList con los contactos agregados por el usuario
 * Un panel, integrado por un scrollpane donde se pueden visualizar los mensajes con el contacto actual seleccionado
 * y dos botones, uno para enviar el mensaje y otro para desplegar un Dialogo para enviar un emoticono
 * Es el panel inicial de la aplicación
 * 
 */
@SuppressWarnings("rawtypes")
public class PanelChat extends JPanel implements ListCellRenderer {
	
	private static final String RECURSOS_VISTA_GRUPO_ICON_PNG = "/recursosVista/grupo-icon.png";


	private static final String RECURSOS_VISTA_100X100USER_PNG = "/recursosVista/100x100user.png";
	

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	public JPanel conversacion;
	public List<BubbleText> listaBurbujas;
	private ControladorAppChat controlador = ControladorAppChat.getUnicaInstancia();
	private JScrollPane scrollPane;
	
	public static String ImagenContactoActual;
	public static String NombreContactoActual;
	public String TelefonoContactoActual;
	public static String EmailContactoActual;
	public static Contacto contactoActual;
	
	private List<Contacto> contactos;
	private JScrollPane scrollPane_1;
	private JList<Contacto> listaContactos;
	
	
	public PanelChat(JFrame frame) {
		
		//JFrame ventana = frame;	
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));


		//PARA OBTENER EL NOMBRE DEL CONTACTO ASOCIADO AL ESTADO
		
		this.scrollPane_1 = new JScrollPane();
		this.scrollPane_1.setMinimumSize(new Dimension(60, 60));
		add(scrollPane_1);
		listaContactos = generarJList();
		

		//AJUSTES DEL PANEL 
		
		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		scrollPane = new JScrollPane();
		scrollPane.setMaximumSize(new Dimension(700, 400));
		scrollPane.setMinimumSize(new Dimension(700, 400));
		scrollPane.setSize(new Dimension(700, 400));
		panel.add(scrollPane);

		conversacion = new JPanel();
		conversacion.setMaximumSize(new Dimension(400, 700));
		conversacion.setMinimumSize(new Dimension(400, 700));
		conversacion.setSize(new Dimension(400, 700));
		scrollPane.setViewportView(conversacion);
		conversacion.setLayout(new BoxLayout(conversacion, BoxLayout.Y_AXIS));

		listaBurbujas = new LinkedList<>();
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] { 30, 0, 85, 129, 116, 0, 0, 0, 30, 0 };
		gbl_panel_2.rowHeights = new int[] { 25, 0 };
		gbl_panel_2.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_2.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_2.setLayout(gbl_panel_2);

		JButton btnBotonemoticonos = new JButton("BotonEmoticonos");
		GridBagConstraints gbc_btnBotonemoticonos = new GridBagConstraints();
		gbc_btnBotonemoticonos.anchor = GridBagConstraints.WEST;
		gbc_btnBotonemoticonos.insets = new Insets(0, 0, 0, 5);
		gbc_btnBotonemoticonos.gridx = 1;
		gbc_btnBotonemoticonos.gridy = 0;
		panel_2.add(btnBotonemoticonos, gbc_btnBotonemoticonos);
		btnBotonemoticonos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ControladorVista.dialogoMiniaturaEmoticonos.setVisible(true);
				ControladorVista.dialogoMiniaturaEmoticonos.setLocation(ControladorVista.frmMain.getLocation());

			}
		});
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 5;
		gbc_textField.insets = new Insets(0, 0, 0, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 0;
		panel_2.add(textField, gbc_textField);
		textField.setColumns(10);

		JButton btnEnviarMensaje = new JButton("Enviar Mensaje");
		GridBagConstraints gbc_btnEnviarMensaje = new GridBagConstraints();
		gbc_btnEnviarMensaje.insets = new Insets(0, 0, 0, 5);
		gbc_btnEnviarMensaje.gridx = 7;
		gbc_btnEnviarMensaje.gridy = 0;
		panel_2.add(btnEnviarMensaje, gbc_btnEnviarMensaje);
		
		if(contactos.size() !=0 ) {
			cargarBurbujas(contactos.get(listaContactos.getSelectedIndex()), conversacion);
		}
		
		
		// Inicialmente
		 if (contactos.size() !=0 ) {
			 contactoActual = (Contacto) contactos.get(0);
			 ControladorVista.setContactoActual(contactoActual);
		 }
		 
		/*
		 if (contactoActual instanceof ContactoIndividual ) {
				
				ContactoIndividual usuarioIndividual = (ContactoIndividual) contactoActual;
				setImagenContactoActual(CatalogoUsuarios.getUnicaInstancia().getUsuario(usuarioIndividual.getTelefono()).getImagen()) ;
				NombreContactoActual= contactoActual.getNombre();
				TelefonoContactoActual = Integer.toString(usuarioIndividual.getTelefono());
				EmailContactoActual = CatalogoUsuarios.getUnicaInstancia().getUsuario(usuarioIndividual.getTelefono()).getEmail();
				
		}
			
		else if (contactoActual instanceof Grupo) {
				NombreContactoActual= listaContactos.getSelectedValue().getNombre();
		}
		*/
		
		 
		ControladorVista.dialogoInfoContacto = new DialogoInfoContacto();
		ControladorVista.ActualizarImagenContacto();
		ControladorVista.dialogoInfoContacto.setVisible(false);
		
		// CAMBIOS VISUALES TRAS LA SELECCION DE UN CONTACTO
		actualizarEventJList();
				
		// ENVIO DE MENSAJES
		btnEnviarMensaje.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (textField.getText() != null || !textField.getText().equals("")) {
					
					if (contactoActual == null) {
						contactoActual = (Contacto) contactos.get(0);
					}
					else {contactoActual = (Contacto) contactos.get(listaContactos.getSelectedIndex());}
					
					Mensaje m = ControladorAppChat.getUnicaInstancia().enviarMensaje(textField.getText(),-1,contactoActual);
					BubbleText mssg = new BubbleText(conversacion, m.getTexto(), Color.WHITE,
					ControladorAppChat.getUnicaInstancia().getUsuarioActual().getNombre(), BubbleText.SENT, 10);	

					textField.setText("");
					listaContactos = generarJList();
					actualizarEventJList();
					
					listaBurbujas.add(mssg);
					conversacion.add(mssg);
					conversacion.repaint();
					ControladorVista.ActualizarBusquedaMensajes();
				}
				
			}
		});
		
	}

	// La generacion de un nuevo Jlist implica a su vez un nuevo listener, ya que el envio de un mensaje hace que dicho contacto se posicione 
	// el primero en la lista
	
	private void actualizarEventJList() {
		
		listaContactos.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				contactoActual= (Contacto) listaContactos.getSelectedValue();
				ControladorVista.setContactoActual(contactoActual);
				/*
				if (contactoActual instanceof ContactoIndividual ) {
					
					ContactoIndividual usuarioIndividual = (ContactoIndividual) contactoActual;
					setImagenContactoActual(CatalogoUsuarios.getUnicaInstancia().getUsuario(usuarioIndividual.getTelefono()).getImagen()) ;
					NombreContactoActual= contactoActual.getNombre();
					TelefonoContactoActual = Integer.toString(usuarioIndividual.getTelefono());
					EmailContactoActual = CatalogoUsuarios.getUnicaInstancia().getUsuario(usuarioIndividual.getTelefono()).getEmail();
					//System.out.println("Lista de mensajes individuales : "+ contactoActual.getMensajes().toString());
				}
				
				else if (contactoActual instanceof Grupo) {
					NombreContactoActual= listaContactos.getSelectedValue().getNombre();
					
					setImagenContactoActual(null);
					NombreContactoActual= contactoActual.getNombre();
					TelefonoContactoActual = "";
					EmailContactoActual = "";
				}*/
				
				borrarBurbujas(conversacion);
				cargarBurbujas(contactoActual, conversacion);
				
				ControladorVista.ActualizarImagenContacto();
				ControladorVista.dialogoInfoContacto = new DialogoInfoContacto();
				ControladorVista.dialogoInfoContacto.setVisible(false);
				
				
				
			}
		});
	}
	
	// Esta funcionalidad permite actualizar el Jlist con los contactos disponibles en los casos en los que un contacto sea eliminado, creado
	
	private JList<Contacto> generarJList() {
		
		if (ControladorAppChat.getUnicaInstancia().getUsuarioActual().obtenerContactos().size()>1) {
			 contactos = ControladorAppChat.getUnicaInstancia().getUsuarioActual().getConversacionesOrdenadas();}
		else { contactos = ControladorAppChat.getUnicaInstancia().getUsuarioActual().getConversaciones(); }
	
		//PARA CREAR EL ARRAY PARA EL MODELO DEL JLIST
		Contacto[] arrayContactos = new Contacto[contactos.size()];
		int i = 0;
		synchronized (arrayContactos) {
			for (Contacto contacto : contactos) {
				arrayContactos[i] = contacto;
				i++;
			}
		}

		JList<Contacto> listaContactosAux = new JList<Contacto>();
		listaContactosAux.setModel(new AbstractListModel<Contacto>() {

			private static final long serialVersionUID = 1L;
			Contacto[] values = arrayContactos;

			public int getSize() {
				return values.length;
			}

			public Contacto getElementAt(int index) {
				return values[index];
			}
		});
		
		//IMPORTANTE SELECCIONAR EL PRIMER CHAT SELECCIONADO POR DEFECTO
		listaContactosAux.setSelectedIndex(0);
		if (ControladorAppChat.getUnicaInstancia().getUsuarioActual().obtenerContactos().size()>1) {
		contactoActual =  (Contacto) arrayContactos[0];
			ControladorVista.setContactoActual(contactoActual);
		}
		
		//if ( ControladorVista.getPanelChat() != null ) {
			//ControladorVista.ActualizarImagenContacto();
		//}
				
		ControladorVista.ActualizarImagenContacto();	
		
		listaContactosAux.setCellRenderer(createListRenderer());
		listaContactosAux.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		scrollPane_1.setViewportView(listaContactosAux);
		scrollPane_1.revalidate();
		scrollPane_1.repaint();;
		
		return listaContactosAux;
	}

	
	private void borrarBurbujas(JPanel conversacion) {
		Iterator<BubbleText> iterator = listaBurbujas.iterator();
		while (iterator.hasNext()){
				BubbleText mssg = iterator.next();
				conversacion.remove(mssg);
		 }
		
		listaBurbujas = new LinkedList<>();

	}
	
	// Esta funcionalidad permite cargar visualmente los mensajes asociados a una conversacion ya sea con un contacto indivual como con un grupo
	
	private void cargarBurbujas(Contacto contacto, JPanel conversacion) {
		List<BubbleText> listaCopia = new LinkedList<BubbleText>(listaBurbujas);
		List<Mensaje> mensajes = contacto.getMensajes();
		Usuario user = this.controlador.getUsuarioActual();
		
		for (Mensaje m : mensajes) {
			Usuario sender = CatalogoUsuarios.getUnicaInstancia().getUsuario(m.getTelefono());
			BubbleText mssg;
			//System.out.println(m.getEmoticono());
			if (sender.equals(user)) {
				if (m.getEmoticono() == Mensaje.EMOTICONO_VACIO) {
					mssg = new BubbleText(conversacion, m.getTexto(), Color.WHITE, user.getNombre(), BubbleText.SENT,10);
				} else {
					mssg = new BubbleText(conversacion, m.getEmoticono(), Color.WHITE, user.getNombre(),BubbleText.SENT, 10);
				}
			} else {
				if (m.getEmoticono() == Mensaje.EMOTICONO_VACIO) {
					mssg = new BubbleText(conversacion, m.getTexto(), Color.LIGHT_GRAY, contacto.getNombre(),BubbleText.RECEIVED, 10);
				} else {
					mssg = new BubbleText(conversacion, m.getEmoticono(), Color.LIGHT_GRAY, contacto.getNombre(),BubbleText.RECEIVED, 10);
				}
			}
			listaCopia.add(mssg);
			conversacion.add(mssg);
		}
		
		listaBurbujas = new LinkedList<>(listaCopia);
	}
	
	
	
	
	// Modificar visualmente el Jlist y que se puedan mostrar la imagen del contacto, el nombre y la fecha
	
	
	public Component getListCellRendererComponent(JList arg0, Object arg1, int arg2, boolean arg3, boolean arg4) {
		// TODO Auto-generated method stub
		return null;
	}
	

	
	
	private static ListCellRenderer<? super Contacto> createListRenderer() {
		return new DefaultListCellRenderer() {
		private static final long serialVersionUID = 1L;
			
		private Color background = new Color(0, 100, 255, 15);
		private Color defaultBackground = (Color) UIManager.get("List.background");
		
		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value,
		int index, boolean isSelected,

		boolean cellHasFocus) {
			Component c = super.getListCellRendererComponent(
			list, value, index, isSelected, cellHasFocus);
			
			
			if (c instanceof JLabel) {
				JLabel label = (JLabel) c;
				Contacto usuario = (Contacto) value;

				if (usuario instanceof ContactoIndividual ) {
					ContactoIndividual usuarioIndividual = (ContactoIndividual) value;
					String ruta = CatalogoUsuarios.getUnicaInstancia().getUsuario(usuarioIndividual.getTelefono()).getImagen();
					if ( ruta != null ) {
					
						File tempFile = new File(ruta);
						 
					    if (tempFile.exists()) {
						//OBTENEMOS LA IMAGEN DEL USUARIO
						ImageIcon imagenUsuarioIcon = new ImageIcon(CatalogoUsuarios.getUnicaInstancia().getUsuario(usuarioIndividual.getTelefono()).getImagen());
						//LA REESCALAMOS
						Image imagenUsuario = imagenUsuarioIcon.getImage();
						Image imagenReescalada = imagenUsuario.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH );
						//MOSTRAMOS LA IMAGEN REESCALADA
						label.setIcon(new ImageIcon(imagenReescalada));
					    }else {
					    	label.setIcon(new ImageIcon(PanelChat.class.getResource( RECURSOS_VISTA_100X100USER_PNG )));
					    }
					}
					
					else label.setIcon(new ImageIcon(PanelChat.class.getResource( RECURSOS_VISTA_100X100USER_PNG )));

					label.setText(String.format("%s [%s]", usuarioIndividual.getNombre(),LocalDate.now().toString() ) );
					if (!isSelected) {
					label.setBackground(
					index % 2 == 0 ? background : defaultBackground);
					}
					
				}
				
				else if (usuario instanceof Grupo) {
					
					Grupo grupo = (Grupo) value;
					label.setIcon(new ImageIcon(PanelChat.class.getResource( RECURSOS_VISTA_GRUPO_ICON_PNG)));	
					label.setText(String.format("%s [%s]", grupo.getNombre(),LocalDate.now().toString() ) );
					
					if (!isSelected) {
					label.setBackground(
					index % 2 == 0 ? background : defaultBackground);
					}
				}
				
			}
			return c;
		}
		};
	
	}
	
	public Contacto getContactoActual() {
		return contactoActual;
	}
	
	public String getImagenContactoActual() {
		return ImagenContactoActual;
	}

	public String getEmailContactoActual() {
		return EmailContactoActual;
	}

	public static void setImagenContactoActual(String imagenContactoActual) {
		ImagenContactoActual = imagenContactoActual;
	}
	
}
