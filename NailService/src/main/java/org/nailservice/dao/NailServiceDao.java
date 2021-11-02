package org.nailservice.dao;

import org.nailservice.entity.NailService;
import org.springframework.data.repository.CrudRepository;

public interface NailServiceDao extends CrudRepository<NailService,Integer> {
}
