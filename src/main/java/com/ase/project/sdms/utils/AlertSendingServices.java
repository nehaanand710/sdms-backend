package com.ase.project.sdms.utils;

import com.ase.project.sdms.config.TwilioConfiguration;
import com.ase.project.sdms.dto.SmsRequestDto;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class AlertSendingServices {

    private final TwilioConfiguration twilioConfiguration;

    public AlertSendingServices(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
    }

    public void sendSms(SmsRequestDto smsRequest) {
        try {
            com.twilio.type.PhoneNumber to = new PhoneNumber("+919999257009");
            com.twilio.type.PhoneNumber from = new PhoneNumber("+19016576642");
            String message = smsRequest.getMessage();
            MessageCreator creator = Message.creator(to, from, message);
            creator.create();
            System.out.println("Message sent successfully");
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Phone number [" + smsRequest.getPhoneNumber() + "] is not a valid number ******"  + e.getMessage()
            );
        }

    }
}
