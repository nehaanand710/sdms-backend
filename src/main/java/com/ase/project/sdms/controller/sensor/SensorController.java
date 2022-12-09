package com.ase.project.sdms.controller.sensor;

import com.ase.project.sdms.dto.*;
import com.ase.project.sdms.service.sensor.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class SensorController {

    @Autowired
    SensorService sensorService;

    @PostMapping("/addSensor")
    public String addSensor(@Valid @RequestBody SensorDto sensorDto) {
        return sensorService.addSensor(sensorDto);
    }

    @PostMapping("/updateSensor")
    public String updateSensor(@Valid @RequestBody SensorUpdateDto sensorDto) {
        return sensorService.updateSensor(sensorDto);
    }

    @PostMapping("/deleteSensor")
    public String deleteSensor(@Valid @RequestBody DeleteSensorDto deleteSensorDto) {
        return sensorService.deleteSensor(deleteSensorDto);
    }

    @PostMapping("/getSensorList")
    public List<UserSensorDto> sensorList(@Valid @RequestBody UserIdDto userId){
        return sensorService.getSensorList(userId);
    }
}
