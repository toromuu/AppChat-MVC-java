package persistencia;


import modelo.Grupo;

public interface IAdaptadorGrupo {
	
	public void registrarGrupo(Grupo grupo);
	public void borrarGrupo(Grupo grupo);
	public void borrarMensajes(Grupo grupo);
	public void modificarGrupo(Grupo grupo);
	public Grupo recuperarGrupo(int codigo);
}
