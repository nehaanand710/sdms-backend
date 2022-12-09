package com.ase.project.sdms.controller.user;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.ase.project.sdms.dto.UserProfileDTO;
import com.ase.project.sdms.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link UserController#updateProfile(UserProfileDTO)}
     */
    @Test
    void testUpdateProfile() throws Exception {
        when(userService.updateProfile((UserProfileDTO) any())).thenReturn("2020-03-01");

        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setAddress("42 Main St");
        userProfileDTO.setContactNo("Contact No");
        userProfileDTO.setEmergencyNumber("42");
        userProfileDTO.setFirstName("Jane");
        userProfileDTO.setLastName("Doe");
        userProfileDTO.setPassword("iloveyou");
        userProfileDTO.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(userProfileDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/updateProfile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("2020-03-01"));
    }

    /**
     * Method under test: {@link UserController#registerUser(UserProfileDTO)}
     */
    @Test
    void testRegisterUser() throws Exception {
        when(userService.addUser((UserProfileDTO) any())).thenReturn("Add User");

        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setAddress("42 Main St");
        userProfileDTO.setContactNo("Contact No");
        userProfileDTO.setEmergencyNumber("42");
        userProfileDTO.setFirstName("Jane");
        userProfileDTO.setLastName("Doe");
        userProfileDTO.setPassword("iloveyou");
        userProfileDTO.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(userProfileDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Add User"));
    }

    /**
     * Method under test: {@link UserController#saveHazards()}
     */
    @Test
    void testSaveHazards() throws Exception {
        when(userService.saveHazards()).thenReturn("Save Hazards");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/saveHazards");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Save Hazards"));
    }

    /**
     * Method under test: {@link UserController#saveBasicInfo()}
     */
    @Test
    void testSaveBasicInfo() throws Exception {
        when(userService.saveUserSensorAndAction()).thenReturn("Save User Sensor And Action");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/saveBasicInfo");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Save User Sensor And Action"));
    }
}

