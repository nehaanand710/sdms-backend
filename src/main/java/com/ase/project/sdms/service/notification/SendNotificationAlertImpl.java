package com.ase.project.sdms.service.notification;

import com.ase.project.sdms.domain.EventHistory;
import com.ase.project.sdms.domain.Sensor;
import com.ase.project.sdms.domain.User;
import com.ase.project.sdms.dto.AlertSendingDto;
import com.ase.project.sdms.dto.MailSenderDto;
import com.ase.project.sdms.dto.SmsRequestDto;
import com.ase.project.sdms.repository.EventHistoryRepository;
import com.ase.project.sdms.repository.SensorRepository;
import com.ase.project.sdms.repository.UserRepository;
import com.ase.project.sdms.utils.AlertSendingServices;
import com.ase.project.sdms.utils.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendNotificationAlertImpl implements SendNotificationAlert {

    @Autowired
    AlertSendingServices alertSendingServices;

    @Autowired
    MailSenderService mailSenderService;

    @Autowired
    EventHistoryRepository eventHistoryRepository;

    @Autowired
    SensorRepository sensorRepository;

    @Autowired
    UserRepository userRepository;

    public String sendOrIgnoreAlert(AlertSendingDto alertSendingDto) {
        User user = userRepository.findById(alertSendingDto.getUserId()).get();
        EventHistory eventHistory = eventHistoryRepository.findById(alertSendingDto.getEventId()).get();

        if (alertSendingDto.getTakeAction()) {
            try {
                SmsRequestDto smsRequestDto = new SmsRequestDto();
                smsRequestDto.setPhoneNumber(user.getContactNo());
                smsRequestDto.setMessage(eventHistory.getEventMessage());
                alertSendingServices.sendSms(smsRequestDto);
                Sensor sensor = sensorRepository.findById(alertSendingDto.getSensorId()).get();
                sensor.setSensorHealth(true);
                sensorRepository.save(sensor);
                //eventHistory.setActionTaken("Action Taken");
                eventHistoryRepository.save(eventHistory);
                MailSenderDto mailSenderDto = new MailSenderDto();
                mailSenderDto.setTo(user.getUsername());
                mailSenderDto.setSubject("Action Taken");
                mailSenderDto.setMessage("Hi " + user.getFirstName() + ", This is to inform you that appropriate measures have been taken to resolve the disaster occurred at your place");
                mailSenderService.sendEmail(mailSenderDto);
                return "Alert Sent Successfully";

            } catch (Exception e) {
                throw new RuntimeException("Error while sending alerts");
            }
        } else {
            Sensor sensor = sensorRepository.findById(alertSendingDto.getSensorId()).get();
            sensor.setSensorHealth(true);
            sensorRepository.save(sensor);
            eventHistory.setActionTaken("Ignore");
            eventHistoryRepository.save(eventHistory);
            MailSenderDto mailSenderDto = new MailSenderDto();
            mailSenderDto.setTo(user.getUsername());
            mailSenderDto.setSubject("Action Taken");
            mailSenderDto.setMessage("Hi " + user.getFirstName() + ", This is to inform you that appropriate measures have been taken to resolve the disaster occurred at your place");
            mailSenderService.sendEmail(mailSenderDto);
            return "Alert Ignored";
        }

    }

}
