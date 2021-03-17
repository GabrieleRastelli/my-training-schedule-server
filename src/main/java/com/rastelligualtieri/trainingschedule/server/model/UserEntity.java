package com.rastelligualtieri.trainingschedule.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table( name = "USER_INFO",
        indexes = {
                @Index(columnList="email", name="email_index"),
        })
public class UserEntity {
    @Id
    @Column(nullable = false)
    @GeneratedValue(generator = "SEC_USER", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEC_USER", sequenceName = "SEC_USER", allocationSize = 1)
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(unique=true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    public UserEntity(){

    }

    public UserEntity( String nome, String email, String password) {
        super();
        this.setName(nome);
        this.setEmail(email);
        this.setPassword(password);
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
