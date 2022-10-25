package red.biopersona.persistenceservice.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import red.biopersona.persistenceservice.controller.exception.CollectionsServiceException;
import red.biopersona.persistenceservice.model.RequestEnrollFaceDTO;
import red.biopersona.persistenceservice.service.IBioService;


@RestController
@RequestMapping("/persistence")
@Slf4j
/**
 * 
 * @author Omar Barrera Valentin
 *
 */
public class BioController {

	@Autowired
	IBioService bioService;

	@PostMapping(value = "/enroll", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registraCliente(@ModelAttribute @Valid RequestEnrollFaceDTO request) throws CollectionsServiceException {
		log.info("Recibiendo petición en /enroll "+request.toString());
		HttpStatus code = HttpStatus.OK;
		String resul = bioService.enroll(request);
		return new ResponseEntity<>(resul, code);
	}
	
	@PostMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> eliminaCliente(
			@RequestParam String key,
			@RequestParam String sample
			) throws CollectionsServiceException {
		log.info("Eliminando la muestra "+sample+" cliente "+key);
		HttpStatus code = HttpStatus.OK;
		boolean resul=false;
		if(sample.length()<8) {
		    code = HttpStatus.BAD_REQUEST;
		}else {
		      resul = bioService.deleteSample(key, sample);
		}
		return new ResponseEntity<>(resul, code);
	}

}
