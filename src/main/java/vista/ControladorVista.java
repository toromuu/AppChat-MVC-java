package vista;


import javax.swing.JFrame;
import java.awt.BorderLayout;


import javax.swing.Box;

import java.awt.Dimension;
import java.awt.Image;


import javax.swing.SwingConstants;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.EventObject;
import java.util.List;

import java.awt.Color;

import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JMenu;



import javax.swing.JMenuItem;
import javax.swing.JMenuBar;

import java.awt.Toolkit;

import javax.swing.border.MatteBorder;


import controlador.ControladorAppChat;
import modelo.CatalogoUsuarios;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Grupo;
import modelo.Usuario;


import java.awt.Component;

import pulsador.IEncendidoListener;
import pulsador.Luz;

/*
 * Esta clase se encarga de crear e instanciar el resto de clases de la vista.
 * Esta centralizacion surge ante la necesidad de administrar las interacciones que se pueden
 * dar entre los distintos paneles.Por ejemplo, el hecho de crear un nuevo contacto 
 * provocará que otros elementos como el chat, la lista de estados, mostrar contactos, eliminar
 * contactos se vean alterados visualmente.
 * 
 */

public class ControladorVista extends JFrame {

	// CONSTANTES IMAGENES
	private static final String RECURSOS_VISTA_ESTADOS_PNG = "/recursosVista/estados.png";
	private static final String RECURSOS_VISTA_CHAT_PNG = "/recursosVista/chat.png";
	private static final String RECURSOS_VISTA_100X100_PNG = "/recursosVista/100x100.png";
	private static final String RECURSOS_VISTA_ROJOUSER64_PNG = "/recursosVista/rojouser64.png";
	private static final String RECURSOS_VISTA_GRUPO_ICON_PNG = "/recursosVista/grupo-icon.png";
	private static final String RECURSOS_VISTA_100X100USER_PNG = "/recursosVista/100x100user.png";
	private static final long serialVersionUID = 1L;
	
	//Objetos
	private Usuario usuario = null;
	
	// PANELES
	private PanelDatosUsuario pnlDatosUsuario;
	private static PanelCrearGrupo pnlCrearGrupo;
	private static PanelModificarGrupo pnlModificarGrupo;
	private static PanelMostrarContactos pnlMostrarContactos;
	public static PanelChat pnlChat;
	private PanelMostrarEstadisticas pnlMostrarEstadisticas;
	private static PanelBusquedaMensaje pnlBusquedaMensaje;
	private static PanelEliminar pnlEliminar;
	
	public static DialogoConvertirsePremium dialogoConvertirsePremium;
	static  DialogoInfoContacto dialogoInfoContacto;
	private VentanaListaEstados ventanaListaEstados;
	public static DialoCrearContacto dialogoCrearContacto;
	public static DialogoMiniaturaEmoticonos dialogoMiniaturaEmoticonos;
	public static DialogoImportarConversacionWhatsApp dialogoImportarChat;

	
	//MENUS
	public static JFrame frmMain;
	private static JMenu mnDatosUsuario;
	private JMenu mnListaEstados;
	private JMenu mnOpciones;
	private static JMenu mnInfoContacto;
	private JMenu mnBusquedaMensajes;
	private JMenu mnEliminar;
	public static List<ContactoIndividual> contactos;
	
	
	//*MENU ITEMS
	private JMenuItem mntmCrearContacto;
	private JMenuItem mntmCrearNuevoGrupo;
	private JMenuItem mntmModificarGrupo;
	private JMenuItem mntmMostrarContactos;
	private JMenuItem mntmPremium;
	private JMenuItem mntmCerrarSesin;
	private JMenuItem mntmMostrarEstadisticas;
	
	//AUX VISTAS
	private Component rigidArea_1;
	private Component rigidArea;
	private JMenu mnChat;
	private Component rigidArea_3;
	private Component rigidArea_4;
	private Component rigidArea_6;
	private Luz luz;
	
	//AUX INFO CONTACTOS
	public static String ImagenContactoActual;
	public static String NombreContactoActual;
	public static String TelefonoContactoActual;
	public static String EmailContactoActual;
	public static Contacto contactoActual;
	
	public ControladorVista(Point p) {
		initialize(p);
	}


