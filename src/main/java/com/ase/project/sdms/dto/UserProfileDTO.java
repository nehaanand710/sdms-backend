package com.ase.project.sdms.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileDTO {

    String username;
    String firstName;
    String lastName;
    String address;
    String emergencyNumber;
    String contactNo;
    String password;
}
