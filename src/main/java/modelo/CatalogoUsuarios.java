package modelo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorUsuarioDAO;

public class CatalogoUsuarios {
	
	private Map<String,Usuario> usuarios; 
	private Map<Integer,Usuario> usuariosTelf; 
	private static CatalogoUsuarios unicaInstancia = new CatalogoUsuarios();
	private FactoriaDAO dao;
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	
	private CatalogoUsuarios() {
		try {
  			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_AppChat);
  			adaptadorUsuario = dao.getUsuarioDAO();
  			usuarios = new HashMap<String,Usuario>();
  			usuariosTelf = new HashMap<Integer,Usuario>();
  			this.cargarCatalogo();
  		} catch (DAOException eDAO) {
  			eDAO.printStackTrace();
  		}
	}
	
	private void cargarCatalogo() throws DAOException {
		List<Usuario> listaUsuarios = adaptadorUsuario.recuperarTodosUsuarios();
		for (Usuario usuario : listaUsuarios) {
			usuarios.put(usuario.getLogin(), usuario);
			usuariosTelf.put(usuario.getMovil(), usuario);
		}
	}
	
	public boolean existeUsuario(String login) {
		return usuarios.containsKey(login);
	}
	
	public boolean existeUsuario(int telefono) {
		return usuariosTelf.containsKey(telefono);
	}
	
	public void addUsuario(Usuario usuarioNuevo) {
		usuarios.put(usuarioNuevo.getLogin(), usuarioNuevo);
		usuariosTelf.put(usuarioNuevo.getMovil(), usuarioNuevo);
	}
	
	public List<Usuario> getUsuarios(){
		LinkedList<Usuario> lista = new LinkedList<Usuario>();
		for (Usuario c:usuarios.values()) 
			lista.add(c);
		return lista;
	}
	
	public Usuario recuperarUsuario(int codigo) {
		for (Usuario usuario : usuarios.values()) {
			if (codigo == usuario.getCodigo())
				return usuario;
		}

		return null;
	}

	
	
	public boolean comprobarContrasena(String login, String password) {

		for (Entry<String, Usuario> entry : usuarios.entrySet()) {
			if (login.equals(entry.getValue().getLogin())) {
				return password.equals(entry.getValue().getContrasena());
			}
		}
		return false;
	}
	
	
	public static CatalogoUsuarios getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new CatalogoUsuarios();
		return unicaInstancia;
	}
	
	
	public Usuario getUsuario(String login) {
		if (existeUsuario(login))
			return usuarios.get(login);
		return null;
	}
	
	public Usuario getUsuario(int telefono) {
		if (existeUsuario(telefono))
			return usuariosTelf.get(telefono);
		return null;
	}
	
	
	public void removeUsuario (Usuario cli) {
		usuarios.remove(cli.getLogin());
	}
	
}
