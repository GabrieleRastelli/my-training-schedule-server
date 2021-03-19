package com.rastelligualtieri.trainingschedule.server.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, String> {

    @Query(value = "SELECT SCHEDULE_ID, TITLE, DESCRIPTION FROM SCHEDULE WHERE USER_ID=?1 ORDER BY SCHEDULE_ID", nativeQuery = true)
    List<Object> findSchedulesGenericInfo(Long userId);

    List<ScheduleEntity> findByUserId(Long userId);

    ScheduleEntity findByScheduleId(Long scheduleId);
}
