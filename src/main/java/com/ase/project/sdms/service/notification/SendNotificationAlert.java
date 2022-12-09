package com.ase.project.sdms.service.notification;

import com.ase.project.sdms.dto.AlertSendingDto;
import org.springframework.stereotype.Service;

@Service
public interface SendNotificationAlert {
    String sendOrIgnoreAlert(AlertSendingDto alertSendingDto);
}
