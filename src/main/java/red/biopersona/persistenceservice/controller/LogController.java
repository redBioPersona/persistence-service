package red.biopersona.persistenceservice.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import red.biopersona.persistenceservice.model.AddClient;

@RestController
@RequestMapping("/persistence")
public class LogController {
    
    @PostMapping(value = "/addOperation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registraCliente(@RequestBody @Valid AddClient cliente) {
        HttpStatus code = HttpStatus.OK;
        return new ResponseEntity<>("oK", code);
    }

}
