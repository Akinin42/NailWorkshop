package org.nailservice.dao;

import java.util.Optional;

import org.nailservice.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDao extends CrudRepository<Customer, Integer> {

    Optional<Customer> findByPhone(String phone);
}
