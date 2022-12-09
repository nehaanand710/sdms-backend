package com.ase.project.sdms.controller.user;

import com.ase.project.sdms.dto.*;
import com.ase.project.sdms.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/updateProfile")
    public String updateProfile(@Valid @RequestBody UserProfileDTO profileDTO) {
        return userService.updateProfile(profileDTO);
    }

    @PostMapping("/register")
    public String registerUser(@Valid @RequestBody UserProfileDTO profileDTO) {
        return userService.addUser(profileDTO);
    }

    @PostMapping("/saveHazards")
    public String saveHazards() {
        return userService.saveHazards();
    }

    @PostMapping("/saveBasicInfo")
    public String saveBasicInfo() {
        return userService.saveUserSensorAndAction();
    }

    @PostMapping("/forgotPassword")
    public String forgotPassword(@RequestBody UsernameDto usernameDto) {
        return userService.forgotPassword(usernameDto);
    }

    @PutMapping("/resetPassword")
    public String resetPassword(@RequestParam("token") String token, @Valid @RequestBody PasswordDTO passwordDTO) {
        return userService.validateAndResetPassword(token, passwordDTO);
    }

    @PostMapping("/oauth/token")
    public AccessTokenDto oauthToken(@RequestPart String username, @RequestPart String grant_type, @RequestPart String client_id, @RequestPart String client_secret, @RequestPart String password) {
        return userService.oauthToken(username, grant_type, client_id, client_secret, password);
    }

}
