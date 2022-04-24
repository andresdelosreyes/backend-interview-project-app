package com.ninjaone.backendinterviewproject.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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

}
