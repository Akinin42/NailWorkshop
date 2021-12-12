package org.nailservice.service.impl;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Customer> findCustomer(String phone) {
        return customerDao.findByPhone(phone);
    }

    @Override
    public void createCustomer(String name, String phone) {
        Customer customer = Customer.builder()
                .withName(name)
                .withPhone(phone)
                .build();
        if (!existCustomer(customer)) {
            customerDao.save(customer);
        }
    }

    private boolean existCustomer(Customer customer) {
        return !customerDao.findByPhone(customer.getPhone()).equals(Optional.empty());
    }

    @Override
    public void deleteById(Integer id) {
        customerDao.deleteById(id);
    }
}
