package org.nailservice.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.nailservice.dao.OrderDao;
import org.nailservice.dto.OrderDto;
import org.nailservice.entity.Order;
import org.nailservice.exception.InvalidOrderTimeException;
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
        checkOrderTime(order);
        orderDao.save(order);
    }

    private void checkOrderTime(Order order) {
        List<Order> orders = getShedule(order);
        if (order.getId() != null) {
            orders = deleteEditedOrderFromList(order.getId(), orders);
        }
        if (!checkFreeTime(order, orders)) {
            throw new InvalidOrderTimeException("busy time");
        }
    }

    private List<Order> getShedule(Order order) {
        LocalDateTime start = order.getStart().toLocalDate().atStartOfDay();
        LocalDateTime end = start.plusHours(23);
        return orderDao.findAllByStartBetweenOrderByStart(start, end);
    }

    private List<Order> deleteEditedOrderFromList(int editedOrderId, List<Order> orders) {
        List<Order> ordersWithoutEdited = new ArrayList<>();
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getId() != editedOrderId) {
                ordersWithoutEdited.add(orders.get(i));
            }
        }
        return ordersWithoutEdited;
    }

    private boolean checkFreeTime(Order order, List<Order> orders) {
        if (orders.isEmpty()) {
            return true;
        }
        LocalDateTime inputStart = order.getStart();
        LocalDateTime inputEnd = order.getEnd();
        for (int i = 0; i < orders.size(); i++) {
            if (i == 0 && inputEnd.isBefore(orders.get(i).getStart())) {
                return true;
            }
            if ((i + 1) == orders.size() && inputStart.isAfter(orders.get(i).getEnd())) {
                return true;
            }
            if (inputStart.isAfter(orders.get(i).getEnd()) && inputEnd.isBefore(orders.get(i + 1).getStart())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void deleteById(Integer id) {
        Order order = deleteChainedEntities(orderDao.findById(id).get());
        orderDao.save(order);
        orderDao.deleteById(id);
    }

    private Order deleteChainedEntities(Order order) {
        return Order.builder()
                .withId(order.getId())
                .withCustomer(null)
                .withProcedures(order.getProcedures())
                .withStart(order.getStart())
                .withEnd(order.getEnd())
                .withAmount(order.getAmount())
                .build();
    }
}
