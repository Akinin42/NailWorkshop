package org.nailservice.service.impl;

import org.nailservice.dao.OrderDao;
import org.nailservice.dto.OrderDto;
import org.nailservice.entity.Order;
import org.nailservice.service.CustomerService;
import org.nailservice.service.OrderService;
import org.nailservice.service.mapper.OrderMapper;
import org.nailservice.service.validator.OrderValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final CustomerService customerService;
    private final OrderMapper mapper;
    private final OrderValidator validator;

    @Override
    public void createOrder(OrderDto orderDto) {
        customerService.createCustomer(orderDto.getCustomerName(), orderDto.getCustomerPhone());
        Order order = mapper.mapDtoToEntity(orderDto);
        validator.validate(order);

    }

    @Override
    public void editOrder(OrderDto orderDto) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteById(Integer id) {
        // TODO Auto-generated method stub

    }

}
