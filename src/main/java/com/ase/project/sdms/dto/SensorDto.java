package com.ase.project.sdms.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SensorDto {
    Integer userId;
    String sensorName;
    String sensorLocation;
    String sensorHealth;
    String sensorTypeId;
}
