package org.nailservice.api.v1;

import java.util.List;

import org.nailservice.entity.Customer;
import org.nailservice.service.CustomerService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public List<Customer> findAll() {
        return customerService.findAllCustomers();
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Integer id) {
        customerService.deleteById(id);
    }
}
