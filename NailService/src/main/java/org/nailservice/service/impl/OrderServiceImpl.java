package org.nailservice.service.impl;

import org.nailservice.dao.OrderDao;
import org.nailservice.dto.CustomerDto;
import org.nailservice.dto.OrderDto;
import org.nailservice.entity.Order;
import org.nailservice.service.CustomerService;
import org.nailservice.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final CustomerService customerService;

    @Override
    public void createOrder(OrderDto orderDto) {
        if (!customerService.findCustomer(orderDto.getCustomerPhone()).isPresent()) {
            addNewCustomer(orderDto);
        }
        
    }

    private void addNewCustomer(OrderDto orderDto) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName(orderDto.getCustomerName());
        customerDto.setPhone(orderDto.getCustomerPhone());
        customerService.createCustomer(customerDto);
    }
    
    private Order mapDtoToEntity(OrderDto orderDto) {
        Order order = new Order();
        return order;
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
