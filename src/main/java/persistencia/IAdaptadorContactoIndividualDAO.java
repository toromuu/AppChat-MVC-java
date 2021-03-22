package persistencia;


import modelo.ContactoIndividual;

public interface IAdaptadorContactoIndividualDAO {

	public void registrarContactoIndividual(ContactoIndividual contactoIndividual);
	public void borrarContactoIndividual(ContactoIndividual contactoIndividual);
	public void borrarMensajes(ContactoIndividual contactoIndividual);
	public void modificarContactoIndividual(ContactoIndividual contactoIndividual);
	public ContactoIndividual recuperarContactoIndividual(int codigo);
}
