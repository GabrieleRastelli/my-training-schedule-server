package com.rastelligualtieri.trainingschedule.server.model;

/**
 * this class is used to not return password of user
 */
public class CreatedDownloadInfo {
    private Long userId;

    private String creator;

    private Long created;

    private Long download;

    public CreatedDownloadInfo(){

    }

    public CreatedDownloadInfo(Long userId, String creator, Long created, Long download) {
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

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getDownload() {
        return download;
    }

    public void setDownload(Long download) {
        this.download = download;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }


}
