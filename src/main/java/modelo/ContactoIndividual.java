package modelo;


import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;

import persistencia.AdaptadorContactoIndividual;

import persistencia.AdaptadorMensaje;
import persistencia.AdaptadorUsuario;

public class ContactoIndividual extends Contacto{

	private int telefono;
	private List<Mensaje> mensajes;

	public ContactoIndividual(String nombre, int telefono) {
		super(nombre);
		this.setTelefono(telefono);
		this.mensajes = new LinkedList<Mensaje>();
	}

	@Override
	public List<Mensaje> getMensajes() {
		return this.mensajes.stream()
				.collect(Collectors.toList());
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}
	
	public void anadirMensaje(Mensaje mensaje) {
		this.mensajes.add(mensaje);
	}

	@Override
	public void registrarConversacion() {
		AdaptadorContactoIndividual.getUnicaInstancia().registrarContactoIndividual(this);
	}
	
	
	// ----- Funcionalidad -----
	
	public String obtenerEstado() {
		
	String estado = CatalogoUsuarios.getUnicaInstancia().getUsuario(this.telefono).getEstadoText();
	if(estado == null) return "No tiene Estado!";
	return estado;
		
	}

	@Override
	public boolean puedeSerEliminado(Usuario usuario) {
		return true;
	}

	/*
	 * Sirve para enviar un mensaje y registrarlo
	 * en la base de datos
	 */
	@Override
	public void enviarMensaje(Mensaje mensaje) {
		this.mensajes.add(mensaje);
		AdaptadorMensaje.getUnicaInstancia().registrarMensaje(mensaje);
		AdaptadorUsuario.getUnicaInstancia().mensajeRecibido(mensaje,CatalogoUsuarios.getUnicaInstancia().getUsuario(this.telefono));
		AdaptadorContactoIndividual.getUnicaInstancia().modificarContactoIndividual(this);
	}

	@Override
	public void eliminarMensajes() {
		this.mensajes = new LinkedList<Mensaje>();
		AdaptadorContactoIndividual.getUnicaInstancia().borrarMensajes(this);
		
	}
	
	/*
	 * Es llamado desde el metodo generarPDF de la clase Usuario
	 * Sirve para escribir en un documento con extension pdf
	 * la informacion del contacto actual
	 */
	@Override
	public void escribirEnPdf(Document documento, String inicio) throws DocumentException {
		 documento.add(new Paragraph(inicio + "Contacto: " + this.getNombre() + " " + this.telefono));
	}
	
}
