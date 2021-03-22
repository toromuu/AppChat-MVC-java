package modelo;

import java.util.Date;
import java.util.function.Predicate;

public class DescuentoTerceraEdad implements Descuento{

	// ----- Singleton ------
	private static DescuentoTerceraEdad unicaInstancia = new DescuentoTerceraEdad();
	private String nombreDescuento;
	private DescuentoTerceraEdad() {
		this.nombreDescuento = "DescuentoTerceraEdad";
	};
	
	public static DescuentoTerceraEdad getUnicaInstancia() {
		
		if( unicaInstancia == null ) {
			return unicaInstancia = new DescuentoTerceraEdad();
		}
		return unicaInstancia;
	}
	
	// ----------------------
	
	// Descuento para Personas nacidas antes de 1970
	private static final Predicate<Usuario> CONDICION = u -> u.getFechaNacimiento().before(new Date(0));
	private static final float DESCUENTO = 0.6f;


	// 40% de descuento
	@Override
	public float Aplicar(float precio) {
		return precio*DESCUENTO;
	}

	//@Override
	public  boolean isAplicable(modelo.Usuario usuario) {
		return CONDICION.test(usuario);
	}

	public String getNombreDescuento() {
		return nombreDescuento;
	}

}
