package com.ase.project.sdms.event;


import com.ase.project.sdms.domain.Sensor;
import com.ase.project.sdms.domain.User;
import com.ase.project.sdms.repository.SensorRepository;
import com.ase.project.sdms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements ApplicationRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SensorRepository sensorRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(userRepository.count()<1){
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            User user1 = new User();
            user1.setUsername("manvir9824@gmail.com");
            user1.setPassword(passwordEncoder.encode("Password"));
            user1.setRole("ROLE_USER");
            user1.setAddress("Windsor");
            user1.setContactNo("1234567890");
            user1.setFirstName("Victor");
            user1.setLastName("Oyeniran");
            user1.setEmergencyNumber("1234567890,1234567890");

            User user2 = new User();
            user2.setUsername("admin");
            user2.setPassword(passwordEncoder.encode("pass"));
            user2.setRole("ROLE_ADMIN");
            user2.setAddress("address");
            user2.setContactNo("1223333");
            user2.setFirstName("Manvir");
            user2.setLastName("channa");
            user2.setEmergencyNumber("888888888888");

            Sensor sensor = new Sensor();
            sensor.setSensorHealth(true);
            sensor.setId(1);
            sensor.setUserId(1);
            sensor.setSensorStatus(1);
            sensor.setSensorName("Fire Sensor");
            sensor.setSensorLocation("Bedroom 1");

            Sensor sensor1 = new Sensor();
            sensor1.setSensorHealth(true);
            sensor1.setId(2);
            sensor1.setUserId(1);
            sensor1.setSensorStatus(1);
            sensor1.setSensorName("WaterOverflow Sensor");
            sensor1.setSensorLocation("Kitchen");

            userRepository.save(user1);
            userRepository.save(user2);
            sensorRepository.save(sensor1);
            sensorRepository.save(sensor);
            System.out.println("Total users saved::"+userRepository.count());

        }
    }
}
