package org.nailservice.api.v1;

import javax.validation.Valid;

import org.nailservice.dto.OrderDto;
import org.nailservice.exception.InvalidOrderTimeException;
import org.nailservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addOrder(@Valid @RequestBody OrderDto order) {
        try {
            orderService.createOrder(order);
        } catch (InvalidOrderTimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public void deleteOrder(@PathVariable("id") Integer id) {
        orderService.deleteById(id);
    }
}
