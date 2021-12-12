package org.nailservice.service.mapper;

import java.time.LocalDateTime;

import org.nailservice.dto.OrderDto;
import org.nailservice.entity.Order;
import org.nailservice.entity.Procedure;
import org.nailservice.service.CustomerService;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class OrderMapper {

    private final CustomerService customerService;

    public Order mapDtoToEntity(OrderDto orderDto) {
        return Order.builder()
                .withId(orderDto.getId())
                .withCustomer(customerService.findCustomer(orderDto.getCustomerPhone()).get())
                .withStart(LocalDateTime.parse(orderDto.getStart()))
                .withEnd(calculateOrderEnd(orderDto))
                .withProcedures(orderDto.getProcedures())
                .withAmount(calculateOrderAmount(orderDto))
                .build();
    }

    private LocalDateTime calculateOrderEnd(OrderDto orderDto) {
        int orderDuration = 0;
        for (Procedure procedure : orderDto.getProcedures()) {
            orderDuration += procedure.getDuration();
        }
        return LocalDateTime.parse(orderDto.getStart()).plusMinutes(orderDuration);
    }

    private Integer calculateOrderAmount(OrderDto orderDto) {
        int amount = 0;
        for (Procedure procedure : orderDto.getProcedures()) {
            amount += procedure.getCost();
        }
        return amount;
    }
}
