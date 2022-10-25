package red.biopersona.persistenceservice.service;

import red.biopersona.persistenceservice.model.AddClient;

public interface IClientesService {
	//boolean validaFecha(String cliente);
	String registraCliente(AddClient cliente);
	boolean existeCliente(String cliente);
	//boolean puedeConsumir(String cliente,String servicio) throws CollectionsServiceException;
}
