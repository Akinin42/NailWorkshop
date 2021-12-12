package org.nailservice.service;

import java.util.List;
import java.util.Optional;

import org.nailservice.entity.Customer;

public interface CustomerService {
    
    List<Customer> findAllCustomers();
    
    Optional<Customer> findCustomer(String phone);
    
    void createCustomer(String name, String phone);
    
    void deleteById(Integer id);
}
