package com.rastelligualtieri.trainingschedule.server.repository;

import java.util.List;

import com.rastelligualtieri.trainingschedule.server.model.ScheduleEntity;
import com.rastelligualtieri.trainingschedule.server.model.ScheduleGenericInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, String> {

    @Query(value = "SELECT SCHEDULE_ID, TITLE, DESCRIPTION FROM SCHEDULE WHERE USER_ID=?1 ORDER BY SCHEDULE_ID", nativeQuery = true)
    List<Object[]> findSchedulesGenericInfo(Long userId);

    @Query("select new com.rastelligualtieri.trainingschedule.server.model.ScheduleGenericInfo(s.scheduleId, s.title,s.description, s.categoria1, s.categoria2, s.equipment, s.creator, s.downloads) from ScheduleEntity s where s.userId = ?1")
    List<ScheduleGenericInfo> findDocumentsForListing(Long userId);

    @Query("select new com.rastelligualtieri.trainingschedule.server.model.ScheduleGenericInfo(s.scheduleId, s.title,s.description, s.categoria1, s.categoria2, s.equipment, s.creator, s.downloads) from ScheduleEntity s join SuggestedScheduleEntity ss on s.scheduleId = ss.scheduleId")
    List<ScheduleGenericInfo> findSuggestedForListing();

    List<ScheduleEntity> findByUserId(Long userId);

    ScheduleEntity findByScheduleId(Long scheduleId);

    @Query("select new com.rastelligualtieri.trainingschedule.server.model.ScheduleGenericInfo(s.scheduleId, s.title,s.description, s.categoria1, s.categoria2, s.equipment, s.creator, s.downloads) from ScheduleEntity s")
    List<ScheduleGenericInfo> findAllSchedules();

    @Query("select new com.rastelligualtieri.trainingschedule.server.model.ScheduleGenericInfo(s.scheduleId, s.title,s.description, s.categoria1, s.categoria2, s.equipment, s.creator, s.downloads) from ScheduleEntity s order by s.downloads desc")
    List<ScheduleGenericInfo> findPopularSchedules(Pageable pageable);
}
