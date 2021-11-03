package org.nailservice.service;

import java.util.List;

import org.nailservice.entity.Customer;

public interface CustomerService {
    
    List<Customer> findAllCustomers();
    
    Customer createCustomer(Customer customer);
}
