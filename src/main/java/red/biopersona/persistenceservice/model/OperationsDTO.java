package red.biopersona.persistenceservice.model;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;
import red.biopersona.persistenceservice.util.OperationsEnum;
import red.biopersona.persistenceservice.util.StatusEnum;

@Getter
@Setter
@Document(collection = "operations")
public class OperationsDTO  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String _id=new ObjectId().toString();
    
    private OperationsEnum operation;
    
    private StatusEnum status;
    
    private String key;
    
    private Object request;
    
    private Object response;
    
    @Field
    private Date createdAt=new Date();
    

}
