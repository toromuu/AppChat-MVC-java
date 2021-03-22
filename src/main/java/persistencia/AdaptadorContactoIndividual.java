package persistencia;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;
import java.util.StringTokenizer;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import beans.Entidad;
import beans.Propiedad;

import modelo.ContactoIndividual;

import modelo.Mensaje;

public class AdaptadorContactoIndividual implements IAdaptadorContactoIndividualDAO {
	
	// Usa un pool para evitar problemas doble referencia con cliente
	private static ServicioPersistencia servPersistencia;
	private SimpleDateFormat dateFormat;
	private static AdaptadorContactoIndividual unicaInstancia;

	public static AdaptadorContactoIndividual getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null)
			return new AdaptadorContactoIndividual();
		else
			return unicaInstancia;
	}

	private AdaptadorContactoIndividual() { 
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		this.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
	}


	public void registrarContactoIndividual(ContactoIndividual contacto){
		Entidad eContacto;
		boolean existe = true; 
		try {
			eContacto = servPersistencia.recuperarEntidad(contacto.getCodigo());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe) return;

		// ---- Propiedades ------
		// - Nombre (en Contacto)
		// - Telefono
		// - Mensajes (Lista de Objetos)
		
		
		// Registrar primero los atributos que son objetos
		String mensajes = obtenerListaMensajesCodigo(contacto.getMensajes());
		
		// Registrar campos porimitivos

		// Crear entidad Contacto
		eContacto = new Entidad();

		eContacto.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(
						new Propiedad("nombre", contacto.getNombre()),
						new Propiedad("mensajes", mensajes),
						new Propiedad("telefono", new Integer(contacto.getTelefono()).toString()))));

		eContacto = servPersistencia.registrarEntidad(eContacto);
		contacto.setCodigo(eContacto.getId()); 	
	}

	public void borrarContactoIndividual(ContactoIndividual contacto) {
		Entidad eContacto;
		AdaptadorMensaje adaptadorMensaje = AdaptadorMensaje.getUnicaInstancia();

		for (Mensaje mensaje: contacto.getMensajes()) {
			adaptadorMensaje.borrarMensaje(mensaje);
		}
		eContacto = servPersistencia.recuperarEntidad(contacto.getCodigo());
		servPersistencia.borrarEntidad(eContacto);

	}

	public void modificarContactoIndividual(ContactoIndividual contacto) {
		
		Entidad eContacto;
		eContacto = servPersistencia.recuperarEntidad(contacto.getCodigo());
		
		servPersistencia.eliminarPropiedadEntidad(eContacto, "nombre");
		servPersistencia.anadirPropiedadEntidad(eContacto, "nombre", contacto.getNombre());
		
		servPersistencia.eliminarPropiedadEntidad(eContacto, "telefono");
		servPersistencia.anadirPropiedadEntidad(eContacto, "telefono", String.valueOf(contacto.getTelefono()));

		String mensajes = obtenerListaMensajesCodigo(contacto.getMensajes());
		servPersistencia.eliminarPropiedadEntidad(eContacto, "mensajes");
		servPersistencia.anadirPropiedadEntidad(eContacto, "mensajes", mensajes);

	}

	public ContactoIndividual recuperarContactoIndividual(int codigo) {
		// Si la entidad est� en el pool la devuelve directamente
		if (PoolDAO.getUnicaInstancia().contiene(codigo))
			return (ContactoIndividual) PoolDAO.getUnicaInstancia().getObjeto(codigo);

		// si no, la recupera de la base de datos
		// Recuperar entidad
		Entidad eContactoIndv = servPersistencia.recuperarEntidad(codigo);

		// Recuperar propiedades que no son objetos
		String nombre,mensajeCodigos;
		int telefono;
		nombre = servPersistencia.recuperarPropiedadEntidad(eContactoIndv, "nombre");
		telefono = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eContactoIndv, "telefono"));
		mensajeCodigos = servPersistencia.recuperarPropiedadEntidad(eContactoIndv, "mensajes");
		
		ContactoIndividual contactoIndv = new ContactoIndividual(nombre, telefono);
		contactoIndv.setCodigo(codigo);
		PoolDAO.getUnicaInstancia().addObjeto(codigo, contactoIndv);

		// recuperar propiedades que son objetos llamando a adaptadores
		// Lista de Mensajes
		StringTokenizer tokenizer = new StringTokenizer(mensajeCodigos, " ");
		AdaptadorMensaje mensajeAdaptador = AdaptadorMensaje.getUnicaInstancia();
		Mensaje mensajeActualM;
		while (tokenizer.hasMoreTokens()) {

			String mensajeActual = tokenizer.nextToken();
		
			mensajeActualM = mensajeAdaptador.recuperarMensaje((Integer.parseInt(mensajeActual)));
			contactoIndv.anadirMensaje(mensajeActualM);
		}
		

		// devolver el objeto contacto
		return contactoIndv;
	}

	// -------------------Funciones auxiliares-----------------------------
	private String obtenerListaMensajesCodigo(List<Mensaje> mensajes) {
		String lineas = "";
		for (Mensaje mensaje : mensajes) {
			lineas += mensaje.getCodigo() + " ";
		}
		return lineas.trim();

	}

	@Override
	public void borrarMensajes(ContactoIndividual contactoIndividual) {
		
		AdaptadorMensaje adaptadorMensaje = AdaptadorMensaje.getUnicaInstancia();
		for (Mensaje mensaje: contactoIndividual.getMensajes()) {
			adaptadorMensaje.borrarMensaje(mensaje);
		}
		
		Entidad eContacto;
		eContacto = servPersistencia.recuperarEntidad(contactoIndividual.getCodigo());
		
		String mensajes = obtenerListaMensajesCodigo(contactoIndividual.getMensajes());
		servPersistencia.eliminarPropiedadEntidad(eContacto, "mensajes");
		servPersistencia.anadirPropiedadEntidad(eContacto, "mensajes", mensajes);
		
		
	}

	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}
}
