package org.nailservice.service;

import java.util.List;

import org.nailservice.dto.NailOperationDto;
import org.nailservice.entity.NailOperation;

public interface NailOperationService {

    List<NailOperation> findAllOperations();

    void createOperation(NailOperationDto operationDto);

    void editCOperation(NailOperationDto operationDto);

    void deleteById(Integer id);
}
