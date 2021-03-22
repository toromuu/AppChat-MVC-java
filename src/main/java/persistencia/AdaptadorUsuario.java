package persistencia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import beans.Entidad;
import beans.Propiedad;

import modelo.Usuario;
import modelo.CatalogoUsuarios;
import modelo.Contacto;
import modelo.ContactoIndividual;

import modelo.Grupo;
import modelo.Mensaje;


//Usa un pool para evitar problemas doble referencia con ventas

public class AdaptadorUsuario implements IAdaptadorUsuarioDAO {
	private static ServicioPersistencia servPersistencia;
	private SimpleDateFormat dateFormat;
	private static AdaptadorUsuario unicaInstancia = null;

	public static AdaptadorUsuario getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null)
			return new AdaptadorUsuario();
		else
			return unicaInstancia;
	}

	private AdaptadorUsuario() { 
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia(); 
		dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy",new Locale("CET"));
	}

	// ------- Funciones Auxiliares -------
	
	private String obtenerListaGrupos(Usuario usuario) {
		String grupos = "";
		for (Contacto v : usuario.getConversaciones()) {
			if(v instanceof Grupo ) {
				Grupo grupo = (Grupo) v;
				grupos += grupo.getCodigo() + " ";
			}
			
		}
		
		return grupos;
	}

	private String obtenerListaContactosIndividuales(Usuario usuario) {
		String contactosIndividuales = "";
		for (Contacto v : usuario.getConversaciones()) {
			if(v instanceof ContactoIndividual ) {
				
				ContactoIndividual c = (ContactoIndividual) v;
				contactosIndividuales += c.getCodigo() + " ";
			}
			
		}
		return contactosIndividuales;
	}
	
	// -------------------------------------
	
	/* cuando se registra un cliente se le asigna un identificador unico */
	public boolean registrarUsuario(Usuario usuario) {
		Entidad eUsuario;
		boolean existe = true; 
		
		// Si la entidad esta registrada no la registra de nuevo
		try {
			eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe) return false;

		// Registrar primero los atributos que son objetos
		
		String contactosIndividuales = obtenerListaContactosIndividuales(usuario);
		String grupos = obtenerListaContactosIndividuales(usuario);
		
		// crear entidad Cliente
		eUsuario = new Entidad();
		eUsuario.setNombre("usuario");
		
		eUsuario.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(
						new Propiedad("nombre", usuario.getNombre()),
						new Propiedad("email", usuario.getEmail()),
						new Propiedad("movil", usuario.getMovil().toString()),
						new Propiedad("contrasena", usuario.getContrasena()),
						new Propiedad("login", usuario.getLogin()),
						new Propiedad("fechaNacimiento", usuario.getFechaNacimiento().toString()),
						new Propiedad("premium", String.valueOf(usuario.isPremium())),
						new Propiedad("descuento", usuario.getDescuento()),
						new Propiedad("estadoText", usuario.getEstadoText()),
						new Propiedad("imagen", usuario.getImagen()),
						new Propiedad("ContatosIndividuales", contactosIndividuales),
						new Propiedad("Grupos", grupos))));
		
		// registrar entidad cliente
		eUsuario = servPersistencia.registrarEntidad(eUsuario);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		usuario.setCodigo(eUsuario.getId()); 
		return true;
	}

	public void borrarUsuario(Usuario usuario) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());
		servPersistencia.borrarEntidad(eUsuario);
	}

	public void modificarUsuario(Usuario usuario) {

		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());

		servPersistencia.eliminarPropiedadEntidad(eUsuario, "nombre");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "nombre", usuario.getNombre());
		
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "email");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "email", usuario.getEmail());
		
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "movil");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "movil", new Integer (usuario.getMovil()).toString());
		
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "contrasena");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "contrasena", usuario.getContrasena());
		
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "login");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "login", usuario.getLogin());
		
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "fechaNacimiento");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "fechaNacimiento", usuario.getFechaNacimiento().toString());
		
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "premium");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "premium", String.valueOf(usuario.isPremium()));
		
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "descuento");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "descuento", usuario.getDescuento());
		
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "estadoText");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "estadoText", usuario.getEstadoText());
		
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "imagen");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "imagen", usuario.getImagen());
		
		String contactosIndividuales = obtenerListaContactosIndividuales(usuario);
		String grupos = obtenerListaGrupos(usuario);
		
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "contactosIndv");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "contactosIndv", contactosIndividuales);
		
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "contactosGrupo");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "contactosGrupo", grupos);
		
	}

	public Usuario recuperarUsuario(int codigo) {
		//VEMOS SI YA HEMOS RECUPERADO EL USUARIO
		if (PoolDAO.getUnicaInstancia().contiene(codigo))
			return (Usuario) PoolDAO.getUnicaInstancia().getObjeto(codigo);
		
		Entidad usuarioEnt;
		boolean premium = false;
		String nombre, email, movil, contrasena, fechaNacimiento,login,contactosIndv,contactosGrupo, imagen, estadoText, descuento; // listas, recientes, filtro;
		List<Contacto> conversaciones = new LinkedList<Contacto>();
		

		usuarioEnt = servPersistencia.recuperarEntidad(codigo);
		//RECUPERAMOS LAS PROPIEDADES DEL USUARIO 
		nombre = servPersistencia.recuperarPropiedadEntidad(usuarioEnt, "nombre");
		email = servPersistencia.recuperarPropiedadEntidad(usuarioEnt, "email");
		contrasena = servPersistencia.recuperarPropiedadEntidad(usuarioEnt, "contrasena");
		fechaNacimiento = servPersistencia.recuperarPropiedadEntidad(usuarioEnt, "fechaNacimiento");
		premium = Boolean.parseBoolean(servPersistencia.recuperarPropiedadEntidad(usuarioEnt, "premium"));
		descuento = servPersistencia.recuperarPropiedadEntidad(usuarioEnt, "descuento");
		movil = servPersistencia.recuperarPropiedadEntidad(usuarioEnt, "movil");
		login = servPersistencia.recuperarPropiedadEntidad(usuarioEnt, "login");
		imagen = servPersistencia.recuperarPropiedadEntidad(usuarioEnt, "imagen");
		estadoText = servPersistencia.recuperarPropiedadEntidad(usuarioEnt, "estadoText");
		contactosIndv = servPersistencia.recuperarPropiedadEntidad(usuarioEnt, "contactosIndv");
		contactosGrupo = servPersistencia.recuperarPropiedadEntidad(usuarioEnt, "contactosGrupo");
		
		//RECUPERAMOS LAS LISTAS
		if(contactosIndv == null) contactosIndv = "";
		StringTokenizer tokenizer = new StringTokenizer(contactosIndv, " ");
		while (tokenizer.hasMoreTokens()) {

			String contactoIndvActual = tokenizer.nextToken();
			ContactoIndividual contactoIndv = AdaptadorContactoIndividual.getUnicaInstancia()
					.recuperarContactoIndividual(Integer.parseInt(contactoIndvActual));
			conversaciones.add(contactoIndv);
		}
		if(contactosGrupo == null) contactosGrupo = "";
		tokenizer = new StringTokenizer(contactosGrupo, " ");
		while (tokenizer.hasMoreTokens()) {

			String contactoGrupoActual = tokenizer.nextToken();
			Grupo contactoGrupo = AdaptadorGrupo.getUnicaInstancia()
					.recuperarGrupo(Integer.parseInt(contactoGrupoActual));
			conversaciones.add(contactoGrupo);
		}
		
		//CREAMOS UN NUEVO OBJETO USUARIO DEL MODELO
		Date fechaNacimentoDate;
		try {
			fechaNacimentoDate =this.dateFormat.parse(fechaNacimiento);
		} catch (ParseException e) {
			fechaNacimentoDate = null;
			System.out.println("Error al Recuperar Fecha " + fechaNacimiento);
		}
		Usuario usuario = new Usuario(nombre, fechaNacimentoDate, email, Integer.parseInt(movil), login, contrasena,conversaciones,imagen,estadoText,premium,descuento);

		usuario.setCodigo(codigo);

		PoolDAO.getUnicaInstancia().addObjeto(codigo, usuario);

		return usuario;
	}

	public List<Usuario> recuperarTodosUsuarios() {
		List<Entidad> usuariosEntidad = servPersistencia.recuperarEntidades("usuario");
		List<Usuario> usuarios = new LinkedList<Usuario>();
		for (Entidad usuarioActual : usuariosEntidad) {
			usuarios.add(recuperarUsuario(usuarioActual.getId()));
		}
		return usuarios;
	}

	public void setPremium(Usuario usuarioActual, boolean premium) {
		// TODO Auto-generated method stub
	}

	public void cambiarDescuento(String nombre, Usuario usuarioActual) {
		// TODO Auto-generated method stub
	}

	public void mensajeRecibido(Mensaje mensaje, Usuario receptor) {
		
		// OJO Se debe duplicar el mensaje para que el emisor y receptor no tengan el mismo elemento en la bbbdd
		
		Mensaje mssgCopia = new Mensaje(mensaje.getTexto(), mensaje.getHora(), mensaje.getTelefono(), mensaje.getEmoticono());
		AdaptadorMensaje.getUnicaInstancia().registrarMensaje(mssgCopia);

		Usuario emisor = CatalogoUsuarios.getUnicaInstancia().getUsuario(mssgCopia.getTelefono()); 
		if(receptor.checkUsuario(emisor)){
			ContactoIndividual ContactoIndv = receptor.obtenerContactoIndv(emisor.getMovil());
			ContactoIndv.anadirMensaje(mssgCopia);
			AdaptadorContactoIndividual.getUnicaInstancia().modificarContactoIndividual(ContactoIndv);
			AdaptadorUsuario.getUnicaInstancia().modificarUsuario(receptor);
		}
		else {
			ContactoIndividual nuevoContacto = new ContactoIndividual(emisor.getMovil().toString(), emisor.getMovil());
			nuevoContacto.anadirMensaje(mssgCopia);
			receptor.anadirConversacion(nuevoContacto);
			AdaptadorContactoIndividual.getUnicaInstancia().modificarContactoIndividual(nuevoContacto);
			AdaptadorUsuario.getUnicaInstancia().modificarUsuario(receptor);
			
		}
		
	}
}
