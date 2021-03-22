package modelo;

import java.util.Date;

public class Mensaje {

	/*
	 *  Se usa para los mensajes normales, 
	 *  si el valor del emoticono es distinto de -1 , 
	 *  entonces el campo texto estara vacio y viceversa
	 */
	public final static int EMOTICONO_VACIO = -1;
	
	private int codigo;
	private Date hora;
	private String texto;
	private int telefono;
	public int emoticono;
	
	public Mensaje(String texto,  Date hora, int telefono, int emoticono) {
		this.codigo = 0;
		this.hora = hora;
		this.texto = texto;
		this.telefono = telefono;
		this.emoticono = emoticono;
	}
	
	
	// ------ Metodos Get-Set -------
	
	public int getEmoticono() {
		return emoticono;
	}

	public void setEmoticono(int emoticono) {
		this.emoticono = emoticono;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public int getCodigo() {
		return codigo;
	}

	public Date getHora() {
		return hora;
	}

	public String getTexto() {
		return texto;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	// ------ Funcionalidad ---------
	
	public boolean isEmoticono() {
		if(this.emoticono == Mensaje.EMOTICONO_VACIO) return false;
		return true;
	}

	
	// ToString()
	
	@Override
	public String toString() {
		return "Mensaje [hora=" + hora + ", texto=" + texto + ", telefono=" + telefono + ", emoticono=" + emoticono
				+ "]";
	}
	
	

}
