package modelo;

import java.awt.Color;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import controlador.ControladorAppChat;


import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;


import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;

public class Usuario {

	private int codigo;
	private String nombre;
	private Date fechaNacimiento;
	private String email;
	private int movil;
	private String login;
	private String contrasena;
	private boolean premium;
	private String descuento;
	private List<Contacto> conversaciones;
	
	// ----- Estado -----
	private String imagen;
	private String estadoText;

	public Usuario(String nombre, Date fechaNacimiento, String email, int movil, String login, String contrasena) {
		this(nombre, fechaNacimiento, email, movil, login, contrasena, new LinkedList<Contacto>(), null, null, false,"");
	}
	

	public Usuario(String nombre, Date fechaNacimiento, String email, int movil, String login, String contrasena, List<Contacto> conversaciones, String imagen, String estadoText,Boolean premium,String descuento) {
		codigo = 0;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.email = email;
		this.movil = movil;
		this.login = login;
		this.contrasena = contrasena;
		this.premium = premium;
		this.descuento = descuento;
		this.conversaciones = new LinkedList<Contacto>(conversaciones);
		this.imagen = imagen;
		this.estadoText = estadoText;
		
	}

	// ----- Funcionalidad -----
	

	public void anadirConversacion(Contacto conversacion) {
		this.conversaciones.add(conversacion);
		conversacion.registrarConversacion();
	}
	
	public Map<Contacto,String> obtenerContactoEstado(){
		
		HashMap<Contacto,String> mapa = new HashMap<Contacto,String>();
		this.conversaciones.stream()
				.filter(c -> c instanceof ContactoIndividual)
				.map(c -> (ContactoIndividual) c)
				.forEach(ci -> mapa.put(ci, ci.obtenerEstado()));
							   
		return mapa;
	}
	
	public List<ContactoIndividual> obtenerContactos(){
		return this.conversaciones.stream()
				.filter(c -> c instanceof ContactoIndividual)
				.map(c -> (ContactoIndividual) c)
				.collect(Collectors.toList());
	}
	
	public List<Grupo> obtenerGrupos(){
		return this.conversaciones.stream()
				.filter(c -> c instanceof Grupo)
				.map(c -> (Grupo) c)
				.collect(Collectors.toList());
	}
	
	public Grupo grupoRegistrado(String nombreGrupo) {

		List<Grupo> grupos = this.obtenerGrupos();
		for (Grupo contacto : grupos) {
			if(contacto.getNombre().equals(nombreGrupo)) {
				return contacto;
			}
		}
		return null;
			
	}
	
	public boolean eliminarContacto(Contacto contacto) {
			return this.conversaciones.remove(contacto);
	}
	
	/*
	 * Sirve para comprobar si el usuario actual tiene agregado como contacto 
	 * el usuario asociado pasado como parametro
	 */
	public boolean checkUsuario(Usuario usuario) {
		
		boolean result = false;
		result = this.conversaciones.stream()
				.filter(c -> c instanceof ContactoIndividual)
				.map(c -> (ContactoIndividual) c)
				.anyMatch(ci -> ci.getTelefono() == usuario.getMovil());
		return result;
	}
	
	/*
	 * Sirve para obtener un contacto individual a partir de un numero de telefono
	 */
	public ContactoIndividual obtenerContactoIndv(int telf) {
		
		Optional<ContactoIndividual> contactoIndv = null;
		contactoIndv = this.conversaciones.stream()
				.filter(c -> c instanceof ContactoIndividual)
				.map(c -> (ContactoIndividual) c)
				.filter(ci -> ci.getTelefono() == telf)
				.findFirst();
			
		if(contactoIndv.isPresent()) return contactoIndv.get();
		return null;
	}
	
