package com.ninjaone.backendinterviewproject.database.model;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class Cost {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "device_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Device device;

    @Column(name = "value")
    private Double value;

    @JoinColumn(name = "service_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Service service;

    public Cost(){
    }


}
