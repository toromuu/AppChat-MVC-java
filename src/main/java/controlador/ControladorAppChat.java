package controlador;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Date;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import java.util.function.Predicate;




import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.PieChart;


import com.lowagie.text.DocumentException;


//import Componente.CargadorMensajes;
//import Componente.MensajesEvent;
//import Componente.MensajesListener;
import modelo.CatalogoUsuarios;
import modelo.Contacto;
import modelo.Usuario;
import parserWhatsAppComponente.CargadorMensajes;
import parserWhatsAppComponente.IMensajeListener;
import parserWhatsAppComponente.MensajeWhatsApp;
import parserWhatsAppComponente.MensajesCargadosEvent;
import modelo.Mensaje;
//import modelo.MensajeWhatsApp;
//import modelo.Plataforma;
import modelo.ContactoIndividual;
import modelo.DescuentoMensajesUltimoMes;
import modelo.DescuentoTerceraEdad;
import modelo.Grupo;
import persistencia.AdaptadorContactoIndividual;
import persistencia.AdaptadorMensaje;
import persistencia.AdaptadorUsuario;
import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorUsuarioDAO;
import persistencia.IAdaptadorMensajeDAO;
import persistencia.IAdaptadorContactoIndividualDAO;
import persistencia.IAdaptadorGrupo;

/*
 * Clase fundamental en el proyecto sobre la cual recae el control de la funcionalidad
 * Permite la separacion vista-modelo
 * Usa invocaciones de las clases del modelo y la persistencia para
 * gestionar la aplicación
 */

public class ControladorAppChat implements IMensajeListener{

	private String nombreGrupoAntiguo;
	private static ControladorAppChat unicaInstancia = new ControladorAppChat();
	
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	@SuppressWarnings("unused")
	private IAdaptadorMensajeDAO adaptadorMensaje;
	private IAdaptadorContactoIndividualDAO adaptadorContactoIndividual;
	private IAdaptadorGrupo adaptadorGrupo;
	private DescuentoTerceraEdad descuentoTerceraEdad;
	private DescuentoMensajesUltimoMes descuentoMensajesUltimoMes;
	
	private Usuario usuarioActual = null;
	private CatalogoUsuarios catalogoUsuarios;

	private CargadorMensajes cargadorMensajes;

	private ControladorAppChat() {
		inicializarAdaptadores(); 
		inicializarCatalogos();
		this.cargadorMensajes = new CargadorMensajes();
		this.cargadorMensajes.addListener(this);
		descuentoMensajesUltimoMes = DescuentoMensajesUltimoMes.getUnicaInstancia();
		descuentoTerceraEdad = DescuentoTerceraEdad.getUnicaInstancia();
	}

