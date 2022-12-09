package com.ase.project.sdms.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AlertSendingDto {
    Integer userId;
    Integer sensorId;
    Integer eventId;
    Boolean takeAction;
}
