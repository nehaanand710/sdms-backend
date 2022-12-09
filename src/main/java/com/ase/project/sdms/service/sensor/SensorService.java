package com.ase.project.sdms.service.sensor;

import com.ase.project.sdms.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SensorService {
    String addSensor(SensorDto sensorDto);

    String updateSensor(SensorUpdateDto sensorDto);

    String deleteSensor(DeleteSensorDto deleteSensorDto);

    List<UserSensorDto> getSensorList(UserIdDto userId);
}
