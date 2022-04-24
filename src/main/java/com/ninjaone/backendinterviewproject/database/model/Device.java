package com.ninjaone.backendinterviewproject.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Device")
public class Device {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "system_name")
    private String systemName;

    @Column(name = "type")
    private DeviceTypeEnum type;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "device")
    private Set<Cost> cost;

    @Column(name = "active")
    private Boolean active;

    public Device() {
    }

    public Device(String systemName, DeviceTypeEnum type) {
        this.systemName = systemName;
        this.type = type;
        this.cost = new HashSet<>();
        this.active = ActiveEnum.YES.getValue();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public DeviceTypeEnum getType() {
        return type;
    }

    public void setType(DeviceTypeEnum type) {
        this.type = type;
    }

    public Set<Cost> getCost() {
        return cost;
    }

    public void setCost(Set<Cost> cost) {
        this.cost = cost;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
