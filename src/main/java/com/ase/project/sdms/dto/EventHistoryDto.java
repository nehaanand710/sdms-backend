package com.ase.project.sdms.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventHistoryDto {
    Integer eventId;
    String EventMessage;
    String happenOn;
    String actionMessage;
    String sensorName;
    Integer sensorId;
    String actionTaken;
}
