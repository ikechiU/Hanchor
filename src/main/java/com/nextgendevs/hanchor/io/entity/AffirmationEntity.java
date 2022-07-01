package com.nextgendevs.hanchor.io.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "affirmation_tb")
public class AffirmationEntity implements Serializable {
    private static final long serialVersionUID = -4197133818142498182L;

    @Id @GeneratedValue private long id;
    private String title;

    @Column(columnDefinition="text")
    private String affirmation;

    @ManyToOne @JoinColumn(name = "user_tb_id")
    private UserEntity userEntityAffirmation;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAffirmation() {
        return affirmation;
    }

    public void setAffirmation(String affirmation) {
        this.affirmation = affirmation;
    }

    public UserEntity getUserEntityAffirmation() {
        return userEntityAffirmation;
    }

    public void setUserEntityAffirmation(UserEntity userEntityAffirmation) {
        this.userEntityAffirmation = userEntityAffirmation;
    }
}
