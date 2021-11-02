package org.nailservice.controller;

import java.util.List;

import org.nailservice.entity.Customer;
import org.nailservice.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;
    
    @GetMapping
    public List<Customer> findAll() {
        return customerService.findAllCustomers();
    }
}
