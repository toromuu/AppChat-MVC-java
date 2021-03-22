package PracticaFinal.UmuChat;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Test;

import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Grupo;
import modelo.Mensaje;
import modelo.Usuario;

public class usuarioTests {

	private Usuario a;
	private ContactoIndividual b;
	private ContactoIndividual c;
	private ContactoIndividual d;
	private Usuario dUser;
	private Grupo g;
	private Mensaje b1;
	
	
	@Before
	public void inicializar() {
		
		b = new ContactoIndividual("b", 608123456);
		b1 = new Mensaje("Hola!", new Date(937519200), 608123456, Mensaje.EMOTICONO_VACIO);
		b.anadirMensaje(b1);
		
		List<Contacto> participantes = new LinkedList<>();
		participantes.add(b);
		
		g = new Grupo("g", 608842375, participantes);
		c = new ContactoIndividual("c", 608654321);
		d = new ContactoIndividual("d", 608000000);
	
		List<Contacto> contactos = new LinkedList<>();
		contactos.add(b);
		contactos.add(g);
		contactos.add(c);
		contactos.add(d);
		
		a = new Usuario("a", new Date(937519200), "a@um.es", 608842375, "a", "psswd",contactos,null,null,false,"");
		dUser = new Usuario("d", new Date(937519200), "d@um.es", 608000000, "d", "psswd");
		
	}
	
	
	@Test
	public void testObtenerContactos() {
		
		List<Contacto> contactosCheck = new LinkedList<>();
		contactosCheck.add(b);
		contactosCheck.add(g);
		contactosCheck.add(c);
		contactosCheck.add(d);
		
		assertEquals(contactosCheck, a.getConversaciones());
	}
	
	
	@Test
	public void testObtenerGrupos() {
		
		List<Contacto> contactosCheck = new LinkedList<>();
		contactosCheck.add(g);
		
		assertEquals(contactosCheck, a.obtenerGrupos());
	}
	
	@Test
	public void testGrupoRegistrado() {
		
		assertEquals(g, a.grupoRegistrado("g"));
	}
	
	
	@Test
	public void testObtenerContactoIndividual1() {
		
		assertEquals(b, a.obtenerContactoIndv(608123456));
	}
	
	@Test
	public void testObtenerContactoIndividual2() {
		
		assertEquals(b, a.obtenerContactoIndv("b"));
	}
	
	@Test
	public void testObtenerContactoIndividualError() {
		
		assertNull(a.obtenerContactoIndv("pepe"));
	}
	
	@Test
	public void testCheckUsuario1() {
		
		assertTrue(a.checkUsuario(dUser));
	}
	
	@Test
	public void testCheckUsuario2() {
		
		assertFalse(a.checkUsuario(new Usuario("w", new Date(937519200), "w@um.es", 608192837, "w", "psswd")));
	}
	
	@Test
	public void testBuscarMensajes() {
		
		List<Mensaje> expectedList = new LinkedList<>();
		expectedList.add(b1);
		
		List<Predicate<Mensaje>> condiciones = new LinkedList<>();
		condiciones.add(m -> m.getTexto().equals("Hola!"));
		
		assertEquals(expectedList, a.buscarMensajes(c -> c instanceof ContactoIndividual, condiciones));
	}

	

}
