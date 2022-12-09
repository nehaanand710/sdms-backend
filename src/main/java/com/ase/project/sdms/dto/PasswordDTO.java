package com.ase.project.sdms.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PasswordDTO {
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,15}$", message = "8-15 Characters with atleast 1 Lower case, 1 Upper case, 1 Special Character, 1 Number")
    @NotNull(message = "Password cannot be null!")
    String password;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,15}$", message = "8-15 Characters with atleast 1 Lower case, 1 Upper case, 1 Special Character, 1 Number")
    @NotNull(message = "Password cannot be null!")
    String confirmPassword;
}