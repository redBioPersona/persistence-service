package red.biopersona.persistenceservice.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import red.biopersona.persistenceservice.model.AddClient;
import red.biopersona.persistenceservice.model.OperationsDTO;
import red.biopersona.persistenceservice.model.UpdateClient;
import red.biopersona.persistenceservice.repository.IAddClientRepository;
import red.biopersona.persistenceservice.repository.IOperationsRepository;
import red.biopersona.persistenceservice.repository.IUpdateClientRepository;
import red.biopersona.persistenceservice.util.OperationsEnum;
import red.biopersona.persistenceservice.util.StatusEnum;

@Slf4j
@Service
@SuppressWarnings("static-access")
public class ClientesService implements IClientesService {

    @Autowired
    private IAddClientRepository addclientRepository;

    @Autowired
    private IUpdateClientRepository updateclientRepository;

    @Autowired
    private IOperationsRepository operationsRepository;

    OperationsEnum operations;
    StatusEnum status;

    public boolean existeCliente(String cliente) {
        UpdateClient datosClientes = updateclientRepository.findClienteByLlave(cliente);
        boolean existenClientes = false;
        if (datosClientes != null) {
            existenClientes = true;
        }
        log.info("Existe el cliente " + cliente + "?=" + datosClientes);
        return existenClientes;
    }

    public List<UpdateClient> getClientesDisponibles() {
        return updateclientRepository.findClientesDisponibles();
    }

    public List<UpdateClient> getClients() {
        return updateclientRepository.findAll();
    }

    public void deleteClient(String client) {
        log.info("Deleting " + client);
        addclientRepository.deleteByKey(client);
    }

    
    public String registraCliente(AddClient cliente) {
        OperationsDTO operacion = new OperationsDTO();
        operacion.setKey(cliente.getKey());
        operacion.setOperation(operations.ENROLL_CLIENT);
        operacion.setRequest(cliente);
        AddClient existe = addclientRepository.findClienteByName(cliente.getCompanyName());
        String result=null;
        if (existe == null) {
            AddClient insertado = addclientRepository.insert(cliente);
            operacion.setStatus(status.OK);
            operacion.setResponse("saved");
            result= insertado.getKey();
        } else {
            String err = "Err insert: " + cliente.getCompanyName() + ", exists data";
            operacion.setStatus(status.ERROR);
            operacion.setResponse(err);
            log.error(err);
        }
        operationsRepository.insert(operacion);
        return result;
    }

    public UpdateClient updateCliente(UpdateClient cliente) {
        OperationsDTO operacion = new OperationsDTO();
        operacion.setKey(cliente.getKey());
        operacion.setOperation(operations.UPDATE_CLIENT);
        operacion.setRequest(cliente);

        UpdateClient existe = updateclientRepository.findClienteByLlave(cliente.getKey());
        if (existe != null) {
            cliente.set_id(existe.get_id());
            cliente.setUpdatedAt(new Date());
            cliente.setCreatedAt(existe.getCreatedAt());
            cliente.setCreatedBy(existe.getCreatedBy());
            operacion.setStatus(status.OK);
            UpdateClient act=updateclientRepository.save(cliente);
            operacion.setResponse(act);
            return act;
        } else {
            operacion.setStatus(status.ERROR);
            String err="Err update: "+cliente.getCompanyName()+", not found, with key:"+cliente.getKey(); 
            operacion.setResponse(err);
            log.error(err);
        }
        return null;
    }

}
