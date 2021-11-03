package org.nailservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.nailservice.dao.CustomerDao;
import org.nailservice.entity.Customer;
import org.nailservice.exception.EntityAlreadyExistException;
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

    @Override
    public Customer createCustomer(Customer customer) {
        if (existCustomer(customer)) {
            throw new EntityAlreadyExistException("customerexist");
        }
        customerDao.save(customer);
        return customer;
    }

    private boolean existCustomer(Customer customer) {
        return !customerDao.findByPhone(customer.getPhone()).equals(Optional.empty());
    }
}
