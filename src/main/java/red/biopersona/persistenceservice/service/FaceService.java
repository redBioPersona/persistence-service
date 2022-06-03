package red.biopersona.persistenceservice.service;

import java.io.IOException;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import red.biopersona.persistenceservice.model.RequestEnrollFaceDTO;
import red.biopersona.persistenceservice.model.ResponseEnrollFaceDTO;
import red.biopersona.persistenceservice.controller.exception.CollectionsServiceException;
@Service
@Slf4j
public class FaceService implements IFaceService{

	@Autowired
	MongoService mongoS;
	
	public ResponseEnrollFaceDTO enrollFace(RequestEnrollFaceDTO request) throws CollectionsServiceException {
		ResponseEnrollFaceDTO resp=new ResponseEnrollFaceDTO();
		boolean puedeOperar=mongoS.canOperateTheClient(request.getClient());
		log.info("El cliente, puede operar?="+puedeOperar);
		if(puedeOperar) {
			try {
				ObjectId template=mongoS.saveTemplate(request.getBiometricPerson(),request.getFile().getBytes(), request.getClient());
				mongoS.SaveRelationShips(request.getClient(), template, null, null, null, request.getBiometricPerson(), "Facial", request.getSegmentation());
			} catch (IOException e) {
				e.printStackTrace();
			}		
		}else {
			throw new CollectionsServiceException("asd");
		}	
		return resp;
		
	}
}
