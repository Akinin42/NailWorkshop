package org.nailservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.nailservice.dao.NailOperationDao;
import org.nailservice.dto.NailOperationDto;
import org.nailservice.entity.NailOperation;
import org.nailservice.exception.EntityAlreadyExistException;
import org.nailservice.service.NailOperationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class NailOperationServiceImpl implements NailOperationService {

    private final NailOperationDao operationDao;

    @Override
    public List<NailOperation> findAllOperations() {
        return (List<NailOperation>) operationDao.findAll();
    }

    @Override
    public void createOperation(NailOperationDto operationDto) {
        NailOperation operation = mapDtoToEntity(operationDto);
        if (existOperation(operation)) {
            throw new EntityAlreadyExistException("operation exist");
        }
        operationDao.save(operation);
    }

    private NailOperation mapDtoToEntity(NailOperationDto operationDto) {
        NailOperation operation = new NailOperation();
        operation.setId(operationDto.getId());
        operation.setName(operationDto.getName());
        operation.setDuration(operationDto.getDuration());
        operation.setCost(operationDto.getCost());
        return operation;
    }

    private boolean existOperation(NailOperation operation) {
        return !operationDao.findByName(operation.getName()).equals(Optional.empty());
    }

    @Override
    public void editCOperation(NailOperationDto operationDto) {
        NailOperation operation = mapDtoToEntity(operationDto);
        operationDao.save(operation);
    }

    @Override
    public void deleteById(Integer id) {
        operationDao.deleteById(id);
    }
}
