package org.nailservice.dao;

import java.util.Optional;

import org.nailservice.entity.Procedure;
import org.springframework.data.repository.CrudRepository;

public interface ProcedureDao extends CrudRepository<Procedure, Integer> {

    Optional<Procedure> findByName(String name);
}
