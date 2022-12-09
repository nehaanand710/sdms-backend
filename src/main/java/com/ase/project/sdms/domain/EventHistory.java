package com.ase.project.sdms.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class EventHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    Integer userId;

    Integer sensorId;

    @Temporal(TemporalType.TIMESTAMP)
    Date createdDate;

    String eventMessage;

    String actionMessage;

    String actionTaken;

    Float sensorValue;
}
