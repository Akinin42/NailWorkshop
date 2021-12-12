package org.nailservice.service;

import java.util.List;
import java.util.Optional;

import org.nailservice.dto.CustomerDto;
import org.nailservice.entity.Customer;

public interface CustomerService {
    
    List<Customer> findAllCustomers();
    
    Optional<Customer> findCustomer(String phone);
    
    void createCustomer(CustomerDto customer);
    
    void editCustomer(CustomerDto customer);
    
    void deleteById(Integer id);
}
