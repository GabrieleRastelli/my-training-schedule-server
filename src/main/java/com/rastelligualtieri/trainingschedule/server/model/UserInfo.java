package com.rastelligualtieri.trainingschedule.server.model;

import javax.persistence.Column;
import javax.persistence.Lob;

/**
 * this class is used to not return password of user
 */
public class UserInfo {
    private Long userId;

    private String nickname;

    private String guid;

    private String name;

    private String email;

    private String profileImage;

    private long created;

    private long download;

    public UserInfo(){

    }

    public UserInfo( Long uid, String nickname, String guid, String nome, String email, String profileImage) {
        super();
        this.setNickname(nickname);
        this.setUserId(uid);
        this.setGuid(guid);
        this.setName(nome);
        this.setEmail(email);
        this.setProfileImage(profileImage);
        this.setCreated(0);
        this.setDownload(0);
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long user_id) {
        this.userId = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
