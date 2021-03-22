package modelo;

public interface Descuento {

	public  boolean isAplicable(Usuario usuario);
	public float Aplicar(float precio);
}
