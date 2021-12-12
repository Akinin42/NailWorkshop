package org.nailservice.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.nailservice.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderDao extends CrudRepository<Order, Integer> {
    
    List<Order> findAllByStartBetweenOrderByStart(LocalDateTime start, LocalDateTime end);
}
