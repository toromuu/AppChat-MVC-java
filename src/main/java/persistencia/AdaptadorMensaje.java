package persistencia;


import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import beans.Entidad;
import beans.Propiedad;

import modelo.Mensaje;

public class AdaptadorMensaje implements IAdaptadorMensajeDAO {

	private static ServicioPersistencia servPersistencia;
	private static AdaptadorMensaje unicaInstancia = null;
	//private DateFormat dateFormat;

	public static AdaptadorMensaje getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null) {
			return new AdaptadorMensaje();
		} else
			return unicaInstancia;
	}


	private AdaptadorMensaje() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		//dateFormat = new DateFormat("EEE MMM dd HH:mm:ss zzz yyyy",Locale.FRANCE);
		//Mon Mar 02 10:30:07 UTC 2020 -->dd-M-yyyy hh:mm:ss
		//dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
	}

	/* cuando se registra un Mensaje se le asigna un identificador unico */
	public void registrarMensaje(Mensaje mensaje) {
		Entidad eMensaje = null;
		// Si la entidad est� registrada no la registra de nuevo
		boolean existe = true;
		try {
			eMensaje = servPersistencia.recuperarEntidad(mensaje.getCodigo());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe)
			return;

		//System.out.println(String.valueOf(mensaje.getHora()));
		
		// crear entidad Mensaje
		eMensaje = new Entidad();
		eMensaje.setNombre("mensaje");
		eMensaje.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad("telefono", String.valueOf(mensaje.getTelefono())),
				new Propiedad("texto", mensaje.getTexto()),
				new Propiedad("emoticono", String.valueOf(mensaje.getEmoticono())),
				new Propiedad("fecha", String.valueOf(mensaje.getHora())))));

		// registrar entidad mensaje
		eMensaje = servPersistencia.registrarEntidad(eMensaje);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		mensaje.setCodigo(eMensaje.getId());
	}

	public void borrarMensaje(Mensaje mensaje) {
		Entidad eMensaje = servPersistencia.recuperarEntidad(mensaje.getCodigo());
		servPersistencia.borrarEntidad(eMensaje);
	}


	public Mensaje recuperarMensaje(int codigo) {
		Entidad eMensaje;
		String texto;
		Date fecha;
		int telefono, emoticono;
		
		eMensaje = servPersistencia.recuperarEntidad(codigo);
		telefono= Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, "telefono"));
		texto = servPersistencia.recuperarPropiedadEntidad(eMensaje, "texto");
		emoticono= Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, "emoticono"));
		
		try {
			//Los mensajes tienen que tener el siguiente formato para poder ordenarlos visualmente
			SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
			//Establece la zona horaria para que funcione el parseo de string a date
			formatter.setTimeZone(TimeZone.getTimeZone("CET"));
			String aux = servPersistencia.recuperarPropiedadEntidad(eMensaje, "fecha");
			fecha = formatter.parse(aux);
			
			
		} catch (ParseException e) {
			fecha = null;
		}

		Mensaje mssg = new Mensaje(texto, fecha, telefono, emoticono);
		mssg.setCodigo(codigo);
		return mssg;
	}

	public List<Mensaje> recuperarTodosMensajes() {
		List<Mensaje> mensajes = new LinkedList<Mensaje>();
		List<Entidad> entidades = servPersistencia.recuperarEntidades("mensaje");

		for (Entidad eMensaje : entidades) {
			mensajes.add(recuperarMensaje(eMensaje.getId()));
		}
		return mensajes;
	}


}
