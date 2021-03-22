package modelo;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;


import persistencia.AdaptadorGrupo;
import persistencia.AdaptadorMensaje;

public class Grupo extends Contacto{

	private List<Contacto> participantes;
	private List<Mensaje> mensajes;
	private int telefonoAdmin;
	private Usuario admin;
	
	public Grupo(String nombre, int telefonoAdmin) {
		this(nombre, telefonoAdmin, new LinkedList<Contacto>());
	}
	
	public Grupo(String nombre, int telefonoAdmin, List<Contacto> participantes) {
		super(nombre);
		this.admin = null;
		this.telefonoAdmin = telefonoAdmin;
		this.participantes = new LinkedList<Contacto>(participantes);
		this.mensajes = new LinkedList<Mensaje>();
	}
	
	public void añadirContacto(Contacto contacto) {
		this.participantes.add(contacto);
		AdaptadorGrupo.getUnicaInstancia().modificarGrupo(this);
	}

	// ----- Funcionalidad -----
	
	@Override
	public List<Mensaje> getMensajes() {
		return this.mensajes.stream()
				.sorted(Comparator.comparing(Mensaje::getHora))
				.collect(Collectors.toList());			
	}
	
	public Usuario getAdmin(int telefono) {
		if(admin == null) {
			this.admin = CatalogoUsuarios.getUnicaInstancia().getUsuario(telefono);
		}
		return admin;
		
	}
	
	public void modificarParticipantes(List<Contacto> participantes) {
		this.participantes = new LinkedList<Contacto>(participantes);

	}
	
	/*
	 * Sirve para comprobar el status del usuario en el grupo
	 * y por tanto los permisos de los que dispone para modificar 
	 * el grupo
	 */
	@Override
	public boolean puedeSerEliminado(Usuario usuario) {
		if(this.getAdmin(this.telefonoAdmin).equals(usuario)) {
			return true;
		}
		return false;
	}

	/*
	 * Sirve para enviar un mensaje a todos los participantes del grupo
	 */
	@Override
	public void enviarMensaje(Mensaje mensaje) {
		this.participantes.stream()
				.forEach(c -> c.enviarMensaje(mensaje));
		this.mensajes.add(mensaje);
		AdaptadorMensaje.getUnicaInstancia().registrarMensaje(mensaje);
		AdaptadorGrupo.getUnicaInstancia().modificarGrupo(this);
		//System.out.println("Se envia el mensaje" + mensaje.getTexto());
		//System.out.println("A los siguientes contactos: "+ participantes.toString());
	}
	
	@Override
	public void eliminarMensajes() {
		this.mensajes = new LinkedList<>();
		AdaptadorGrupo.getUnicaInstancia().borrarMensajes(this);
		
	}
	
	/*
	 * Es llamado desde el metodo generarPDF de la clase Usuario
	 * Sirve para escribir en un documento con extension pdf
	 * la informacion del grupo actual
	 */
	@Override
	public void escribirEnPdf(Document documento, String incioFrase) throws DocumentException {
		 documento.add(new Paragraph("inicio" + "Grupo: " + this.getNombre() +".\n" + "Participantes:"));
		 this.participantes.stream()
		 		.forEach(c -> {
					try {
						//System.out.println(c.getNombre());
						c.escribirEnPdf(documento, "\t-");
					} catch (DocumentException e) {
						System.out.println("Error al generar pdf");
					}
				}); 		
	}
	
	@Override
	public int obtenerMensajesGrupo() {
		return this.mensajes.size();
	}
	
	@Override
	public void registrarConversacion() {
		AdaptadorGrupo.getUnicaInstancia().registrarGrupo(this);
		
	}
	
	@Override
	public void formarListaGruposAdmin(List<Grupo> lista, int telf) {
		if(this.telefonoAdmin == telf) {
			lista.add(this);
		}
		
	}
	
	// ----- Getters And Setters -----
	
	public int getTelefonoAdmin() {
		return telefonoAdmin;
	}

	public void setTelefonoAdmin(int telefonoAdmin) {
		this.telefonoAdmin = telefonoAdmin;
	}

	public void setAdmin(Usuario admin) {
		this.admin = admin;
	}

	public List<Contacto> getParticipantes() {
		return new LinkedList<Contacto>(participantes);
	}

	public void anadirMensaje(Mensaje mensaje) {
		this.mensajes.add(mensaje);
	}

	@Override
	public String toString() {
		return super.toString();
	}
	
}
