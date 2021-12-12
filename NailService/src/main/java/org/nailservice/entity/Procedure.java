package org.nailservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "procedures")
@Data
@Builder(setterPrefix = "with")
public class Procedure {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "procedure_id", unique = true, nullable = false)
    private Integer id;
    
    @Column(name = "procedure_name", nullable = false)
    private String name;
    
    @Column(name = "procedure_cost", nullable = false)
    private Integer cost;
    
    @Column(name = "procedure_duration", nullable = false)
    private Integer duration;
}
