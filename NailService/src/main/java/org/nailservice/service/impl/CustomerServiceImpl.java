package org.nailservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.nailservice.dao.CustomerDao;
import org.nailservice.dto.CustomerDto;
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
    public Optional<Customer> findCustomer(String phone) {
        return customerDao.findByPhone(phone);
    }

    @Override
    public void createCustomer(CustomerDto customerDto) {
        Customer customer = mapDtoToEntity(customerDto);
        if (existCustomer(customer)) {
            throw new EntityAlreadyExistException("customerexist");
        }
        customerDao.save(customer);
    }

    private boolean existCustomer(Customer customer) {
        return !customerDao.findByPhone(customer.getPhone()).equals(Optional.empty());
    }

    private Customer mapDtoToEntity(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setId(customerDto.getId());
        customer.setName(customerDto.getName());
        customer.setPhone(customerDto.getPhone());
        return customer;
    }

    @Override
    public void editCustomer(CustomerDto customerDto) {
        Customer customer = mapDtoToEntity(customerDto);
        customerDao.save(customer);
    }

    @Override
    public void deleteById(Integer id) {
        customerDao.deleteById(id);
    }
}