	/*
	 * Sirve para obtener un contacto individual a partir de un nombre
	 */
	public ContactoIndividual obtenerContactoIndv(String nombre) {
		Optional<ContactoIndividual> contactoIndv = null;
		contactoIndv = this.conversaciones.stream()
				.filter(c -> c instanceof ContactoIndividual)
				.map(c -> (ContactoIndividual) c)
				.filter(ci -> ci.getNombre().equals(nombre))
				.findFirst();
			
		if(contactoIndv.isPresent()) return contactoIndv.get();
		return null;
	}
	
	
	/*
	 * Sirve para buscar un mensaje en la lista de mensajes del contacto
	 * segun un predicado (condicion) determinada
	 */
	public List<Mensaje> buscarMensajes(Predicate<Contacto> prContactos, List<Predicate<Mensaje>> listaPrMensaje) {
		// c -> instanceof(Grupo) // m -> m.getText().constains("CLave") // m -> m.getFecha().equals(fecha)
		
		return this.conversaciones.stream()       //Coge todas las conversaciones (lista de Contactos que le han hablado)
				.filter(c -> prContactos.test(c)) // Las filtra por el predicado de ContactoIndividual, Grupo o Ambos
				.map(c -> c.getMensajes())        // Para cada conversacion obtiene su lista de mensajes   
				.flatMap(listam -> listam.stream()) // Junta todas estas listas
				.filter(m -> listaPrMensaje.stream() // Obtiene los dos filtros del Segundo Predicado
						.map(listam -> listam.test(m)) //Filtra los mensajes
						.filter(b-> b == false)  //Descartar los que no cumplen las condiciones
						/*.map(pr -> pr.test(m))*/
					    .collect(Collectors.toList()).isEmpty()) //Recoge los que cumplen la condicion
				.sorted(Comparator.comparing(Mensaje::getHora)) // Los ordena segun la fecha
				.collect(Collectors.toList());
	}
	
	/*
	 * Sirve para generar un documento con extension pdf
	 * con la informacion de todos los contactos 
	 */
	public void generarPDF(String ruta) throws FileNotFoundException, DocumentException {
		//File file = new File(ruta);
		FileOutputStream archivo = new FileOutputStream(ruta+"\\Lista_De_Contactos_De_"+ControladorAppChat.getUnicaInstancia().getUsuarioActual().getNombre() + ".pdf");
	    Document documento = new Document();
	    PdfWriter.getInstance(documento, archivo);
	    
	    documento.open();
	    documento.add(new Paragraph("Documento con mensajes de AppChat"));
	    this.conversaciones.stream()
	    		.forEach(c -> {
					try {
						//Tanto contacto indivual como grupo tienen su propia version del metodo
						c.escribirEnPdf(documento,"");
					} catch (DocumentException e) {
						System.out.println("Error al generar PDF");
					}
				});
	    documento.add(new Paragraph("Fin del Documento"));
	    documento.close();
	}
	
	
	/*
	 * Sirve para crear un diagrama de tarta
	 * que representa la cantidad de los mensajes en los 6 grupos 
	 * más significativos del Usuario
	 */
	public PieChart makePieChart(){
		
		// Create Chart
	    PieChart chart = new PieChartBuilder().width(800).height(800).title("Mensajes en los grupos más significativos del Usuario").build();
	 
	    // Customize Chart
	    Color[] sliceColors = new Color[] { new Color(224, 68, 14), new Color(230, 105, 62), new Color(236, 143, 110), new Color(243, 180, 159), new Color(246, 199, 182) };
	    chart.getStyler().setSeriesColors(sliceColors);
	    
	    List<Contacto> grupos = this.conversaciones.stream()
	    		.filter(c -> c.obtenerMensajesGrupo() >= 0)
	    		.sorted(Comparator.comparing(Contacto::obtenerMensajesGrupo).reversed())
	    		.collect(Collectors.toList());
	    
	    List<List<Object>> gruposInfo = new LinkedList<List<Object>>();
	    for( Contacto grupo : grupos) {
	    	
	    	Integer numMensajes = grupo.obtenerMensajesGrupo();
	    	String nombre = grupo.getNombre();
	    	
	    	List<Object> grupoInfo = new LinkedList<Object>();
	    	
	    	grupoInfo.add(nombre);	
	    	grupoInfo.add(numMensajes);
	    	gruposInfo.add(grupoInfo);
	    }
	    int i = 0;
	    while(true) {
	    	if(i == 6 || gruposInfo.isEmpty()) break;
	    	
	    	chart.addSeries( (String)gruposInfo.get(0).get(0), (Integer)gruposInfo.get(0).get(1));
	    	gruposInfo.remove(0);
	    	i++;
	    }
	    return chart;
	}

