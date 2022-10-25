package red.biopersona.persistenceservice.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;
@SuppressWarnings("unused")
@Getter
@Setter
@Document(collection = "companies")
public class UpdateClient extends AddClient implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//Información sobre creación/actualización
	@NotNull(message = "createdBy not null")
	private String updatedBy;

	
    @Field
    private Date updatedAt=new Date();
    
    public UpdateClient() {
    	createdBy = "";
    }

}
