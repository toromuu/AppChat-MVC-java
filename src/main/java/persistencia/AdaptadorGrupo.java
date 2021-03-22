package persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import beans.Entidad;
import beans.Propiedad;
import modelo.Contacto;

import modelo.Grupo;
import modelo.Mensaje;

public class AdaptadorGrupo implements IAdaptadorGrupo {

	private static ServicioPersistencia servPersistencia;
	private static AdaptadorGrupo unicaInstancia;

	public static AdaptadorGrupo getUnicaInstancia() { // patron
														// singleton
		if (unicaInstancia == null)
			return new AdaptadorGrupo();
		else
			return unicaInstancia;
	}

	private AdaptadorGrupo() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	/*
	 * cuando se registra un Grupo se le asigna un identificador unico
	 */
	public void registrarGrupo(Grupo grupo) {
		Entidad eGrupo;
		// Si la entidad esta registrada no la registra de nuevo
		boolean existe = true;
		try {
			eGrupo = servPersistencia.recuperarEntidad(grupo.getCodigo());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe)
			return;

		// ---- Propiedades ------
		// - Nombre (en Contacto)
		// - TelefonoAdmi
		// - Admin (NO se recupera, OJO)
		// - participantes (Lista de Objetos)
		// - Mensajes (Lista de Objetos

		// Registrar primero los atributos que son objetos
		String contactos = obtenerListaParticipantesCodigo(grupo.getParticipantes());
		//System.out.println("Contactos " + contactos + grupo.getParticipantes());
		String mensajes = obtenerListaMensajesCodigo(grupo.getMensajes());
		// Registrar campos porimitivos

		// Crear entidad Contacto
		eGrupo = new Entidad();

		eGrupo.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad("nombre", grupo.getNombre()),
				new Propiedad("telefonoAdmin", new Integer(grupo.getTelefonoAdmin()).toString()),
				new Propiedad("mensajes", mensajes), new Propiedad("participantes", contactos))));
		// registrar entidad grupo
		eGrupo = servPersistencia.registrarEntidad(eGrupo);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		grupo.setCodigo(eGrupo.getId());
	}

	public void borrarGrupo(Grupo grupo) {
		// No se borran los contactos, OJO
		AdaptadorMensaje adaptadorMensaje = AdaptadorMensaje.getUnicaInstancia();

		for (Mensaje mensaje : grupo.getMensajes()) {
			if (mensaje!=null) {
				adaptadorMensaje.borrarMensaje(mensaje);
			}
			
		}
		Entidad eGrupo = servPersistencia.recuperarEntidad(grupo.getCodigo());
		servPersistencia.borrarEntidad(eGrupo);
	}

	public Grupo recuperarGrupo(int codigo) {
		// Si la entidad est� en el pool la devuelve directamente
		if (PoolDAO.getUnicaInstancia().contiene(codigo))
			return (Grupo) PoolDAO.getUnicaInstancia().getObjeto(codigo);

		// si no, la recupera de la base de datos
		// Recuperar entidad
		Entidad eContactoIndv = servPersistencia.recuperarEntidad(codigo);

		// Recuperar propiedades que no son objetos
		String nombre, contactoCodigos, mensajeCodigos;
		int telefono;
		nombre = servPersistencia.recuperarPropiedadEntidad(eContactoIndv, "nombre");
		telefono = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eContactoIndv, "telefonoAdmin"));
		mensajeCodigos = servPersistencia.recuperarPropiedadEntidad(eContactoIndv, "mensajes");
		contactoCodigos = servPersistencia.recuperarPropiedadEntidad(eContactoIndv, "participantes");
		

		// recuperar propiedades que son objetos llamando a adaptadores
		// Lista de Mensajes
		StringTokenizer tokenizer = new StringTokenizer(contactoCodigos, " ");
		AdaptadorContactoIndividual ContactoAdaptador = AdaptadorContactoIndividual.getUnicaInstancia();
		List<Contacto> participantes = new LinkedList<Contacto>();
		while (tokenizer.hasMoreTokens()) {
			String ContactoActual = tokenizer.nextToken();
			participantes.add(ContactoAdaptador.recuperarContactoIndividual((Integer.parseInt(ContactoActual))));
		}

		Grupo grupo = new Grupo(nombre, telefono, participantes);
		grupo.setCodigo(codigo);

		StringTokenizer tokenizer2 = new StringTokenizer(mensajeCodigos, " ");
		AdaptadorMensaje mensajeAdaptador = AdaptadorMensaje.getUnicaInstancia();
		Mensaje mensajeActualM;
		while (tokenizer2.hasMoreTokens()) {

			String mensajeActual = tokenizer2.nextToken();

			mensajeActualM = mensajeAdaptador.recuperarMensaje((Integer.parseInt(mensajeActual)));
			grupo.anadirMensaje(mensajeActualM);
		}

		// IMPORTANTE:a�adir la venta al pool antes de llamar a otros
		PoolDAO.getUnicaInstancia().addObjeto(codigo, grupo);

		// devolver el objeto contacto
		return grupo;
	}

	@Override
	public void modificarGrupo(Grupo grupo) {
		Entidad eGrupo;
		eGrupo = servPersistencia.recuperarEntidad(grupo.getCodigo());

		servPersistencia.eliminarPropiedadEntidad(eGrupo, "nombre");
		servPersistencia.anadirPropiedadEntidad(eGrupo, "nombre", grupo.getNombre());

		servPersistencia.eliminarPropiedadEntidad(eGrupo, "telefonoAdmin");
		servPersistencia.anadirPropiedadEntidad(eGrupo, "telefonoAdmin", String.valueOf(grupo.getTelefonoAdmin()));

		String participantes = obtenerListaParticipantesCodigo(grupo.getParticipantes());
		servPersistencia.eliminarPropiedadEntidad(eGrupo, "participantes");
		servPersistencia.anadirPropiedadEntidad(eGrupo, "participantes", participantes);

		String mensajes = obtenerListaMensajesCodigo(grupo.getMensajes());
		servPersistencia.eliminarPropiedadEntidad(eGrupo, "mensajes");
		servPersistencia.anadirPropiedadEntidad(eGrupo, "mensajes", mensajes);

	}
	
	@Override
	public void borrarMensajes(Grupo grupo) {
		
		AdaptadorMensaje adaptadorMensaje = AdaptadorMensaje.getUnicaInstancia();
		for (Mensaje mensaje: grupo.getMensajes()) {
			adaptadorMensaje.borrarMensaje(mensaje);
		}
		
		Entidad eContacto;
		eContacto = servPersistencia.recuperarEntidad(grupo.getCodigo());
		
		String mensajes = obtenerListaMensajesCodigo(grupo.getMensajes());
		servPersistencia.eliminarPropiedadEntidad(eContacto, "mensajes");
		servPersistencia.anadirPropiedadEntidad(eContacto, "mensajes", mensajes);
		
		
	}

	// ------ Funciones Auxiliares ------

	private String obtenerListaParticipantesCodigo(List<Contacto> participantes) {
		String lineas = "";
		for (Contacto contacto : participantes) {
			lineas += contacto.getCodigo() + " ";
		}
		return lineas.trim();

	}

	private String obtenerListaMensajesCodigo(List<Mensaje> mensajes) {
		String lineas = "";
		for (Mensaje mensaje : mensajes) {
			lineas += mensaje.getCodigo() + " ";
		}
		return lineas.trim();

	}

	// ----------------------------------

}
