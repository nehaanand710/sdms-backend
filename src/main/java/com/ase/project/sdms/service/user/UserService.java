package com.ase.project.sdms.service.user;

import com.ase.project.sdms.domain.AppUser;
import com.ase.project.sdms.dto.AccessTokenDto;
import com.ase.project.sdms.dto.PasswordDTO;
import com.ase.project.sdms.dto.UserProfileDTO;
import com.ase.project.sdms.dto.UsernameDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    String updateProfile(UserProfileDTO userProfileDTO);

    String addUser(UserProfileDTO userProfileDTO);

    AppUser loadUserByUsername(String username);

    String saveHazards();

    String saveUserSensorAndAction();

    String forgotPassword(UsernameDto username);

    String validateAndResetPassword(String token, PasswordDTO passwordDTO);

    AccessTokenDto oauthToken(String username, String grant_type, String client_id, String client_secret, String password);
}
