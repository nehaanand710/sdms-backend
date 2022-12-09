package com.ase.project.sdms.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimulationDTO {

   String sensorId;

   String sensorTypeId;

   Float sensorHealth;

   String username;
}
