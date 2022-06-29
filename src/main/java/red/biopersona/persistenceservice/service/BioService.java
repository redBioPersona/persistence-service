package red.biopersona.persistenceservice.service;

import java.io.IOException;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import red.biopersona.persistenceservice.controller.exception.CollectionsServiceException;
import red.biopersona.persistenceservice.model.RequestEnrollFaceDTO;

@Service
@Slf4j
public class BioService implements IBioService{

	@Autowired
	MongoService mongoS;
	
	public String enrollFace(RequestEnrollFaceDTO request) throws CollectionsServiceException {
		String resp=null;
		boolean puedeOperar=mongoS.canOperateTheClient(request.getClient());
		log.info("El cliente:"+request.getClient()+" puede operar?="+puedeOperar);
		if(puedeOperar) {
			try {
				ObjectId template=mongoS.saveTemplate(request.getBiometricPerson(),request.getFile().getBytes(), request.getClient());
				resp=template.toString();
				mongoS.SaveRelationShips(request.getClient(), template, null, null, null, request.getBiometricPerson(), "Facial", request.getSegmentation());
			} catch (IOException e) {
				e.printStackTrace();
			}		
		}else {
			throw new CollectionsServiceException("El cliente no puede consumir el servicio");
		}	
		return resp;
		
	}
	
	public boolean deleteSample(String client,String sample) throws CollectionsServiceException {
		return mongoS.RemoveFile(client, sample);		
	}
}
