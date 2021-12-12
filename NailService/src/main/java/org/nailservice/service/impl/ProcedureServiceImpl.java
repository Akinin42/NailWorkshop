package org.nailservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.nailservice.dao.ProcedureDao;
import org.nailservice.dto.ProcedureDto;
import org.nailservice.entity.Procedure;
import org.nailservice.exception.EntityAlreadyExistException;
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
            throw new EntityAlreadyExistException("procedure exist");
        }
        procedureDao.save(procedure);
    }

    private Procedure mapDtoToEntity(ProcedureDto procedureDto) {
        Procedure procedure = new Procedure();
        procedure.setId(procedureDto.getId());
        procedure.setName(procedureDto.getName());
        procedure.setDuration(procedureDto.getDuration());
        procedure.setCost(procedureDto.getCost());
        return procedure;
    }

    private boolean existProcedure(Procedure procedure) {
        return !procedureDao.findByName(procedure.getName()).equals(Optional.empty());
    }

    @Override
    public void editProcedure(ProcedureDto procedureDto) {
        Procedure procedure = mapDtoToEntity(procedureDto);
        procedureDao.save(procedure);
    }

    @Override
    public void deleteById(Integer id) {
        procedureDao.deleteById(id);
    }
}