	/*
	 * Sirve para crear un histograma del numero de mensajes
	 * enviados cada mes por el usuario en este año actual
	 */
	@SuppressWarnings("deprecation")
	public CategoryChart makeHistogram() {
		CategoryChart chart = new CategoryChartBuilder().width(800).height(800).title("Histograma de Mensajes").xAxisTitle("Mes").yAxisTitle("Numero").build();
		List<Mensaje> mensajesAnoActual = this.conversaciones.stream()
				.flatMap(c -> c.getMensajes().stream())
				.filter(m -> (m.getHora().getYear()+1900) == LocalDate.now().getYear())
				.collect(Collectors.toList());
		
		List<Integer> mensajesPorMes = new LinkedList<>();
		Integer numMensajes = 0;
		
		for(int i = 0; i <= 11; i++) {
			for(Mensaje m : mensajesAnoActual) {
					if(m.getHora().getMonth() == i) numMensajes++;
			}
			mensajesPorMes.add(numMensajes);
			numMensajes = 0;
			
		}
		chart.addSeries("Mensajes de Este Ano",  Arrays.asList(new Integer[] { 1, 2, 3, 4 ,5 ,6 ,7 ,8 ,9 , 10 ,11 ,12 }) , mensajesPorMes );
		return chart;
	}
	

	public boolean existeContacto(int telf) {
		for(Contacto contacto : this.conversaciones) {
			if(contacto instanceof ContactoIndividual) {
				ContactoIndividual contactoIndv = (ContactoIndividual) contacto;
				if(contactoIndv.getTelefono() == telf) return true;
			}
		}
		return false;
	}
	
	public List<Grupo> obtenerListaGruposAdmin() {
		List<Grupo> lista = new LinkedList<>();
		this.conversaciones.stream()
			.forEach(c -> c.formarListaGruposAdmin(lista, this.movil));
		return lista;
	}
	
	
	
	public String getImagen() {
		return imagen;
	}

	public String getEstadoText() {
		return estadoText;
	}
	
	public List<Contacto> getConversaciones (){
		return conversaciones;//new LinkedList<Contacto>(conversaciones);		
	}
	
	/*
	 * Obtiene la lista de conversaciones ordenadas por su fecha
	 * Se utiliza para determinar visualmente el orden de las conversaciones
	 */
	public List<Contacto> getConversacionesOrdenadas() {
		List<Contacto> aux = new LinkedList<>(conversaciones);
		aux.sort((c1, c2) -> {
			
			LinkedList<Mensaje> listaMensajes1 = new LinkedList<>(c1.getMensajes());
			LinkedList<Mensaje> listaMensajes2 = new LinkedList<>(c2.getMensajes());
		
			if (listaMensajes1.size()==0 || listaMensajes1==null ) {
				return 1;
			}
			
			if (listaMensajes2.size()==0 || listaMensajes2==null) {
				return -1;
			}
			else {
				
				Mensaje m1 = listaMensajes1.getLast();
				Mensaje m2 = listaMensajes2.getLast();
				Date fechaUltimoMensaje1 = m1.getHora();
				Date fechaUltimoMensaje2 = m2.getHora();
				
				if(fechaUltimoMensaje1.compareTo(fechaUltimoMensaje2)<0) {
					return 1;
				}
				else if (fechaUltimoMensaje1.compareTo(fechaUltimoMensaje2)==0){
					return 0;
				}
				else return -1;				
			}
			
		});
		return aux;
	}


	public String getEmail() {
		return email;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public Integer getMovil() {
		return movil;
	}

	public String getLogin() {
		return login;
	}

	public String getContrasena() {
		return contrasena;
	}

	public boolean isPremium() {
		return premium;
	}

	public String getDescuento() {
		return descuento;
	}

	// Metodos de establecimiento

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public void setLogin(String usuario) {
		this.login = usuario;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public void setMovil(int movil) {
		this.movil = movil;
	}

	public void setPremium(boolean premium) {
		this.premium = premium;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public void setDescuento(String descuento) {
		this.descuento = descuento;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
	public void setEstadoText(String estadoText) {
		this.estadoText = estadoText;
	}

	// Obtener el Descuento en forma de Objetoc
	/*
	Descuento descuento;
	try {
		Object descuentoObj =  Class.forName(this.descuento).cast(descuentoa);
		descuento = (Descuento) descuentoObj;
		} catch (ClassNotFoundException e1) {
			System.out.println("Error al recuperar descuento");
	}
	*/
}
