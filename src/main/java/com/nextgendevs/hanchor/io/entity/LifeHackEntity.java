package com.nextgendevs.hanchor.io.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "life_hack_tb")
public class LifeHackEntity implements Serializable {
    private static final long serialVersionUID = -4197133818142498182L;

    @Id @GeneratedValue
    private long id;

    @Column(columnDefinition="text")
    private String lifeHack;
//    @ManyToOne @JoinColumn(name = "user_tb_id")
//    private UserEntity userEntityLifeHack;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLifeHack() {
        return lifeHack;
    }

    public void setLifeHack(String lifeHack) {
        this.lifeHack = lifeHack;
    }

//    public UserEntity getUserEntityLifeHack() {
//        return userEntityLifeHack;
//    }
//
//    public void setUserEntityLifeHack(UserEntity userEntityLifeHack) {
//        this.userEntityLifeHack = userEntityLifeHack;
//    }
}
