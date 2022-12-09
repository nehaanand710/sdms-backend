package com.ase.project.sdms.repository;

import com.ase.project.sdms.domain.SensorType;
import com.ase.project.sdms.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorTypeRepository extends CrudRepository<SensorType, Integer> {

    @Query(value = "select * from SensorType", nativeQuery = true )
    List<SensorType> findAll();


}
