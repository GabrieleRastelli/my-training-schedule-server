package com.rastelligualtieri.trainingschedule.server.model;

import javax.persistence.*;

@Entity
@Table( name = "SCHEDULE",
        indexes = {
                @Index(columnList="userId", name="user_id_index"),
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
    private String dataXml;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private int downloads;

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

    public String getDataXml() {
        return dataXml;
    }

    public void setDataXml(String dataXml) {
        this.dataXml = dataXml;
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

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }
}
