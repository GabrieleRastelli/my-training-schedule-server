package com.rastelligualtieri.trainingschedule.server.model;

import java.math.BigDecimal;

public class ScheduleGenericInfo{

    private Long scheduleId;
    private String title;
    private String description;
    private String categoria1;
    private String categoria2;
    private String equipment;

    public ScheduleGenericInfo(Long scheduleId,String title, String description, String categoria1, String categoria2, String equipment){
        this.setScheduleId(scheduleId);
        this.setTitle(title);
        this.setDescription(description);
        this.setCategoria1(categoria1);
        this.setCategoria2(categoria2);
        this.setEquipment(equipment);
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

    public String getCategoria1() {
        return categoria1;
    }

    public void setCategoria1(String categoria1) {
        this.categoria1 = categoria1;
    }

    public String getCategoria2() {
        return categoria2;
    }

    public void setCategoria2(String categoria2) {
        this.categoria2 = categoria2;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }
}