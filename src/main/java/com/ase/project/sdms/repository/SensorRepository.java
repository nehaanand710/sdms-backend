package com.ase.project.sdms.repository;

import com.ase.project.sdms.domain.Sensor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SensorRepository extends CrudRepository<Sensor, Integer> {

    Optional<Sensor> findById(Integer id);
    @Query(value = "select * from Sensor where userId=:userId and id=:sensorId", nativeQuery = true )
    Sensor getSensorData(@Param(value = "userId") Integer userId, @Param(value = "sensorId") Integer sensorId);

    @Query(value = "select * from Sensor where userId=:userId", nativeQuery = true )
    List<Sensor> getSensorList(@Param(value = "userId") Integer userId);
}