	private void initialize(Point p) {
	
		// CREAMOS EL FRAME
		frmMain = new JFrame();
		frmMain.setBackground(Color.WHITE);
		frmMain.setForeground(Color.WHITE);
		frmMain.setMinimumSize(new Dimension(1100, 720));
		frmMain.setIconImage(Toolkit.getDefaultToolkit().getImage(ControladorVista.class.getResource(RECURSOS_VISTA_100X100_PNG)));
		frmMain.setLocation(p);
		frmMain.getContentPane().setForeground(Color.WHITE);
		frmMain.getContentPane().setBackground(Color.WHITE);
		frmMain.setTitle("UmuChat");
		frmMain.setBounds(100, 100, 1010, 720);
		frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMain.getContentPane().setLayout(new BorderLayout(0, 0));
		
		
		//CREAMOS EL MENU
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		
		// 1 .DATOS USUARIO
		mnDatosUsuario = new JMenu("");
		mnDatosUsuario.setBackground(Color.WHITE);
		
		usuario = ControladorAppChat.getUnicaInstancia().getUsuarioActual();
		
		if (usuario.getImagen() == null) {
			mnDatosUsuario.setIcon(new ImageIcon(ControladorVista.class.getResource(RECURSOS_VISTA_100X100USER_PNG)));
		}
		else {
			File tempFile = new File(usuario.getImagen());
			 
		    if (tempFile.exists()) {
				//OBTENEMOS LA IMAGEN DEL USUARIO
				ImageIcon imagenUsuarioIcon = new ImageIcon(usuario.getImagen());
				//LA REESCALAMOS
				Image imagenUsuario = imagenUsuarioIcon.getImage();
				Image imagenReescalada = imagenUsuario.getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH );
				//MOSTRAMOS LA IMAGEN REESCALADA
				mnDatosUsuario.setIcon(new ImageIcon(imagenReescalada));
		    }else {
		    	mnDatosUsuario.setIcon(new ImageIcon(ControladorVista.class.getResource(RECURSOS_VISTA_100X100USER_PNG)));
		    }
		}
			
		mnDatosUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(mnDatosUsuario);
		
		rigidArea = Box.createRigidArea(new Dimension(70, 50));
		rigidArea.setMinimumSize(new Dimension(50, 50));
		menuBar.add(rigidArea);
		
		
		// 2 .ESTADOS
		mnListaEstados = new JMenu("");
		mnListaEstados.setBackground(Color.WHITE);
		mnListaEstados.setIcon(new ImageIcon(ControladorVista.class.getResource(RECURSOS_VISTA_ESTADOS_PNG)));
		mnListaEstados.setHorizontalAlignment(SwingConstants.RIGHT);
		menuBar.add(mnListaEstados);
		
		rigidArea_1 = Box.createRigidArea(new Dimension(20, 50));
		menuBar.add(rigidArea_1);
		
						
		// 3.OPCIONES
		mnOpciones = new JMenu("");
		mnOpciones.setBackground(Color.WHITE);
		mnOpciones.setIcon(new ImageIcon(ControladorVista.class.getResource(RECURSOS_VISTA_CHAT_PNG)));
		mnOpciones.setHorizontalAlignment(SwingConstants.RIGHT);
		menuBar.add(mnOpciones);
		
		// 3.1 CREAR CONTACTO
		mntmCrearContacto = new JMenuItem("Crear Contacto");
		mnOpciones.add(mntmCrearContacto);
		
		// 3.2 CREAR NUEVO GRUPO
		mntmCrearNuevoGrupo = new JMenuItem("Crear Nuevo Grupo");
		mnOpciones.add(mntmCrearNuevoGrupo);
		
		// 3.3 MODIFICAR GRUPO
		mntmModificarGrupo = new JMenuItem("Modificar Grupo");
		mnOpciones.add(mntmModificarGrupo);
		
		// 3.4 MOSTRAR CONTACTOS
		mntmMostrarContactos= new JMenuItem("Mostrar Contactos");
		mnOpciones.add(mntmMostrarContactos);
		
		
		// 3.5 CONVERTIRSE EN PREMIUM
		mntmPremium = new JMenuItem("Convertirse en Premium");
		mnOpciones.add(mntmPremium);
		
		// 3.6 CERRAR SESION
		mntmCerrarSesin = new JMenuItem("Cerrar Sesi\u00F3n");
		mnOpciones.add(mntmCerrarSesin);
		
		
		// 3.7 MOSTRAR ESTADISTICAS
		mntmMostrarEstadisticas = new JMenuItem("Mostrar Estadisticas");
		mnOpciones.add(mntmMostrarEstadisticas);
		
