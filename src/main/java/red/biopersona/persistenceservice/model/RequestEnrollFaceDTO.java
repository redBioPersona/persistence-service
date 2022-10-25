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
public class RequestEnrollFaceDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "key not null")
    private String key;

    @NotNull(message = "biometricPerson not null")
    private String biometricPerson;
    
    @NotNull(message = "type not null")
    private String type;

    private String segmentation;

    @NotNull(message = "file not null")
    private MultipartFile file;

}
