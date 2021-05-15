package com.rastelligualtieri.trainingschedule.server.model;

import javax.persistence.*;

@Entity
@Table( name = "SCHEDULE",
        indexes = {
                //@Index(columnList="scheduleId", name="schedule_id_index"),
        })
public class ScheduleEntity {

    @Id
    @Column(nullable = false)
    @GeneratedValue(generator = "SEC_SCHEDULE", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEC_SCHEDULE", sequenceName = "SEC_SCHEDULE", allocationSize = 1)
    private Long scheduleId;

    @Column(nullable = false)
    private Long userId;

    @Lob
    @Column(nullable = false)
    private String dataJson;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private String categoria1;

    @Column(nullable = true)
    private String categoria2;

    @Column(nullable = true)
    private String equipment;

    @Column(nullable = true)
    private Integer downloads;

    @Column(nullable = false)
    private String creator;

    public ScheduleEntity(){ }

    public ScheduleEntity(Long userId,String dataJson, String title, String description){
        super();
        this.setUserId(userId);
        this.setDataJson(dataJson);
        this.setTitle(title);
        this.setDescription(description);
    }

    public ScheduleEntity(Long userId,String dataJson, String title, String description, String categoria1, String categoria2, String equipment, String creator){
        super();
        this.setUserId(userId);
        this.setDataJson(dataJson);
        this.setTitle(title);
        this.setDescription(description);
        this.setCategoria1(categoria1);
        this.setCategoria2(categoria2);
        this.setEquipment(equipment);
        this.setCreator(creator);
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDataJson() {
        return dataJson;
    }

    public void setDataJson(String dataJson) {
        this.dataJson = dataJson;
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

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
