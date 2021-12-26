package org.nailservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.nailservice.dao.ProcedureDao;
import org.nailservice.dto.ProcedureDto;
import org.nailservice.entity.Procedure;
import org.nailservice.exception.EntityAlreadyExistException;
import org.nailservice.exception.EntityNotExistException;
import org.nailservice.service.ProcedureService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class ProcedureServiceImpl implements ProcedureService {

    private final ProcedureDao procedureDao;

    @Override
    public List<Procedure> findAllProcedures() {
        return (List<Procedure>) procedureDao.findAll();
    }

    @Override
    public void createProcedure(ProcedureDto procedureDto) {
        Procedure procedure = mapDtoToEntity(procedureDto);
        if (existProcedure(procedure)) {
            throw new EntityAlreadyExistException("procedure exists");
        }
        procedureDao.save(procedure);
    }

    private Procedure mapDtoToEntity(ProcedureDto procedureDto) {
        return Procedure.builder().withId(procedureDto.getId())
                .withName(procedureDto.getName())
                .withDuration(procedureDto.getDuration())
                .withCost(procedureDto.getCost())
                .build();
    }

    private boolean existProcedure(Procedure procedure) {
        return !procedureDao.findByName(procedure.getName()).equals(Optional.empty());
    }

    @Override
    public void editProcedure(ProcedureDto procedureDto) {
        Procedure procedure = mapDtoToEntity(procedureDto);
        if (!procedureDao.existsById(procedure.getId())) {
            throw new EntityNotExistException("procedure not exists");
        }
        procedureDao.save(procedure);
    }

    @Override
    public void deleteById(Integer id) {
        procedureDao.deleteById(id);
    }
}
