package persistencia;

import java.util.List;
import modelo.Usuario;

public interface IAdaptadorUsuarioDAO {

	public boolean registrarUsuario(Usuario usuario);
	
	public void modificarUsuario(Usuario usuario);
	
	public Usuario recuperarUsuario(int codigo);
	
	public List<Usuario> recuperarTodosUsuarios();
	
	public void setPremium(Usuario usuarioActual, boolean premium);
	
	public void cambiarDescuento(String nombre, Usuario usuarioActual);
}