		rigidArea_4 = Box.createRigidArea(new Dimension(70, 50));
		rigidArea_4.setMinimumSize(new Dimension(50, 50));
		menuBar.add(rigidArea_4);
		
		// 4 CHAT
		mnChat = new JMenu("");
		mnChat.setIcon(new ImageIcon(ControladorVista.class.getResource(RECURSOS_VISTA_100X100_PNG)));
		mnChat.setHorizontalAlignment(SwingConstants.RIGHT);
		mnChat.setBackground(Color.WHITE);
		menuBar.add(mnChat);
		

		rigidArea_3 = Box.createRigidArea(new Dimension(70, 50));
		rigidArea_3.setMinimumSize(new Dimension(50, 50));
		menuBar.add(rigidArea_3);
		
		// 5. INFO-CONTACTO
		mnInfoContacto = new JMenu(PanelChat.NombreContactoActual);
		mnInfoContacto.setBackground(Color.WHITE);
		mnInfoContacto.setHorizontalAlignment(SwingConstants.RIGHT);
		menuBar.add(mnInfoContacto);
		
		
		
		Component rigidArea_5 = Box.createRigidArea(new Dimension(20,50));
		menuBar.add(rigidArea_5);
		
		// 6. BUSQUEDA MENSAJES
		mnBusquedaMensajes = new JMenu("");
		mnBusquedaMensajes.setBackground(Color.WHITE);
		mnBusquedaMensajes.setIcon(new ImageIcon(ControladorVista.class.getResource("/recursosVista/buscar.png")));
		mnBusquedaMensajes.setHorizontalAlignment(SwingConstants.RIGHT);
		menuBar.add(mnBusquedaMensajes);
		
		Component rigidArea_2 = Box.createRigidArea(new Dimension(70,50));
		rigidArea_2.setMinimumSize(new Dimension(50, 50));
		menuBar.add(rigidArea_2);
				
		// 7. ELIMINAR
		mnEliminar = new JMenu("");
		mnEliminar.setBackground(Color.WHITE);
		mnEliminar.setIcon(new ImageIcon(ControladorVista.class.getResource("/recursosVista/opciones2.png")));
		mnEliminar.setHorizontalAlignment(SwingConstants.RIGHT);
		menuBar.add(mnEliminar);
		
		
		// INFORMACION SOBRE LOS MENUS
		mnDatosUsuario.setToolTipText("Muestra informacion del usuario actual");
		mnListaEstados.setToolTipText("Despligea una ventana con una lista de estados de los contactos agregados");
		mnChat.setToolTipText("Sirve para mostrar la pantalla principal de chats");
		mnInfoContacto.setToolTipText("Muestra informacion del contacto seleccionado en el chat");
		mnBusquedaMensajes.setToolTipText("Busqueda de mensajes según diversos parametros");
		mnEliminar.setToolTipText("Eliminar un contacto agregado o la conversación con el mismo");
		
		mntmCrearContacto.setToolTipText("Sirve para agregar un contacto");
		mntmCrearNuevoGrupo.setToolTipText("Sirve para crear un grupo a partir de los contactos actuales");
		mntmModificarGrupo.setToolTipText("Sirve para modificar los grupos con permiso de administrador( grupos creados)");
		mntmMostrarContactos.setToolTipText("Sirve para mostrar una lista con los contactos agregados");
		mntmPremium.setToolTipText("Sirve para suscribirse al servicio Premium y desbloquear funcionalidad adicional");
		mntmCerrarSesin.setToolTipText("Sirve cerrar sesión y acceder a otra cuenta");
		mntmMostrarEstadisticas.setToolTipText("Sirve para generar estadísticas sobre el usuario y el uso del chat");
		
				
		// 8. COMPONENTE LUZ
		frmMain.setJMenuBar(menuBar);
		rigidArea_6 = Box.createRigidArea(new Dimension(70, 50));
		rigidArea_6.setMaximumSize(new Dimension(50, 50));
		rigidArea_6.setMinimumSize(new Dimension(50, 45));
		menuBar.add(rigidArea_6);
		luz = new Luz();
		luz.setMinimumSize(new Dimension(45, 45));
		luz.setMaximumSize(new Dimension(45, 45));
		luz.setColor(new Color(220, 20, 60));
		menuBar.add(luz);
		
     
     
