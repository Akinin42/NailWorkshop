package org.nailservice.dao;

import org.nailservice.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderDao extends CrudRepository<Order, Integer> {
}
