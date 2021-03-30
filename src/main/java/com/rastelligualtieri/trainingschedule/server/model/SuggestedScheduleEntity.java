package com.rastelligualtieri.trainingschedule.server.model;

import javax.persistence.*;

@Entity
@Table( name = "SUGGESTED_SCHEDULE",
        indexes = {
                //@Index(columnList="scheduleId", name="schedule_id_index"),
        })
public class SuggestedScheduleEntity {
    @Id
    @Column(nullable = false)
    private Long scheduleId;
}
