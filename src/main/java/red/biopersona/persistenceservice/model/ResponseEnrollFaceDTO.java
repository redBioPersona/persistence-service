package red.biopersona.persistenceservice.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseEnrollFaceDTO  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String client;
	private String biometricPerson;
	private String segmentation;
	private byte[] file;

}