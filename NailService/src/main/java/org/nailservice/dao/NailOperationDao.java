package org.nailservice.dao;

import java.util.Optional;

import org.nailservice.entity.NailOperation;
import org.springframework.data.repository.CrudRepository;

public interface NailOperationDao extends CrudRepository<NailOperation,Integer> {
    
    Optional<NailOperation> findByName(String name);
}
