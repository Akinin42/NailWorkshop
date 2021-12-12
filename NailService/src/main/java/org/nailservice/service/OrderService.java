package org.nailservice.service;

import org.nailservice.dto.OrderDto;


public interface OrderService {    
 
    void createOrder(OrderDto orderDto);

    void deleteById(Integer id);
}
