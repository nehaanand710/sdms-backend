package com.ase.project.sdms.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    Integer userId;
    String sensorName;
    String sensorLocation;
    Integer sensorStatus;
    Boolean sensorHealth;
    //@OneToOne(cascade = CascadeType.ALL)
    //@JoinColumn
    String sensorTypeId;

}
