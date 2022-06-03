package red.biopersona.persistenceservice.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import red.biopersona.persistenceservice.model.RequestEnrollFaceDTO;
import red.biopersona.persistenceservice.model.ResponseEnrollFaceDTO;

@Service
public class FaceService implements IFaceService{

	@Autowired
	MongoService mongoS;
	
	public ResponseEnrollFaceDTO enrollFace(RequestEnrollFaceDTO request) {
		ResponseEnrollFaceDTO resp=new ResponseEnrollFaceDTO();
		try {
			mongoS.saveTemplate(request.getClient(),request.getFile().getBytes(), request.getClient());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return resp;
		
	}
}
