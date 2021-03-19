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
    private Integer downloads;

    public ScheduleEntity(){ }

    public ScheduleEntity(Long userId,String dataJson, String title, String description){
        super();
        this.setUserId(userId);
        this.setDataJson(dataJson);
        this.setTitle(title);
        this.setDescription(description);
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
}
