package org.nailservice.api.v1;

import java.util.List;

import javax.validation.Valid;

import org.nailservice.dto.CustomerDto;
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
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public List<Customer> findAll() {
        return customerService.findAllCustomers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@Valid @RequestBody CustomerDto customer) {
        customerService.createCustomer(customer);
    }

    @PutMapping("{id}")
    public void update(@Valid @RequestBody CustomerDto customer) {
        customerService.editCustomer(customer);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Integer id) {
        customerService.deleteById(id);
    }
}
