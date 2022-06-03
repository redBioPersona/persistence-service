package red.biopersona.persistenceservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import red.biopersona.persistenceservice.model.RequestEnrollFaceDTO;
import red.biopersona.persistenceservice.model.ResponseEnrollFaceDTO;
import red.biopersona.persistenceservice.service.FaceService;

@RestController
@RequestMapping("/persistence")
public class FaceController {

	@Autowired
	FaceService faceService;

	@PostMapping(value = "/enrollFace", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registraCliente(@ModelAttribute RequestEnrollFaceDTO request) {
		HttpStatus code = HttpStatus.OK;
		ResponseEnrollFaceDTO resul = faceService.enrollFace(request);
		return new ResponseEntity<>(resul, code);
	}

}