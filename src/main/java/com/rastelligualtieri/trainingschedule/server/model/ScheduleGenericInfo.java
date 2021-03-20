package com.rastelligualtieri.trainingschedule.server.model;

import java.math.BigDecimal;

public class ScheduleGenericInfo{

    private Long scheduleId;
    private String title;
    private String description;

    public ScheduleGenericInfo(Long scheduleId,String title, String description){
        this.setScheduleId(scheduleId);
        this.setTitle(title);
        this.setDescription(description);
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}