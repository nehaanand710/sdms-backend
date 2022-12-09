package com.ase.project.sdms.service.sensor;

import com.ase.project.sdms.domain.Sensor;
import com.ase.project.sdms.dto.*;
import com.ase.project.sdms.repository.SensorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SensorServiceImpl implements SensorService {

    @Autowired
    SensorRepository sensorRepository;

    private static final Logger logger = LoggerFactory.getLogger(SensorServiceImpl.class);



    public String addSensor(SensorDto sensorDto) {
        try {
            Sensor sensor = new Sensor();
            sensor.setUserId(sensorDto.getUserId());
            sensor.setSensorLocation(sensorDto.getSensorLocation());
            //sensor status is interchangeably used for sensorType
            sensor.setSensorTypeId(sensorDto.getSensorHealth());
            sensor.setSensorStatus(1);
            sensor.setSensorHealth(true);
            sensor.setSensorTypeId(sensorDto.getSensorTypeId());

           // sensor.setSensorTypeId();
            sensor.setSensorName(sensorDto.getSensorName());
            sensorRepository.save(sensor);
            return "Sensor Added Successfully";
        } catch (Exception e) {
            logger.error("Exception occurred while adding the Sensor details "+sensorDto);
            logger.error("Exception occurred while adding the Sensor details with the exception as "+e.getStackTrace());
            return "Error While saving Data";
        }
    }

    public String updateSensor(SensorUpdateDto sensorDto) {
        try {
            Sensor sensor = sensorRepository.findById(sensorDto.getSensorId()).get();
            if (sensor == null)
                return "No sensor found of given id";
            sensor.setUserId(sensorDto.getUserId());
            sensor.setSensorLocation(sensorDto.getSensorLocation());
            sensor.setSensorStatus(sensor.getSensorStatus());
            sensor.setSensorHealth(sensor.getSensorHealth());
            sensor.setSensorName(sensorDto.getSensorName());
            sensorRepository.save(sensor);
            return "Sensor Update Successfully";
        } catch (Exception e) {
            return "Error While Updating Sensor Data";
        }
    }

    public String deleteSensor(DeleteSensorDto deleteSensorDto) {
        try {
            Sensor sensor = sensorRepository.getSensorData(deleteSensorDto.getUserId(), deleteSensorDto.getSensorId());
            if (sensor == null)
                return "No sensor found of given id";
            sensor.setSensorStatus(0);
            sensorRepository.save(sensor);
            return "Sensor Deleted Successfully";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Error while deleting Sensor";
        }
    }

    public List<UserSensorDto> getSensorList(UserIdDto userId) {
        try {
            List<Sensor> sensorList = sensorRepository.getSensorList(userId.getUserId());
            List<UserSensorDto> userSensorDtoList = new ArrayList<>();
            if (sensorList.isEmpty())
                return new ArrayList<>();
            for (Sensor sensor : sensorList) {
                if (sensor.getSensorStatus() == 1) {
                    UserSensorDto userSensorDto = new UserSensorDto();
                    userSensorDto.setSensorId(sensor.getId());
                    userSensorDto.setUserId(sensor.getUserId());
                    userSensorDto.setSensorHealth(sensor.getSensorHealth());
                    userSensorDto.setSensorName(sensor.getSensorName());
                    userSensorDto.setSensorLocation(sensor.getSensorLocation());
                    userSensorDtoList.add(userSensorDto);
                }
            }
            return userSensorDtoList;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }


}