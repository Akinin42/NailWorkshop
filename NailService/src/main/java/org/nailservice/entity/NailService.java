package org.nailservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "nailservices")
@Data
public class NailService {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "nailservice_id", unique = true, nullable = false)
    private Integer id;
    
    @Column(name = "nailservice_name", nullable = false)
    private String name;
    
    @Column(name = "nailservice_cost", nullable = false)
    private Integer cost;
    
    @Column(name = "nailservice_duration", nullable = false)
    private Integer duration;
}
