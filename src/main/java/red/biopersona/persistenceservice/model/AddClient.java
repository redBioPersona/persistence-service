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
public class AddClient implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String _id=new ObjectId().toString();
	
	@Field
	@NotNull(message = "key must not null")
	private String key=new ObjectId().toString();
	
	@NotNull(message = "Cliente name not null")
	private String companyName;
	private String direccion;
	
	private Object logo;
	
	//Datos del contacto, con el cliente
	@NotNull(message = "Client name cannot be null")
	private String contactoNombre;
	
	@NotNull(message = "Client mail cannot be null")
	private String contactoEmail;
	
	@NotNull(message = "Client number cannot be null")
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
	@NotNull(message = "createdBy not null")
	protected String createdBy;
	
	@Field
    private Date createdAt=new Date();

}
