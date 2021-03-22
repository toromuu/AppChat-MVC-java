package modelo;

import java.time.ZonedDateTime;

import java.util.Date;

import java.util.stream.Collectors;

public class DescuentoMensajesUltimoMes implements Descuento{

	// ----- Singleton ------
	private static DescuentoMensajesUltimoMes unicaInstancia = new DescuentoMensajesUltimoMes();
	private String nombreDescuento;
	private DescuentoMensajesUltimoMes() {
		this.nombreDescuento = "DescuentoMensajesUltimoMes";
	};
	
	public static DescuentoMensajesUltimoMes getUnicaInstancia() {
		
		if( unicaInstancia == null ) {
			return unicaInstancia = new DescuentoMensajesUltimoMes();
		}
		return unicaInstancia;
	}
	
	// ----------------------
	
	// Descuento para Personas con X mensajes el ultimo mes
	//private static final Predicate<Usuario> CONDICION = u -> u.getConversaciones().);
	private static final float DESCUENTO = 0.8f;
	private static final Date FECHAACTUAL = Date.from(ZonedDateTime.now().toInstant());
	private static final Date MESANTERIO = Date.from(ZonedDateTime.now().minusMonths(1).toInstant());
	private static final int NUMEROMENSAJESPARADESCUENTO = 10;

	// 30% de descuento
	@Override
	public float Aplicar(float precio) {
		return precio*DESCUENTO;
	}

	//@Override
	public  boolean isAplicable(modelo.Usuario usuario) {

		int nmensajesMes = usuario.getConversaciones().stream()    //Coge todas las conversaciones (lista de Contactos que le han hablado)
				.map(c -> c.getMensajes())        // Para cada conversacion obtiene su lista de mensajes   
				.flatMap(listam -> listam.stream()) // Para esos mensajes
				.filter(m -> m.getHora().after(MESANTERIO) &&  m.getHora().before(FECHAACTUAL) )
				.collect(Collectors.toList())
				.size();
		
		if (nmensajesMes >= NUMEROMENSAJESPARADESCUENTO) {
			return true;
		}		
		return false;
	}

	public String getNombreDescuento() {
		return nombreDescuento;
	}

}
