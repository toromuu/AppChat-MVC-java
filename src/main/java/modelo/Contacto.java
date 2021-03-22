package modelo;


import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;

/*
 * Clase abstracta que define el concepto de contacto
 * Un contacto se puede entender tanto como un contacto indivual 
 * como un grupo.
 * 
 */
public abstract class Contacto {

	private String nombre;
	private int codigo;
	public Contacto(String nombre) {
		this.nombre = nombre;
		codigo = 0;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Integer getCodigo() {
		return codigo;
	}
	
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	public abstract List<Mensaje> getMensajes();
	public abstract boolean puedeSerEliminado(Usuario usuario);
	public abstract void enviarMensaje(Mensaje mensaje);
	public abstract void eliminarMensajes();
	public abstract void escribirEnPdf(Document documento, String incioFrase) throws DocumentException;
	public int obtenerMensajesGrupo() { return -1; } // -1 si no es grupo
	public void formarListaGruposAdmin(List<Grupo> lista, int telf) { } 
	public abstract void registrarConversacion();
	
	
	@Override
	public String toString() {
		return this.nombre;
	}
}
