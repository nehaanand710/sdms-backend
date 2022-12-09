package com.ase.project.sdms.dto;

import com.ase.project.sdms.domain.SensorType;
import com.ase.project.sdms.domain.User;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DashboardDto {
    User user;
    List<EventHistoryDto> newEvent;
    List<EventHistoryDto> eventHistoryList;
    List<UserSensorDto> sensorList;
    List<SensorType> sensorTypes;
}
