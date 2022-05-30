package red.biopersona.persistenceservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import red.biopersona.persistenceservice.model.EntityClientes;
import red.biopersona.persistenceservice.repository.IClientesRepository;

@Slf4j
@Service
public class ClientesService implements IClientesService {
	@Autowired
	private IClientesRepository clientesRepository;


//	public boolean puedeConsumir(String cliente, String servicio) throws CollectionsServiceException {
//		boolean result = false;
//		if (cliente.equalsIgnoreCase("omar")) {
//			throw new CollectionsServiceException(ErrorEnum.EXC_ERROR_PARAMS);
//		}
//		return result;
//	}

	public boolean existeCliente(String cliente) {
		boolean result = false;
		List<EntityClientes> datosClientes = clientesRepository.findClienteByLlave(cliente);
		boolean existenClientes = !datosClientes.isEmpty();
		log.info("Existe el cliente " + cliente + "?=" + existenClientes);
		if (existenClientes) {
			result = true;
		}
		return result;
	}

	public String registraCliente(EntityClientes cliente) {
		EntityClientes insertado = clientesRepository.insert(cliente);
		return insertado.getLlave();
	}
}
