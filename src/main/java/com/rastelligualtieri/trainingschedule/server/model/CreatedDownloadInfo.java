package com.rastelligualtieri.trainingschedule.server.model;

/**
 * this class is used to not return password of user
 */
public class CreatedDownloadInfo {
    private Long userId;

    private String creator;

    private long created;

    private long download;

    public CreatedDownloadInfo(){

    }

    public CreatedDownloadInfo(Long userId, String creator, long created, long download) {
        this.userId = userId;
        this.creator = creator;
        this.created = created;
        this.download = download;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getDownload() {
        return download;
    }

    public void setDownload(long download) {
        this.download = download;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }


}
