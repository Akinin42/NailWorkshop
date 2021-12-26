package org.nailservice.utils;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.nailservice.entity.Customer;
import org.nailservice.entity.Order;
import org.nailservice.entity.Procedure;

public class CreatorTestEntities {
    
    public static List<Procedure> createTestProcedures() {
        List<Procedure> procedures = new ArrayList<>();
        Procedure procedure = Procedure.builder()
                .withId(1)
                .withName("manicure")
                .withDuration(120)
                .withCost(1000)
                .build();
        procedures.add(procedure);
        procedure = Procedure.builder()
                .withId(2)
                .withName("pedicure")
                .withDuration(90)
                .withCost(1500)
                .build();
        procedures.add(procedure);
        return procedures;
    }
    
    public static List<Customer> createTestCustomers() {
        List<Customer> customers = new ArrayList<>();
        Customer customer = Customer.builder()
                .withId(1)
                .withName("First customer")
                .withPhone("phone first")
                .build();
        customers.add(customer);
        customer = Customer.builder()
                .withId(2)
                .withName("Second customer")
                .withPhone("phone second")
                .build();
        customers.add(customer);
        return customers;
    }

    public static List<Order> createOrders() {
        List<Order> orders = new ArrayList<>();
        Set<Procedure> procedures = new HashSet<>();
        procedures.add(createTestProcedures().get(0));
        Order order = Order.builder()
                .withId(1)
                .withCustomer(createTestCustomers().get(0))
                .withStart(LocalDateTime.of(2021, Month.DECEMBER, 27, 10, 00, 00))
                .withEnd(LocalDateTime.of(2021, Month.DECEMBER, 27, 12, 00, 00))
                .withProcedures(procedures)
                .withAmount(1000)
                .build();
        orders.add(order);
        procedures.add(createTestProcedures().get(1));
        order = Order.builder()
                .withId(2)
                .withCustomer(createTestCustomers().get(1))
                .withStart(LocalDateTime.of(2021, Month.DECEMBER, 27, 13, 00, 00))
                .withEnd(LocalDateTime.of(2021, Month.DECEMBER, 27, 16, 30, 00))
                .withProcedures(procedures)
                .withAmount(2500)
                .build();
        orders.add(order);
        return orders;
    }    
}
