package org.nailservice.entity;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_customer", referencedColumnName = "customer_id")
    Customer customer;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "orders_to_nailservices", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "nailservice_id"))
    Set<NailOperation> nailServices;

    @Column(name = "order_start", columnDefinition = "TIMESTAMP", nullable = false)
    LocalDateTime start;

    @Column(name = "order_end", columnDefinition = "TIMESTAMP", nullable = false)
    LocalDateTime end;
}
