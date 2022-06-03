package red.biopersona.persistenceservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import red.biopersona.persistenceservice.model.EntityClientes;

public interface IClientesRepository extends MongoRepository<EntityClientes, String> {
	
	@Query("{ 'llave' : ?0 }")
	List<EntityClientes> findClienteByLlave(String llave);
	
	@Query("{'active':true}")
	List<EntityClientes> findClientesDisponibles();
}
