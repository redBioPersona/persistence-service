package red.biopersona.persistenceservice.service;

import red.biopersona.persistenceservice.controller.exception.CollectionsServiceException;
import red.biopersona.persistenceservice.model.RequestEnrollFaceDTO;

public interface IBioService {
	String enrollFace(RequestEnrollFaceDTO request) throws CollectionsServiceException;
	boolean deleteSample(String client,String sample) throws CollectionsServiceException;
}
