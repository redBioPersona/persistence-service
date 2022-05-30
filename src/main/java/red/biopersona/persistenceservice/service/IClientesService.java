package red.biopersona.persistenceservice.service;

import red.biopersona.persistenceservice.model.EntityClientes;

public interface IClientesService {
	//boolean validaFecha(String cliente);
	String registraCliente(EntityClientes cliente);
	boolean existeCliente(String cliente);
	//boolean puedeConsumir(String cliente,String servicio) throws CollectionsServiceException;
}
