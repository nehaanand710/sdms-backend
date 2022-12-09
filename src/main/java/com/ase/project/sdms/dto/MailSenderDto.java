package com.ase.project.sdms.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MailSenderDto {

    private String to;
    private String subject;
    private String message;
}
