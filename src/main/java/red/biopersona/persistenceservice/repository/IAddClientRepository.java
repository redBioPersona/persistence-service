package red.biopersona.persistenceservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import red.biopersona.persistenceservice.model.AddClient;

public interface IAddClientRepository extends MongoRepository<AddClient, String> {
	
	@Query("{ 'key' : ?0 }")
	List<AddClient> findClienteByLlave(String llave);
	
	@Query("{'active':true}")
	List<AddClient> findClientesDisponibles();
	
	@Query("{'companyName': ?0}")
	AddClient findClienteByName(String name);
	
	@Query(value="{'key' : ?0}", delete = true)
	void deleteByKey (String id);
}
