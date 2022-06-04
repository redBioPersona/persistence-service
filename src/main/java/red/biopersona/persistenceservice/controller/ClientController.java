package red.biopersona.persistenceservice.controller;

import java.util.List;

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

import red.biopersona.persistenceservice.model.EntityClientes;
import red.biopersona.persistenceservice.service.ClientesService;
import red.biopersona.persistenceservice.service.MongoService;

@RestController
@RequestMapping("/persistence")
/***
 * 
 * @author Omar Barrera Valentin
 *
 */
public class ClientController{
	
	@Autowired
	ClientesService clientesService;
	
	@Autowired
	MongoService mongoS;
	
	@PostMapping(value = "/registraCliente", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registraCliente(@RequestBody EntityClientes cliente)  {
		HttpStatus code = HttpStatus.OK;
		String resul=clientesService.registraCliente(cliente);
		return new ResponseEntity<>(resul, code);
	}
	
	@GetMapping(value = "/getClientesDisponibles", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getClientesDisponibles()  {
		HttpStatus code = HttpStatus.OK;
		List<Document> datos=mongoS.getClientesDisponibles();
		return new ResponseEntity<>(datos, code);
	}
	
	@PostMapping(value = "/existeCliente", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> existeCliente(@RequestParam String cliente)  {
		HttpStatus code = HttpStatus.OK;
		boolean resul=clientesService.existeCliente(cliente);
		return new ResponseEntity<>(resul, code);
	}
}
