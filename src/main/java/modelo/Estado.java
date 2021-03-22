package modelo;

public class Estado {
	private int codigo; 
	private String mensaje;
	//private Imagen imagen;
	
	public Estado(String mensaje) {
		setCodigo(0);
		this.mensaje = mensaje;
		//imagen
	}
	
	public String getMensaje() {
		return this.mensaje;
	}
	
	void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
}
