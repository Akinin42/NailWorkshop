package org.nailservice.service;

import java.util.List;

import org.nailservice.dto.CustomerDto;
import org.nailservice.entity.Customer;

public interface CustomerService {
    
    List<Customer> findAllCustomers();
    
    void createCustomer(CustomerDto customer);
    
    void editCustomer(CustomerDto customer);
    
    void deleteById(Integer id);
}
