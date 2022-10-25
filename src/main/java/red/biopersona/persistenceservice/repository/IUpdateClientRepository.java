package red.biopersona.persistenceservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import red.biopersona.persistenceservice.model.UpdateClient;

public interface IUpdateClientRepository extends MongoRepository<UpdateClient, String> {
	
	@Query("{ 'key' : ?0 }")
	UpdateClient findClienteByLlave(String llave);
	
	@Query("{'active':true}")
	List<UpdateClient> findClientesDisponibles();
	
	@Query("{'companyName': ?0}")
	UpdateClient findClienteByName(String name);
}
