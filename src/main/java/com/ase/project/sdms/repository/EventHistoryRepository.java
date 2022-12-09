package com.ase.project.sdms.repository;

import com.ase.project.sdms.domain.EventHistory;
import com.ase.project.sdms.domain.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface EventHistoryRepository extends JpaRepository<EventHistory, Integer> {
    @Query(value = "select * from EventHistory where userId=:userId and actionTaken='N'", nativeQuery = true )
    List<EventHistory> getEventHistory(@Param(value = "userId") Integer userId);

    @Query(value = "select * from EventHistory where id=:eventId", nativeQuery = true )
    EventHistory findByEventId(@Param(value = "eventId")Integer eventId);

    @Modifying
    @Transactional
    @Query(value = "update EventHistory  set  actionTaken =:message  where id=:eventId", nativeQuery = true )

    Integer updateActionMessage(@Param(value = "message")  String message ,@Param(value = "eventId") Integer eventId);

}
