package com.ase.project.sdms.service.dashboard;

import com.ase.project.sdms.domain.*;
import com.ase.project.sdms.dto.*;
import com.ase.project.sdms.exceptions.SensorNotFoundException;
import com.ase.project.sdms.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    private static final Logger logger = LoggerFactory.getLogger(DashboardServiceImpl.class);


    @Autowired
    SensorRepository sensorRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    EventHistoryRepository eventHistoryRepository;

    @Autowired
    SensorTypeRepository sensorTypeRepository;

    /***
     * This function is use to test our application whether its working fine with CI/CD or not.
     * @return
     */
    public String dashboardIndex() {
        return "Welcome To Smart Disaster Management System";
    }


    public TestResponseDto testResponse() {
        TestResponseDto testResponseDto = new TestResponseDto();
        testResponseDto.setStatus("Working");
        return testResponseDto;
    }

    /**
     * It is used simulate the data which will actually come from the sensors and save it in DB
     *
     * @return
     */
    @Override
    public String simulateSensors(SimulationDTO simulation) {
        User user = userRepository.findByUsername(simulation.getUsername());

        Sensor sensor = sensorRepository.getSensorData(user.getId(),Integer.parseInt(simulation.getSensorId()));
        String response = null;
        if (null == sensor) {
            try {
                throw new SensorNotFoundException("Sensor specified does not exist");
            } catch (SensorNotFoundException e) {
                logger.error("No sensor exists for the user "+simulation.getUsername()+ " with sensor id "+ simulation.getSensorId());
                // throw new RuntimeException(e);
                return null;
            }
        }

        EventHistory eventHistory = new EventHistory();
        switch (Integer.parseInt(simulation.getSensorTypeId())) {
            case 1:

                if (simulation.getSensorHealth() == 1) {

                    eventHistory.setActionMessage("Notify 911");
                    eventHistory.setEventMessage("Fire occurred for sensor " + sensor.getSensorName() + " at " + sensor.getSensorLocation());
                    eventHistory.setSensorId(Integer.parseInt(simulation.getSensorId()));
                    eventHistory.setActionTaken("N");
                    Date todaysDate = new Date();
                    eventHistory.setCreatedDate(new Timestamp(todaysDate.getTime()));
                    eventHistory.setUserId(user.getId());
                    eventHistory.setSensorValue(simulation.getSensorHealth());
                    sensor.setSensorHealth(false);
                    eventHistory = eventHistoryRepository.save(eventHistory);
                    response = "Data simulated successfully for Fire Sensor";
                    callSmsAlertAPI(simulation, user, eventHistory);
                }
                else {
                    setHealthyEvent(simulation, user, eventHistory);
                    response = "Data simulated successfully for  Healthy Fire Sensor";

                }

                break;
            case 2:

                if (simulation.getSensorHealth() == 1) {
                    eventHistory.setActionMessage("Notify Plumber");
                    eventHistory.setEventMessage("WaterOverFlow occurred for sensor " + sensor.getSensorName() + " at " + sensor.getSensorLocation());
                    eventHistory.setSensorId(Integer.parseInt(simulation.getSensorId()));

                    Date todaysDate = new Date();
                    eventHistory.setCreatedDate(new Timestamp(todaysDate.getTime()));
                    eventHistory.setUserId(user.getId());
                    eventHistory.setActionTaken("N");
                   // eventHistory.setActionMessage("Call Plumber as WaterOverflow Occurred at " + sensor.getSensorLocation() + " for " + sensor.getSensorName());
                    sensor.setSensorHealth(false);
                    eventHistory.setSensorValue(simulation.getSensorHealth());
                    eventHistory =eventHistoryRepository.save(eventHistory);
                    response = "Data simulated successfully for WaterOverflow Sensor";
                    callSmsAlertAPI(simulation, user, eventHistory);
                }

                else {
                    setHealthyEvent(simulation,user,eventHistory);
                    response = "Data simulated successfully for Healthy WaterOverflow Sensor";

                }

                break;
            case 3:

                if (simulation.getSensorHealth() == 1) {
                    eventHistory.setActionMessage("Notify Electrician");
                    eventHistory.setEventMessage("ShortCircuit occurred for sensor " + sensor.getSensorName() + " at " + sensor.getSensorLocation());
                    eventHistory.setSensorId(Integer.parseInt(simulation.getSensorId()));

                    Date todaysDate = new Date();
                    eventHistory.setCreatedDate(new Timestamp(todaysDate.getTime()));
                    eventHistory.setUserId(user.getId());
                    eventHistory.setActionTaken("N");
                    //eventHistory.setActionMessage("Call Electrician as ShortCircuit Occurred at " + sensor.getSensorLocation() + " for " + sensor.getSensorName());
                    sensor.setSensorHealth(false);
                    eventHistory.setSensorValue(simulation.getSensorHealth());
                    eventHistory =eventHistoryRepository.save(eventHistory);
                    response = "Data simulated successfully for Shortcircuit Sensor";
                    callSmsAlertAPI(simulation, user, eventHistory);
                }

                else {
                    setHealthyEvent(simulation, user, eventHistory);
                    response = "Data simulated successfully for Healthy Shortcircuit Sensor";

                }
                break;

            case 4:

                if (simulation.getSensorHealth() <8 || simulation.getSensorHealth() > 26) {

                    eventHistory.setActionMessage("Notify Maintenance");
                    eventHistory.setEventMessage("Temperature spike  for sensor " + sensor.getSensorName() + " at " + sensor.getSensorLocation());
                    eventHistory.setSensorId(Integer.parseInt(simulation.getSensorId()));

                    Date todaysDate = new Date();
                    eventHistory.setCreatedDate(new Timestamp(todaysDate.getTime()));
                    eventHistory.setUserId(user.getId());
                    eventHistory.setActionTaken("N");
                    //eventHistory.setActionMessage("Check physically the temperature sensor at " + sensor.getSensorLocation() + " for " + sensor.getSensorName());
                    sensor.setSensorHealth(false);
                    eventHistory.setSensorValue(simulation.getSensorHealth());
                    eventHistory = eventHistoryRepository.save(eventHistory);
                    response = "Data simulated successfully for Temperature Sensor";
                    callSmsAlertAPI(simulation, user, eventHistory);
                }
                else {
                    setHealthyEvent(simulation, user, eventHistory);
                    response = "Data simulated successfully for Healthy Temperature Sensor";
                }
                break;
            case 5:

                if (simulation.getSensorHealth() > 101) {

                    eventHistory.setActionMessage("Notify Maintenance");
                    eventHistory.setEventMessage("CO2 levels have risen for sensor " + sensor.getSensorName() + " at " + sensor.getSensorLocation());
                    eventHistory.setSensorId(Integer.parseInt(simulation.getSensorId()));

                    Date todaysDate = new Date();
                    eventHistory.setCreatedDate(new Timestamp(todaysDate.getTime()));
                    eventHistory.setUserId(user.getId());
                    eventHistory.setActionTaken("N");
                   // eventHistory.setActionMessage("Check physically the smoke detector  at " + sensor.getSensorLocation() + " for " + sensor.getSensorName());
                    sensor.setSensorHealth(false);
                    eventHistory.setSensorValue(simulation.getSensorHealth());
                    eventHistory =eventHistoryRepository.save(eventHistory);
                    response = "Data simulated successfully for Smoke Detector Sensor";
                    callSmsAlertAPI(simulation, user, eventHistory);
                }

                else {
                    setHealthyEvent(simulation, user, eventHistory);
                    response = "Data simulated successfully for Smoke Detector Sensor";
                }
                break;
        }
        if (response.isEmpty()) {
            response ="No event occurred as sensor  values within limits ";
        }
        return response ;
    }

    private static void callSmsAlertAPI(SimulationDTO simulation, User user, EventHistory eventHistory) {
        RestTemplate restTemplate = new RestTemplate();
        //String urlForSendingSMS = "http://localhost:8080/api/sendOrIgnoreAlert";
        String urlForSendingSMS = "https://sdms2.tk/api/sendOrIgnoreAlert";
        AlertSendingDto alertSendingDto = new AlertSendingDto();
        alertSendingDto.setSensorId(Integer.parseInt(simulation.getSensorId()));
        alertSendingDto.setTakeAction(true);
        alertSendingDto.setUserId(user.getId());
        alertSendingDto.setEventId(eventHistory.getId());
        restTemplate.postForObject(urlForSendingSMS,alertSendingDto,String.class);
    }

    private void setHealthyEvent(SimulationDTO simulation, User user, EventHistory eventHistory) {
        Date todaysDate = new Date();
        eventHistory.setCreatedDate(new Timestamp(todaysDate.getTime()));
        eventHistory.setActionTaken("Y");
        eventHistory.setActionMessage("No action to be taken");
        eventHistory.setEventMessage("Sensor Healthy");
        eventHistory.setSensorId(Integer.parseInt(simulation.getSensorId()));
        eventHistory.setUserId(user.getId());
        eventHistory.setSensorValue(simulation.getSensorHealth());

        eventHistoryRepository.save(eventHistory);
    }

    public DashboardDto dashboardData(UsernameDto usernameDto) {
        User user = userRepository.findByUsername(usernameDto.getUsername());
        System.out.println(user.getPassword());
        DashboardDto dashboardDto = new DashboardDto();
        dashboardDto.setUser(user);
        List<EventHistoryDto> newEvent = new ArrayList<>();
        List<EventHistoryDto> eventHistoryDtoList = new ArrayList<>();
        List<SensorType>  sensorTypeList =  sensorTypeRepository.findAll();
        logger.info("Fetched the sensorType List with "+sensorTypeList.size()+ " elements");
        List<EventHistory> eventHistoryList = eventHistoryRepository.getEventHistory(user.getId());

        for (EventHistory eventHistory : eventHistoryList) {
            if ((new Date().getTime() / 1000 - eventHistory.getCreatedDate().getTime() / 1000) <= 5) {
                Sensor sensor = sensorRepository.findById(eventHistory.getSensorId()).get();
                EventHistoryDto eventHistoryDto = new EventHistoryDto();
                eventHistoryDto.setEventId(eventHistory.getId());
                eventHistoryDto.setSensorName(sensor.getSensorName());
                eventHistoryDto.setHappenOn(eventHistory.getCreatedDate().toString());
                eventHistoryDto.setEventMessage(eventHistory.getEventMessage());
                eventHistoryDto.setActionMessage(eventHistory.getActionMessage());
                eventHistoryDto.setSensorId(sensor.getId());
                eventHistoryDto.setActionTaken(eventHistory.getActionTaken());
                newEvent.add(eventHistoryDto);
            } else {
                Sensor sensor = sensorRepository.findById(eventHistory.getSensorId()).get();
                EventHistoryDto eventHistoryDto = new EventHistoryDto();
                eventHistoryDto.setEventId(eventHistory.getId());
                eventHistoryDto.setSensorName(sensor.getSensorName());
                eventHistoryDto.setHappenOn(eventHistory.getCreatedDate().toString());
                eventHistoryDto.setEventMessage(eventHistory.getEventMessage());
                eventHistoryDto.setActionMessage(eventHistory.getActionMessage());
                eventHistoryDto.setSensorId(sensor.getId());
                eventHistoryDto.setActionTaken(eventHistory.getActionTaken());
                eventHistoryDtoList.add(eventHistoryDto);
            }

        }

        UserIdDto userIdDto = new UserIdDto();
        userIdDto.setUserId(user.getId());

        List<UserSensorDto> userSensorDtoList = getSensorList(userIdDto);
        dashboardDto.setSensorList(userSensorDtoList);
        dashboardDto.setNewEvent(newEvent);
        dashboardDto.setEventHistoryList(eventHistoryDtoList);
        dashboardDto.setSensorTypes(sensorTypeList);
        return dashboardDto;
    }

    @Override
    public String updateEventHistoryActionFlag(EventHistoryUpdateDto actionValue) {
        EventHistory event = eventHistoryRepository.findByEventId(actionValue.getEventId());
        if (null == event) {
            logger.error("No such event present");
            return "No such event occurs for the user";
        }
        eventHistoryRepository.updateActionMessage(actionValue.getActionTaken(),actionValue.getEventId());
        return "Successfully updated the actionMessage for the eventHistory";
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
