package red.biopersona.persistenceservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import red.biopersona.persistenceservice.model.AddClient;
import red.biopersona.persistenceservice.model.UpdateClient;
import red.biopersona.persistenceservice.service.ClientesService;
import red.biopersona.persistenceservice.service.MongoService;

@RestController
@RequestMapping("/persistence")
/***
 * 
 * @author Omar Barrera Valentin
 *
 */
@Slf4j
public class ClientController {

	@Autowired
	ClientesService clientesService;

	@Autowired
	MongoService mongoS;

	@PostMapping(value = "/addClient", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registraCliente(@RequestBody @Valid AddClient cliente) {
		HttpStatus code = HttpStatus.OK;
		String resul = clientesService.registraCliente(cliente);
		if (resul == null) {
			code = HttpStatus.BAD_REQUEST;
			return new ResponseEntity<>("duplicated", code);
		}
		return new ResponseEntity<>(resul, code);
	}
	
	@PostMapping(value = "/deleteClient", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteClient(@RequestParam String key) {
		clientesService.deleteClient(key);
		return new ResponseEntity<>("", HttpStatus.OK);
	}

	@PostMapping(value = "/updateClient", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateCliente(@RequestBody @Valid UpdateClient cliente) {
		HttpStatus code = HttpStatus.OK;
		UpdateClient resul = clientesService.updateCliente(cliente);
		if (resul == null) {
			code = HttpStatus.BAD_REQUEST;
			return new ResponseEntity<>("notFound", code);
		}
		return new ResponseEntity<>(resul, code);
	}

	@GetMapping(value = "/getAvailableClients", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getClientesDisponibles() {
		HttpStatus code = HttpStatus.OK;
		List<Document> datos = mongoS.getClientesDisponibles();
		return new ResponseEntity<>(datos, code);
	}
	
	@GetMapping(value = "/getClients", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getClients() {
		HttpStatus code = HttpStatus.OK;
		List<UpdateClient> datos = clientesService.getClients();
		return new ResponseEntity<>(datos, code);
	}

	@PostMapping(value = "/existsClient", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> existeCliente(@RequestParam String client) {
		HttpStatus code = HttpStatus.OK;
		boolean resul = mongoS.canOperateTheClient(client);
		log.info("validando si existe el cliente " + client + "?=" + resul);
		return new ResponseEntity<>(resul, code);
	}
}
