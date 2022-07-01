package com.nextgendevs.hanchor.io.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "gratitude_tb")
public class GratitudeEntity implements Serializable {
    private static final long serialVersionUID = -4197133818142498182L;

    @Id @GeneratedValue
    private long id;
    private String title;

    @Column(columnDefinition="text")
    private String message;
    private String imageUrl = "";
    private String imageId = "0";
    @ManyToOne @JoinColumn(name = "user_tb_id")
    private UserEntity userEntityGratitude;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public UserEntity getUserEntityGratitude() {
        return userEntityGratitude;
    }

    public void setUserEntityGratitude(UserEntity userEntityGratitude) {
        this.userEntityGratitude = userEntityGratitude;
    }
}
