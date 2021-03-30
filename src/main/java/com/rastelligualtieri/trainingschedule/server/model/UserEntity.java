package com.rastelligualtieri.trainingschedule.server.model;

import javax.persistence.*;


@Entity
@Table( name = "USER_INFO",
        indexes = {
                //@Index(columnList="userId", name="user_id_index"),
        })
public class UserEntity {
    @Id
    @Column(nullable = false)
    @GeneratedValue(generator = "SEC_USER", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEC_USER", sequenceName = "SEC_USER", allocationSize = 1)
    private Long userId;

    @Column(nullable = false)
    private String guid;

    @Column(nullable = false)
    private String name;



    @Column(unique=true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Lob
    private String profileImage;

    public UserEntity(){

    }

    public UserEntity( String guid, String nome, String email, String password) {
        super();
        this.setGuid(guid);
        this.setName(nome);
        this.setEmail(email);
        this.setPassword(password);
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
