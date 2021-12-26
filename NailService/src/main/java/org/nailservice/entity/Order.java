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

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@Builder(setterPrefix = "with")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_customer", referencedColumnName = "customer_id")
    private Customer customer;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "orders_to_procedures", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "procedure_id"))
    private Set<Procedure> procedures;

    @Column(name = "order_start", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime start;

    @Column(name = "order_end", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime end;

    @Column(name = "order_amount", nullable = false)
    private Integer amount;
}