		// INSTANCIACIÓN DE LOS PANELES
		 pnlDatosUsuario = new PanelDatosUsuario(frmMain);
		 contactos = ControladorAppChat.getUnicaInstancia().getUsuarioActual().obtenerContactos();
	     pnlCrearGrupo = new PanelCrearGrupo(frmMain,null);
	 	 pnlModificarGrupo = new PanelModificarGrupo(frmMain);
		 pnlMostrarContactos = new PanelMostrarContactos(frmMain);
		 pnlMostrarEstadisticas = new PanelMostrarEstadisticas(frmMain);
		 pnlBusquedaMensaje  = new PanelBusquedaMensaje(frmMain);
		 pnlEliminar  = new PanelEliminar(frmMain);
		 pnlChat = new PanelChat(frmMain);
		 
		 
		// INSTANCIACION DE DIALOGOS
		dialogoInfoContacto = new DialogoInfoContacto();
		dialogoInfoContacto.setVisible(false);
		
		dialogoConvertirsePremium = new DialogoConvertirsePremium();
		dialogoConvertirsePremium.setVisible(false);
		
		dialogoCrearContacto = new DialoCrearContacto(null);
		dialogoCrearContacto.setVisible(false);
		
		dialogoMiniaturaEmoticonos = new DialogoMiniaturaEmoticonos();
		dialogoMiniaturaEmoticonos.setVisible(false);
		
		dialogoImportarChat = new DialogoImportarConversacionWhatsApp();
		dialogoImportarChat.setVisible(false);
		
		
		//Panel Inicial tras el login
		 frmMain.setContentPane(pnlChat);
		 frmMain.revalidate();
		 frmMain.repaint();

		 		
		// CONTROLADORES DE EVENTOS----------------------------
		 
		// No se han parametrizado ya que algunos necesitaban un mouselistener en vez de un action listener , al tratarse de menuitems
		