	// PATRON SINGLETON
	public static ControladorAppChat getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new ControladorAppChat();
		return unicaInstancia;
	}
	
	// AUX DEL CONTROLADOR
	private void inicializarAdaptadores() {
		FactoriaDAO factoria = null;
		try {
			factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_AppChat);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		adaptadorUsuario = factoria.getUsuarioDAO();
		adaptadorMensaje = factoria.getMensajeDAO();
		adaptadorContactoIndividual = factoria.getContactoIndividualDAO();
		adaptadorGrupo = factoria.getGrupoDAO();
	}

	private void inicializarCatalogos() {
		catalogoUsuarios = CatalogoUsuarios.getUnicaInstancia();
	}
	
	// ---------------------- METODOS ----------------------
	
	// USUARIO
	public boolean existeTelf(int telf) {
		
		if (CatalogoUsuarios.getUnicaInstancia().getUsuario(telf) == null ) return false;
		return true;
	}
	
	public boolean esxisteContacto(int telf) {
		return this.usuarioActual.existeContacto(telf);
	}
	
	public boolean registrarUsuario(String nombre, Date fechaNacimiento, String movil, String email, String nick, String contrasena) {
		Usuario usuario;
		try {
			usuario = new Usuario(nombre, fechaNacimiento, email, Integer.parseInt(movil), nick, contrasena);
			
		} catch (NumberFormatException e) {
			System.out.println("ERROR Registrar usuario");
			return false;
		}
		if (!adaptadorUsuario.registrarUsuario(usuario)) return false;
		catalogoUsuarios.addUsuario(usuario);
		return true;
	}

	public boolean existeUsuario(String login) {
		return catalogoUsuarios.existeUsuario(login);
	}
	
	public boolean comprobarContrasena(String login, String password) {
		return catalogoUsuarios.comprobarContrasena(login, password);
	}
	
	public Usuario getUsuarioActual() {
		return usuarioActual;
	}
	
	public void setUsuarioActual(String login) {
		usuarioActual = catalogoUsuarios.getUsuario(login);
	}
	
	// CONTACTO INDIVUAL
	
	public void crearContactoIndividual(String nombre, int telefono) {
		
		this.usuarioActual.anadirConversacion(new ContactoIndividual(nombre, telefono));
		this.adaptadorUsuario.modificarUsuario(usuarioActual);
	}
	
	public void modificarContactoIndividual(ContactoIndividual contactomodificar,Usuario user, String nombreNuevo) {
		contactomodificar.setNombre(nombreNuevo);
		this.adaptadorContactoIndividual.modificarContactoIndividual(contactomodificar);
		this.adaptadorUsuario.modificarUsuario(user);
	}
	
	
	// DESCUENTOS
	
	//Por politicas de empresa los Descuentos no son acumulables, solo se aplica el mas grande
	public float generarDescuentos(float precio) {
		String descuentoActual = usuarioActual.getDescuento();
		//System.out.println(descuentoActual);	
		if (descuentoActual.equals("")) {
			
			if (descuentoTerceraEdad.isAplicable(usuarioActual)) {
				this.getUsuarioActual().setDescuento(descuentoTerceraEdad.getNombreDescuento());
				AdaptadorUsuario.getUnicaInstancia().modificarUsuario(usuarioActual);
				return descuentoTerceraEdad.Aplicar(precio);
			}
			else if (descuentoMensajesUltimoMes.isAplicable(usuarioActual)) {
				this.getUsuarioActual().setDescuento(descuentoMensajesUltimoMes.getNombreDescuento());
				AdaptadorUsuario.getUnicaInstancia().modificarUsuario(usuarioActual);
				//System.out.println(usuarioActual.getDescuento());
				return descuentoMensajesUltimoMes.Aplicar(precio);
			}
		}
		else if (descuentoActual.equals("DescuentoTerceraEdad")) {
			return descuentoTerceraEdad.Aplicar(precio);
		}	
		else if (descuentoActual.equals("DescuentoMensajesUltimoMes")) {
			return descuentoMensajesUltimoMes.Aplicar(precio);
		}	
		return precio;
		
		
	}
	
	// GRUPOS
	/*
	 * Cuando se crea un grupo además de añadir este contacto  grupo al usuario que ha creado el grupo
	 * Se debe realizar una iteracion por cada uno de usuarios asociados a la lista de contacto participantes 
	 * para tambien añadir este contacto a su lista de contactos, descartandose a si mismos como participantes
	 * Además si estos contactos no tienen algun contacto de la lista de participantes se les añade dicho contacto
	 * Este metodo se reutiliza a la hora de modificar un grupo, pues si cambia el nombre del grupo o la lista de participantes
	 * debe hacerlo para todos, para usarlo con esta funcionalidad y evitar bororar y crear un grupo nuevo se pasa 
	 * como parametro el grupo Antiguo
	 */
	
	public void crearGrupo(Contacto grupoAntiguo , String nombre, int telefonoAdmin, List<Contacto> participantes) {

		Grupo grupoAux = (Grupo) grupoAntiguo;
		
		if (grupoAntiguo != null) {
			this.nombreGrupoAntiguo = grupoAux.getNombre();
			//System.out.println(grupoAux.getNombre());
			//this.eliminarContacto(grupoAntiguo,usuarioActual);
			//Grupo grupoAux = (Grupo) grupoAntiguo;
			this.modificarGrupo(grupoAux, participantes, nombre, usuarioActual);
		}
		else {
			this.usuarioActual.anadirConversacion(new Grupo(nombre, telefonoAdmin, participantes));
			this.adaptadorUsuario.modificarUsuario(usuarioActual);
		}
		
	
		for (Contacto contacto : participantes) {
			
			// Crea una lista auxiliar
			List<Contacto> participantesAux = new LinkedList<Contacto>();
			// Se obtiene el usuario asociado al contacto actual
			
			
			// Se añade en primer lugar al usuario admin
			Usuario receptor = ControladorAppChat.getUnicaInstancia().catalogoUsuarios.getUsuario(((ContactoIndividual) contacto).getTelefono());
			//ContactoIndividual contactoAux = (ContactoIndividual) contacto;
			//System.out.println("Usuario tratando " + receptor.getNombre());
			
			//if(checkUsuarioEnLista(receptor,participantes)) {
			  //if (!adminRegistrado) {
				if(receptor.checkUsuario(this.usuarioActual)){
					//System.out.println("Ya tenia admin registrado");
					ContactoIndividual ContactoIndv = receptor.obtenerContactoIndv(this.usuarioActual.getMovil());
					participantesAux.add(ContactoIndv);
					AdaptadorUsuario.getUnicaInstancia().modificarUsuario(receptor);
				}
				else {
					//System.out.println("Anade admin por primera vez");
					ContactoIndividual nuevoContacto = new ContactoIndividual(this.usuarioActual.getMovil().toString(), this.usuarioActual.getMovil());
					receptor.anadirConversacion(nuevoContacto);
					participantesAux.add(nuevoContacto);
					AdaptadorUsuario.getUnicaInstancia().modificarUsuario(receptor);
				}
			//}
			
		
			// En este segundo bucle se pretende que el resto de usuario asociados a un contacto de la lista de participantes, agregue al resto
			for (Contacto contacto2 : participantes) {
				
				Usuario receptorAux = ControladorAppChat.getUnicaInstancia().catalogoUsuarios.getUsuario(((ContactoIndividual) contacto2).getTelefono());

				//if(checkUsuarioEnLista(receptorAux,participantes)) {

					if(receptor.checkUsuario(receptorAux) ){
						//System.out.println("Entra ya lo tiene registrado");
						ContactoIndividual ContactoIndv2 = receptor.obtenerContactoIndv(receptorAux.getMovil());
						participantesAux.add(ContactoIndv2);
						AdaptadorUsuario.getUnicaInstancia().modificarUsuario(receptor);
					}
					else {
						//System.out.println("No lo tiene lo tiene registrado");
						
						if (!receptorAux.getMovil().equals(receptor.getMovil()) ) {
							//System.out.println("No es el mismo y lo puede agregar");
							ContactoIndividual nuevoContacto2 = new ContactoIndividual(receptorAux.getMovil().toString(), receptorAux.getMovil());
							receptor.anadirConversacion(nuevoContacto2);
							participantesAux.add(nuevoContacto2);
							AdaptadorUsuario.getUnicaInstancia().modificarUsuario(receptor);
						}
						
					}
					
				
				//}
				
			}

			
			if (grupoAntiguo != null ) {
				Grupo grupoAux2 = receptor.grupoRegistrado(nombreGrupoAntiguo);
				
				if (grupoAux2 != null ) {
					this.modificarGrupo(grupoAux2, participantesAux, nombre, receptor);
				}
				else {
					receptor.anadirConversacion(new Grupo(nombre, telefonoAdmin, participantesAux));
					this.adaptadorUsuario.modificarUsuario(receptor);
				}
				
			}	
			else {
				receptor.anadirConversacion(new Grupo(nombre, telefonoAdmin, participantesAux));
				this.adaptadorUsuario.modificarUsuario(receptor);
			}
			
		}
		
	}
	
	public List<String> obtenerGruposContacto(ContactoIndividual contacto) {
		
		List<String> gruposCompartidos = new LinkedList<>();
		
		for (Grupo grupo : this.usuarioActual.obtenerGrupos()) {
			
			for (Contacto participante: grupo.getParticipantes()) {
				
				ContactoIndividual participanteGrupo = (ContactoIndividual) participante;
				
				if (participanteGrupo.getTelefono() == contacto.getTelefono()) {
					gruposCompartidos.add(grupo.getNombre());
				}
			}
		}
		
		return gruposCompartidos;
	}
		

	public void modificarGrupo(Grupo grupo, List<Contacto> participantes,String nombre, Usuario user) {
		grupo.modificarParticipantes(participantes);
		grupo.setNombre(nombre);
		this.adaptadorGrupo.modificarGrupo(grupo);
		this.adaptadorUsuario.modificarUsuario(user);
	}
	
	// MENSAJES
	public Mensaje enviarMensaje(String text,int emoticono, Contacto contacto) {
		
		//System.out.println(java.sql.Date.valueOf(LocalDate.now()));
		//REVISAR
		//Mensaje mensaje = new Mensaje(text,java.sql.Date.valueOf(LocalDate.now()), this.usuarioActual.getMovil(), emoticono);
		Date ahora = new Date();
		Mensaje mensaje = new Mensaje(text,ahora, this.usuarioActual.getMovil(), emoticono);
		
		contacto.enviarMensaje(mensaje);
		this.adaptadorUsuario.modificarUsuario(usuarioActual);
		return mensaje;
	}
	
	public List<Mensaje> buscarMensajes(Predicate<Contacto> prContactos, List<Predicate<Mensaje>> listaPrMensaje){
		return usuarioActual.buscarMensajes(prContactos,listaPrMensaje);
	}
	

	
	// ELIMINAR 
	
	public boolean eliminarMensajesContacto(Contacto contacto) {
		if(contacto.puedeSerEliminado(this.usuarioActual)) {
			contacto.eliminarMensajes();
			this.adaptadorUsuario.modificarUsuario(usuarioActual);
			return true;
		}
		return false;
	}
	
	public boolean eliminarContacto(Contacto contacto, Usuario user) {
		//if (user.eliminarContacto(contacto)) {
			System.out.println("Ha borrado el contacto de la lista de conversaciones");
			//TODO MEJORAR CREANDO METODO BORRAR EN CONTACTO
			if(contacto instanceof ContactoIndividual) {
				this.adaptadorContactoIndividual.borrarContactoIndividual((ContactoIndividual) contacto);
			}
			else if(contacto instanceof Grupo) {
				this.adaptadorGrupo.borrarGrupo((Grupo) contacto);
			}
			user.eliminarContacto(contacto);
			this.adaptadorUsuario.modificarUsuario(user);
			return true;
		//}
		//return false;
	}
	
	public boolean eliminarGrupo(Contacto grupoAntiguo) {

		Grupo grupoAux = (Grupo) grupoAntiguo;
		List<Contacto> participantes = grupoAux.getParticipantes();
		grupoAux.eliminarMensajes();
		this.usuarioActual.eliminarContacto(grupoAux);
		this.adaptadorGrupo.borrarGrupo(grupoAux);
		this.adaptadorUsuario.modificarUsuario(usuarioActual);
		
		for (Contacto contacto : participantes) {
			if (contacto instanceof ContactoIndividual) {
				
				Usuario receptor = ControladorAppChat.getUnicaInstancia().catalogoUsuarios.getUsuario(((ContactoIndividual) contacto).getTelefono());
				Grupo grupoEliminar=null;
				
				for (Grupo grupo : receptor.obtenerGrupos()) {
					if (grupo.getNombre().equals(grupoAux.getNombre())) {
						grupoEliminar=grupo;
					}
				}
				
				if (grupoEliminar!=null) {
					grupoEliminar.eliminarMensajes();
					receptor.eliminarContacto(grupoEliminar);
					this.adaptadorGrupo.borrarGrupo(grupoEliminar);
					this.adaptadorUsuario.modificarUsuario(receptor);
				}
			}
			
		}
		return true;
	}
	
	public boolean eliminarMensajesGrupo(Contacto grupoAntiguo) {

		Grupo grupoAux = (Grupo) grupoAntiguo;
		List<Contacto> participantes = grupoAux.getParticipantes();
		grupoAux.eliminarMensajes();
		this.adaptadorGrupo.modificarGrupo(grupoAux);
		this.adaptadorUsuario.modificarUsuario(usuarioActual);
		
		for (Contacto contacto : participantes) {
			if (contacto instanceof ContactoIndividual) {
				
				Usuario receptor = ControladorAppChat.getUnicaInstancia().catalogoUsuarios.getUsuario(((ContactoIndividual) contacto).getTelefono());
				Grupo grupoModificar=null;
				
				for (Grupo grupo : receptor.obtenerGrupos()) {
					if (grupo.getNombre().equals(grupoAux.getNombre())) {
						grupoModificar=grupo;
					}
				}
				
				if (grupoModificar!=null) {
					grupoModificar.eliminarMensajes();
					this.adaptadorGrupo.modificarGrupo(grupoModificar);
					this.adaptadorUsuario.modificarUsuario(receptor);
				}
			}
		}		
		return true;
		
	}
	
	
	
	// ESTADOS
	public void cambiarEstado(String imagen, String estadoText) {
		this.usuarioActual.setImagen(imagen);
		this.usuarioActual.setEstadoText(estadoText);
		this.adaptadorUsuario.modificarUsuario(usuarioActual);
	}
	
	public Map<Contacto,String> obtenerListaEstados(){
		return this.usuarioActual.obtenerContactoEstado();
	}
	
	public List<ContactoIndividual> obtenerContactos(){
		return this.usuarioActual.obtenerContactos();
	}
	

	// PREMIUM
	public void setPremium(boolean premium) {
		this.usuarioActual.setPremium(premium);
		this.adaptadorUsuario.modificarUsuario(usuarioActual);
	}
	
	
	// ESTADISTICAS
	public void crearDiagramaDeTarta(String Path, String formato) throws IOException {
		//String ruta = Path+"\\UsoGrupos.jpg";
		if (formato.equals("JPG")) {
			BitmapEncoder.saveJPGWithQuality(this.usuarioActual.makePieChart(), Path, 0.95f);
		}
		else {
			BitmapEncoder.saveBitmap(this.usuarioActual.makePieChart(), Path, BitmapFormat.PNG);
		}
	}
	
	public void crearHistograma(String Path, String formato) throws IOException {
		//String ruta = Path+"\\UsoGrupos.jpg";
		if (formato.equals("JPG")) {
			BitmapEncoder.saveJPGWithQuality(this.usuarioActual.makeHistogram(), Path, 0.95f);
		}
		else {
			BitmapEncoder.saveBitmap(this.usuarioActual.makeHistogram(), Path, BitmapFormat.PNG);
		}
		
	}
	
	public PieChart mostrarDiagramaDeTarta() throws IOException {
		return this.usuarioActual.makePieChart();
	    //new SwingWrapper<PieChart>(chart).displayChart()
	    
	}
	
	public CategoryChart mostrarHistograma() throws IOException {
		 return this.usuarioActual.makeHistogram();
		 //new SwingWrapper<CategoryChart>( this.usuarioActual.makeHistogram()).displayChartMatrix();
	}
	
	
	// EXPORTAR A PDF
	public boolean crearPdf(String ruta) {
		
		if(this.usuarioActual.isPremium()) {
			try {
				
				this.usuarioActual.generarPDF(ruta);
				return true;
				
			} catch (FileNotFoundException | DocumentException e) {
				return false;
			}
		}
		
		return false;
	}
	

	public List<Grupo> obtenerListaGruposAdmin(){
		return this.usuarioActual.obtenerListaGruposAdmin();
	}
	

	// COMPONENTE JAVA BEAN PARA IMPORTAR CHATS
	
	public void exportarChat(String rutaFichero, String plataforma, String formato) {
		this.cargadorMensajes.cargarMensajes(formato, rutaFichero, plataforma);
	}
	
	//public void enteradoCambioMensajes(EventObject arg0) {
	public void nuevosMensajes(EventObject arg0) {
		MensajesCargadosEvent ev = (MensajesCargadosEvent) arg0;
		List<Mensaje> mensajesUC = new LinkedList<>();
		// Check for Users
		ContactoIndividual conver = checkExport(this.usuarioActual,ev.getChatNuevo());
		if(conver != null) {
			//Swap format
			List<MensajeWhatsApp> mensajesWA = ev.getChatNuevo();
			for(MensajeWhatsApp mwa : mensajesWA) {
				int sender;
				ContactoIndividual senderC = this.usuarioActual.obtenerContactoIndv(mwa.getAutor());
				if ( senderC == null) sender = this.usuarioActual.getMovil();
				else {sender = senderC.getTelefono();}
				
				Mensaje mensaje = new Mensaje(mwa.getTexto(), new Date(), sender , Mensaje.EMOTICONO_VACIO);
				//Contacto contacto = (Contacto) sender;
				/*System.out.println(senderC.getNombre());
				senderC.enviarMensaje(mensaje);*/
				//this.adaptadorUsuario.modificarUsuario(usuarioActual);
				//this.adaptadorMensaje.registrarMensaje(mensaje);
				
				mensajesUC.add(mensaje);
			}
			/*mensajesUC.stream()
					  .forEach(m -> conver.anadirMensaje(m));*/
			
			for (Mensaje mensaje : mensajesUC) {
				conver.anadirMensaje(mensaje);
				AdaptadorMensaje.getUnicaInstancia().registrarMensaje(mensaje);
				//AdaptadorUsuario.getUnicaInstancia().mensajeRecibido(mensaje,CatalogoUsuarios.getUnicaInstancia().getUsuario(conver.getTelefono()));
				AdaptadorContactoIndividual.getUnicaInstancia().modificarContactoIndividual(conver);
			}	
		}
		else {
			System.out.println("Error, chat grupal, contacto no existe, usuario acutal no se encuentra en el chat");
		}
		
	}

	// Funcion auxiliar Componente Parser
	private ContactoIndividual checkExport(Usuario usuarioActual2, List<MensajeWhatsApp> mensajesWA) {
		ContactoIndividual conver = null; 
		for(MensajeWhatsApp mwa : mensajesWA) {
			
			@SuppressWarnings("unused")
			int sender;
			ContactoIndividual senderC = this.usuarioActual.obtenerContactoIndv(mwa.getAutor());
			if ( senderC == null) sender = this.usuarioActual.getMovil();
			else {
				sender = senderC.getTelefono();
				if(conver == null ) conver = senderC;
				if(!senderC.equals(conver)) return null;
				conver = senderC;
			}
		}
		return conver;
	}
}
