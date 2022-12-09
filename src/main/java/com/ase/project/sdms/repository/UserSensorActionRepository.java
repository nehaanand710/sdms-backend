package com.ase.project.sdms.repository;

import com.ase.project.sdms.domain.UserSensorAction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSensorActionRepository extends CrudRepository<UserSensorAction, Integer> {

}