		// 1 .DATOS USUARIO
		  mnDatosUsuario.addMouseListener(new MouseAdapter() {
		    	 @Override
		    	public void mouseClicked(MouseEvent e) {
		    		frmMain.setContentPane(pnlDatosUsuario);
					frmMain.revalidate();
					frmMain.repaint();
				}
			});
	     
	
		// 2 .ESTADOS
		  mnListaEstados.addMouseListener(new MouseAdapter() {
		    	 @Override
		    	public void mouseClicked(MouseEvent e) {
				ventanaListaEstados = new VentanaListaEstados();
				ventanaListaEstados.getFrmMain().setLocation(frmMain.getLocation());
				ventanaListaEstados.mostrarVentana();
			}
		});
		
					
		// 3.1 CREAR CONTACTO
		mntmCrearContacto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialogoCrearContacto.setLocation(frmMain.getLocation());
				setLocation(frmMain.getLocation());
				dialogoCrearContacto.setVisible(true);
				
			}
		});
		
		// 3.2 CREAR NUEVO GRUPO
		mntmCrearNuevoGrupo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frmMain.setContentPane(pnlCrearGrupo);
				frmMain.revalidate();
				frmMain.repaint();
			}
		});
	
		// 3.3 MODIFICAR GRUPO
		mntmModificarGrupo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frmMain.setContentPane(pnlModificarGrupo);
				frmMain.revalidate();
				frmMain.repaint();
			}
		});
		
		
		// 3.4 MOSTRAR CONTACTOS
		
		mntmMostrarContactos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frmMain.setContentPane(pnlMostrarContactos);
				frmMain.revalidate();
				frmMain.repaint();
			}
		});
		
		
		// 3.5 CONVERTIRSE EN PREMIUM
		mntmPremium.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogoConvertirsePremium.setLocation(frmMain.getLocation());
				dialogoConvertirsePremium.setVisible(true);
			}
		});
		
		// 3.6 CERRAR SESION
		mntmCerrarSesin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PanelLogin ventana = new PanelLogin();
				ventana.mostrarVentana();
				dialogoInfoContacto.dispose();
				PanelChat.setImagenContactoActual(null);
				frmMain.dispose();
				
			}
		});
		
		
		// 3.7 MOSTRAR ESTADISTICAS
		
		mntmMostrarEstadisticas.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
	
				frmMain.setContentPane(pnlMostrarEstadisticas);
				frmMain.revalidate();
				frmMain.repaint();

			}
		});
		
		// 4 CHAT
		
		mnChat.addMouseListener(new MouseAdapter() {
	    	 @Override
	    	public void mouseClicked(MouseEvent e) {
				 frmMain.setContentPane(pnlChat);
				 frmMain.revalidate();
				 frmMain.repaint();
			}
		});
		
		// 5. INFO-CONTACTO
		
		mnInfoContacto.addMouseListener(new MouseAdapter() {
	    	 @Override
	    	public void mouseClicked(MouseEvent e) {
				dialogoInfoContacto.pack();
				dialogoInfoContacto.setLocation(frmMain.getLocation());
				dialogoInfoContacto.setVisible(true);
			}
		});
		
		if (  getPanelChat().getImagenContactoActual() != null ) {
			
			File tempFile = new File(getImagenContactoActual());
			 
		    if (tempFile.exists()) {
			//OBTENEMOS LA IMAGEN DEL USUARIO
			ImageIcon imagenUsuarioIcon = new ImageIcon(getPanelChat().getImagenContactoActual() );
			//LA REESCALAMOS
			Image imagenUsuario = imagenUsuarioIcon.getImage();
			Image imagenReescalada = imagenUsuario.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH );
			//MOSTRAMOS LA IMAGEN REESCALADA
			mnInfoContacto.setIcon(new ImageIcon(imagenReescalada));
		    }else {
		    	mnInfoContacto.setIcon(new ImageIcon(ControladorVista.class.getResource( RECURSOS_VISTA_ROJOUSER64_PNG )));
		    }
		    
		}
		
		else mnInfoContacto.setIcon(new ImageIcon(ControladorVista.class.getResource( RECURSOS_VISTA_ROJOUSER64_PNG )));
		
		// 6. BUSQUEDA MENSAJES
		mnBusquedaMensajes.addMouseListener(new MouseAdapter() {
	    	 @Override
	    	public void mouseClicked(MouseEvent e) {
				frmMain.setContentPane(pnlBusquedaMensaje);
				frmMain.revalidate();
				frmMain.repaint();
			}
		});
		

		// 7. ELIMINAR
		
		mnEliminar.addMouseListener(new MouseAdapter() {
	    	 @Override
	    	public void mouseClicked(MouseEvent e) {
				frmMain.setContentPane(pnlEliminar);
				frmMain.revalidate();
				frmMain.repaint();
				
				//PanelPrincipal.this.validate(); 
			}
		});
		
		
		// 8. COMPONENTE LUZ-EXPORTAR CHATS
		
		luz.addEncendidoListener(new IEncendidoListener() {
			
			@Override
			public void enteradoCambioEncendido(EventObject arg0) {
				dialogoInfoContacto.setLocation(frmMain.getLocation());
				dialogoImportarChat.setVisible(true);
			}
		});
		
	}
	

	// FUNCIONALIDAD DE RESFRESCO DE ACTUALIZACION DE PANTALLAS

	public void mostrarVentana() {
		frmMain.setVisible(true);
	}
	
	public static void ActualizarVentanasImplicanContactos() {
		pnlChat = new PanelChat(frmMain);
		pnlEliminar = new PanelEliminar(frmMain);
		pnlMostrarContactos = new PanelMostrarContactos(frmMain);
		contactos = ControladorAppChat.getUnicaInstancia().getUsuarioActual().obtenerContactos();
	    pnlCrearGrupo = new PanelCrearGrupo(frmMain,null);
	    pnlModificarGrupo = new PanelModificarGrupo(frmMain);

		frmMain.setContentPane(pnlChat);
		frmMain.revalidate();
		frmMain.repaint();
	}
	
	
	
	public static void ActualizarEliminar() {
		pnlChat = new PanelChat(frmMain);
		pnlEliminar = new PanelEliminar(frmMain);
		pnlMostrarContactos = new PanelMostrarContactos(frmMain);
		contactos = ControladorAppChat.getUnicaInstancia().getUsuarioActual().obtenerContactos();
	    pnlCrearGrupo = new PanelCrearGrupo(frmMain,null);
	    pnlModificarGrupo = new PanelModificarGrupo(frmMain);
		
		frmMain.setContentPane(pnlEliminar);
		frmMain.revalidate();
		frmMain.repaint();
	}
	
	
	public static void ActualizarBusquedaMensajes() {
		pnlBusquedaMensaje = new PanelBusquedaMensaje(frmMain);
		frmMain.revalidate();
		frmMain.repaint();
	}
	
	
	public static void ActualizarPnlChat() {
		pnlChat = new PanelChat(frmMain);
		frmMain.setContentPane(pnlChat);
		frmMain.revalidate();
		frmMain.repaint();
	}
	
	public static void ActualizarImagenContacto() {
	
	 // if (getPanelChat() != null) {
		  
		  if (  getImagenContactoActual() != null ) {
			  
				File tempFile = new File(getImagenContactoActual());
				 
			    if (tempFile.exists()) {
			    	//OBTENEMOS LA IMAGEN DEL USUARIO
				  	System.out.println(getImagenContactoActual());
					ImageIcon imagenUsuarioIcon = new ImageIcon(getImagenContactoActual());
					//LA REESCALAMOS
					Image imagenUsuario = imagenUsuarioIcon.getImage();
					Image imagenReescalada = imagenUsuario.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH );
					//MOSTRAMOS LA IMAGEN REESCALADA
					mnInfoContacto.setIcon(new ImageIcon(imagenReescalada));
			    	
			    }else {
			    	mnInfoContacto.setIcon(new ImageIcon(ControladorVista.class.getResource( RECURSOS_VISTA_ROJOUSER64_PNG )));
			    	
			    }
				
			}
			else { 
				
				if (getContactoActual() instanceof ContactoIndividual) {
					mnInfoContacto.setIcon(new ImageIcon(ControladorVista.class.getResource( RECURSOS_VISTA_ROJOUSER64_PNG )));
				}
				else {
					mnInfoContacto.setIcon(new ImageIcon(ControladorVista.class.getResource( RECURSOS_VISTA_GRUPO_ICON_PNG )));
				}
				
			}
			
			mnInfoContacto.setText(getNombreContactoActual());
			frmMain.revalidate();
			frmMain.repaint();
		  
	  //}
		
		
	}

	public static void ActualizarImagenDatosPersonales(Image imagen) {
		
		
		if ( imagen != null) {
			Image imagenReescalada = imagen.getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH );
			mnDatosUsuario.setIcon(new ImageIcon(imagenReescalada));
		}
		else {
			mnDatosUsuario.setIcon(new ImageIcon(ControladorVista.class.getResource(RECURSOS_VISTA_100X100USER_PNG)));
		}	
	}
	
	public static PanelChat getPanelChat() {
		return pnlChat;
	}


	public static String getImagenContactoActual() {
		return ImagenContactoActual;
	}


	public static void setImagenContactoActual(String imagenContactoActual) {
		ImagenContactoActual = imagenContactoActual;
	}


	public static String getNombreContactoActual() {
		return NombreContactoActual;
	}


	public static void setNombreContactoActual(String nombreContactoActual) {
		NombreContactoActual = nombreContactoActual;
	}


	public static String getTelefonoContactoActual() {
		return TelefonoContactoActual;
	}


	public static void setTelefonoContactoActual(String telefonoContactoActual) {
		TelefonoContactoActual = telefonoContactoActual;
	}


	public static String getEmailContactoActual() {
		return EmailContactoActual;
	}


	public static void setEmailContactoActual(String emailContactoActual) {
		EmailContactoActual = emailContactoActual;
	}


	public static Contacto getContactoActual() {
		return contactoActual;
	}


	public static void setContactoActual(Contacto contactoActual) {
		ControladorVista.contactoActual = contactoActual;
		
		if (contactoActual instanceof ContactoIndividual ) {
			
			ContactoIndividual usuarioIndividual = (ContactoIndividual) contactoActual;
			setImagenContactoActual(CatalogoUsuarios.getUnicaInstancia().getUsuario(usuarioIndividual.getTelefono()).getImagen()) ;
			setNombreContactoActual(contactoActual.getNombre());
			setTelefonoContactoActual(Integer.toString(usuarioIndividual.getTelefono()));
			setEmailContactoActual( CatalogoUsuarios.getUnicaInstancia().getUsuario(usuarioIndividual.getTelefono()).getEmail());
			//System.out.println("Lista de mensajes individuales : "+ contactoActual.getMensajes().toString());
		}
		
		else if (contactoActual instanceof Grupo) {
			setNombreContactoActual(contactoActual.getNombre());
			setImagenContactoActual(null) ;
			setTelefonoContactoActual("");
			setEmailContactoActual("");
		}
		
		
		//ActualizarImagenContacto();
	}
	
	
	
	

}
	
	
