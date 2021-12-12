package org.nailservice.service;

import java.util.List;

import org.nailservice.dto.ProcedureDto;
import org.nailservice.entity.Procedure;

public interface ProcedureService {

    List<Procedure> findAllProcedures();

    void createProcedure(ProcedureDto procedureDto);

    void editProcedure(ProcedureDto procedureDto);

    void deleteById(Integer id);
}
