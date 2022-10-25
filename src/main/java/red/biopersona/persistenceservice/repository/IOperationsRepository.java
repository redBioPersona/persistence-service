package red.biopersona.persistenceservice.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import red.biopersona.persistenceservice.model.OperationsDTO;

public interface IOperationsRepository extends MongoRepository<OperationsDTO, String> {
    
}
