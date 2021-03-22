package persistencia;

public class AppChatFactoriaDAO extends FactoriaDAO {
	
	public AppChatFactoriaDAO () {
	}
	
	@Override
	public IAdaptadorContactoIndividualDAO getContactoIndividualDAO() {
		return AdaptadorContactoIndividual.getUnicaInstancia();
	}

	@Override
	public IAdaptadorGrupo getGrupoDAO() {
		return AdaptadorGrupo.getUnicaInstancia();
	}

	@Override
	public IAdaptadorMensajeDAO getMensajeDAO() {
		return AdaptadorMensaje.getUnicaInstancia();
	}

	@Override
	public IAdaptadorUsuarioDAO getUsuarioDAO() {
		return AdaptadorUsuario.getUnicaInstancia();
	}

}
