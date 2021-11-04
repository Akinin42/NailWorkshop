package org.nailservice.controller;

import java.util.List;

import org.nailservice.entity.Customer;
import org.nailservice.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer add(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }
    
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer update(@RequestBody Customer customer) {
        return customerService.editCustomer(customer);
    }
    
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Integer id) {
        customerService.deleteById(id);
    }
}
