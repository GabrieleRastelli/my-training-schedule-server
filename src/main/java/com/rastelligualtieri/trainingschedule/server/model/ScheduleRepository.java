package com.rastelligualtieri.trainingschedule.server.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, String> {

    @Query(value = "SELECT SCHEDULE_ID, TITLE, DESCRIPTION FROM SCHEDULE WHERE USER_ID=?1 ORDER BY SCHEDULE_ID", nativeQuery = true)
    List<Object[]> findSchedulesGenericInfo(Long userId);

    @Query("select new com.rastelligualtieri.trainingschedule.server.model.ScheduleGenericInfo(s.scheduleId, s.title,s.description) from ScheduleEntity s where s.userId = ?1")
    List<ScheduleGenericInfo> findDocumentsForListing(Long userId);

    List<ScheduleEntity> findByUserId(Long userId);

    ScheduleEntity findByScheduleId(Long scheduleId);
}
