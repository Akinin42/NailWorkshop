package org.nailservice.service.impl;

import java.util.List;

import org.nailservice.dao.CustomerDao;
import org.nailservice.entity.Customer;
import org.nailservice.service.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {
    
    private final CustomerDao customerDao;

    @Override
    public List<Customer> findAllCustomers() {        
        return (List<Customer>) customerDao.findAll();
    }
}
