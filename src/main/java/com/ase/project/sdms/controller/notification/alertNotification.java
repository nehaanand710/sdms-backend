package com.ase.project.sdms.controller.notification;


import com.ase.project.sdms.dto.AlertSendingDto;
import com.ase.project.sdms.dto.SensorDto;
import com.ase.project.sdms.service.notification.SendNotificationAlert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class alertNotification {

    @Autowired
    SendNotificationAlert sendNotificationAlert;

    @PostMapping("/sendOrIgnoreAlert")
    public String sendOrIgnoreAlert(@Valid @RequestBody AlertSendingDto alertSendingDto) {
        return sendNotificationAlert.sendOrIgnoreAlert(alertSendingDto);
    }

}
