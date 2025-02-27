package com.ninjaone.backendinterviewproject.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Cost")
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

    public Cost(Device device, Double value, Service service) {
        this.device = device;
        this.value = value;
        this.service = service;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
