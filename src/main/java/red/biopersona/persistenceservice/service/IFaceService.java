package red.biopersona.persistenceservice.service;

import red.biopersona.persistenceservice.model.RequestEnrollFaceDTO;
import red.biopersona.persistenceservice.model.ResponseEnrollFaceDTO;

public interface IFaceService {
	ResponseEnrollFaceDTO enrollFace(RequestEnrollFaceDTO request);
}
