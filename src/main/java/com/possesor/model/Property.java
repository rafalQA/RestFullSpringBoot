package com.possesor.model;

/**
 * Created by Rafal Piotrowicz on 19.12.2016.
 */
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Property {

    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private BigDecimal value;
    @ManyToOne
    @JoinColumn(name ="id")
    private User user;

}
