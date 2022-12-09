package com.ase.project.sdms.service.user;


import com.ase.project.sdms.domain.*;
import com.ase.project.sdms.dto.*;
import com.ase.project.sdms.repository.*;
import com.ase.project.sdms.domain.AppUser;
import com.ase.project.sdms.domain.ConfirmationToken;
import com.ase.project.sdms.domain.GrantAuthorityImpl;
import com.ase.project.sdms.domain.User;
import com.ase.project.sdms.exceptions.PasswordAndConfirmPasswordMismatchException;
import com.ase.project.sdms.exceptions.UserNotFoundException;
import com.ase.project.sdms.repository.ConfirmationTokenRepository;
import com.ase.project.sdms.repository.UserRepository;
import com.ase.project.sdms.utils.AlertSendingServices;
import com.ase.project.sdms.utils.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collections;

import java.util.Calendar;

@Repository
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

     @Autowired
    SensorRepository sensorRepository;

    @Autowired
    UserSensorActionRepository userSensorActionRepository;

    @Autowired
    EventHistoryRepository eventHistoryRepository;

    @Autowired
    MailSenderService mailSenderService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;


    @Autowired
    AlertSendingServices alertSendingServices;


    /***
     * This function will be use to add a new user in the system
     * @param userProfileDTO
     * @return
     */
    public String addUser(UserProfileDTO userProfileDTO) {
        User user = userRepository.findByUsername(userProfileDTO.getUsername());
        if (user == null) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            User newUser = new User();
            newUser.setUsername(userProfileDTO.getUsername());
            newUser.setAddress(userProfileDTO.getAddress());
            newUser.setEmergencyNumber(userProfileDTO.getEmergencyNumber());
            newUser.setPassword(passwordEncoder.encode(userProfileDTO.getPassword()));
            newUser.setRole("ROLE_USER");
            newUser.setFirstName(userProfileDTO.getFirstName());
            newUser.setLastName(userProfileDTO.getLastName());
            newUser.setContactNo(userProfileDTO.getContactNo());
            userRepository.save(newUser);
            MailSenderDto mailSenderDto = new MailSenderDto();
            mailSenderDto.setTo(userProfileDTO.getUsername());
            mailSenderDto.setSubject("Registration successfully Completed !!");
            mailSenderDto.setMessage("You have successFully Registered for Smart Disaster Management System");
            mailSenderService.sendEmail(mailSenderDto);

            return "New Customer name - " + userProfileDTO.getFirstName() + " has been added into database successfully";
        } else {
            throw new RuntimeException("User already exist with given username");
        }
    }

    /**
     * This function will be used to update user's profile information
     *
     * @param userProfileDTO
     * @return
     */
    public String updateProfile(UserProfileDTO userProfileDTO) {
        User user = userRepository.findByUsername(userProfileDTO.getUsername());
        if (user != null) {
            user.setFirstName(userProfileDTO.getFirstName());
            user.setLastName(userProfileDTO.getLastName());
            user.setEmergencyNumber(userProfileDTO.getEmergencyNumber());
            user.setAddress(userProfileDTO.getAddress());
            user.setContactNo(userProfileDTO.getContactNo());
            userRepository.save(user);
            MailSenderDto mailSenderDto = new MailSenderDto();
            mailSenderDto.setTo(userProfileDTO.getUsername());
            mailSenderDto.setSubject("Profile Updation successfully Completed !!");
            mailSenderDto.setMessage("You have successFully Updated your profile. Thanks for using Smart Disaster Management System");
            mailSenderService.sendEmail(mailSenderDto);
            return "Data Updated";
        } else {
            throw new RuntimeException("No User Found of Given Username");
        }

    }

    /***
     * After authenticating this function will return the user details
     * @param username
     * @return
     */
    public AppUser loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        System.out.println(user);
        if (username != null) {
            return new AppUser(user.getUsername(), user.getPassword(), Collections.singletonList(new GrantAuthorityImpl(user.getRole())));
        } else {
            throw new RuntimeException();
        }
    }

    /***
     * This function will save the data in user, sensor and action tables
     * @return
     */
    public String saveUserSensorAndAction() {

        try {

           /** Action action = new Action();
            Action action2 = new Action();
            Action action3 = new Action();
            Action action4 = new Action();
            Action action5 = new Action();
            Sensor sensor = new Sensor();

            sensor.setSensorName("Fire Alarm");
            sensor.setUserId(1);
            sensor.setSensorLocation("Bedroom_1");
            sensor.setSensorStatus(1);

            action.setActionDescription("Fire Alarm Actions");
            action2.setActionDescription("Smoke Alarm Actions");
            action3.setActionDescription("Electricity Leakage Actions");
            action4.setActionDescription("Water Overflow Actions");
            action5.setActionDescription("Intrusion Actions");

            actionRepository.save(action);
            actionRepository.save(action2);
            actionRepository.save(action3);
            actionRepository.save(action4);
            actionRepository.save(action5);
            sensorRepository.save(sensor);
            return "Data saved successfully";
            **/
           return null;
        } catch (Exception e) {
            throw new RuntimeException("Exception while saving generic data");
        }
    }


    /***
     * This function will save the disaster occurred in the hard code format
     * @return
     */
    public String saveHazards() {

        try {
            EventHistory eventHistory = new EventHistory();
            UserSensorAction userSensorAction = new UserSensorAction();

            eventHistory.setSensorId(sensorRepository.findById(1).get().getId());
            eventHistory.setEventMessage("Fire in the Bedroom 1");

            userSensorAction.setUserId(userRepository.findByUsername("user@gmail.com").getId());
            userSensorAction.setActionId(1);
            userSensorAction.setActionTaken(1);
            userSensorAction.setSensorId(sensorRepository.findById(1).get().getId());

            eventHistoryRepository.save(eventHistory);
            userSensorActionRepository.save(userSensorAction);

            return "Hazards created";
        } catch (Exception e) {
            throw new RuntimeException("Error while saving Hazards");
        }

    }

    /**
     * This function will be use to generate token and bind with the link which will
     * send to user on his email address saved in database.
     *
     * @param username
     * @return
     */
    public String forgotPassword(UsernameDto username) {
        User user = userRepository.findByUsername(username.getUsername());
        if (user == null) {
            throw new UserNotFoundException("User not Found");
        } else {
            // Create token
            ConfirmationToken confirmationToken = new ConfirmationToken(user);
            // Saved
            confirmationTokenRepository.save(confirmationToken);
            // Send message
            mailSenderService.sendPasswordResetTokenMail(user);
        }
        return "Link to reset your password sent to your email..";
    }

    /***
     * This function first validate whether the token is active or not.
     * If its active then it will check whether the password and confirm password both are equal or not.
     * If it's a case then the password will get update into the database
     * @param token
     * @param passwordDTO
     * @return
     */
    public String validateAndResetPassword(String token, PasswordDTO passwordDTO) {
        if (!passwordDTO.getPassword().equals(passwordDTO.getConfirmPassword())) {
            throw new PasswordAndConfirmPasswordMismatchException("Password and confirm password did not match");
        }
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByConfirmationToken(token);
        Calendar cal = Calendar.getInstance();
        if (confirmationToken == null) {
            return "Invalid token";
        } else if ((confirmationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            confirmationTokenRepository.deleteByConfirmationToken(token);
            return "Token Expired";
        } else {
            User user = confirmationToken.getUser();
            try {
                user.setPassword(passwordEncoder.encode(passwordDTO.getPassword()));
                userRepository.save(user);
                MailSenderDto mailSenderDto = new MailSenderDto();
                mailSenderDto.setTo(user.getUsername());
                mailSenderDto.setSubject("Password Updated successfully !!");
                mailSenderDto.setMessage("You have successFully Updated your password. Thanks for using Smart Disaster Management System");
                mailSenderService.sendEmail(mailSenderDto);
                return "Password Updated successfully";
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new RuntimeException("Exception while resetting Password");
            }

        }
    }

    public AccessTokenDto oauthToken(String username, String grant_type, String client_id, String client_secret, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (passwordEncoder.matches(password, user.getPassword())){
                AccessTokenDto tokenDto = new AccessTokenDto();
                tokenDto.setAccess_token("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NzE5NDI0OTksInVzZXJfbmFtZSI6Im1hbnZpcjk4MjRAZ21haWwuY29tIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImp0aSI6ImQ5ZDU2MDljLTcwMjgtNGY0Ny1iYjc1LTZjNjE2YTMzZDRhNiIsImNsaWVudF9pZCI6ImxpdmUtdGVzdCIsInNjb3BlIjpbImFwcCJdfQ.QhNu_CjUv6XY81FHrb4_bWtKk_SbCS8ff80K-WiPU20");
                tokenDto.setToken_type("bearer");
                return tokenDto;
            } else
                throw new UnauthorizedUserException("Bad Credentials");
        } else
            throw new UserNotFoundException("User is not Registered");
    }


}
