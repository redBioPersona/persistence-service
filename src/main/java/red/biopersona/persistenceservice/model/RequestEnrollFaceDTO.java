package red.biopersona.persistenceservice.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/***
 * 
 * @author Omar Barrera Valentin
 *
 */
public class RequestEnrollFaceDTO  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotNull
	private String client;
	@NotNull
	private String biometricPerson;
	@NotNull
	private String segmentation;
	@NotNull
	private MultipartFile file;

}
