package red.biopersona.persistenceservice.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "companies")
public class EntityClientes implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String _id=new ObjectId().toString();
	
	@Field
	private String llave=new ObjectId().toString();
	
	@NotNull(message = "Cliente name not null")
	private String companyName;
	private String direccion;
	
	private Object logo;
	
	//Datos del contacto, con el cliente
	private String contactoNombre;
	private String contactoEmail;
	private String contactoNumero;
	
	//Información sobre los usos permitidos (operaciones)
	@Field
	private boolean puedeConsumirIris=false;
	@Field
	private boolean puedeConsumirFace=false;
	@Field
	private boolean puedeConsumirFinger=false;
	@Field
	private boolean active=false;
	
	//Información sobre los usos permitidos (tiempos)
	private int minOperacionesDiarias;
	private Date maxDateUso;
	
	//Información sobre creación/actualización
	private String createdBy;
	private String updatedBy;
	@Field
    private Date createdAt=new Date();
    @Field
    private Date updatedAt=new Date();

}
