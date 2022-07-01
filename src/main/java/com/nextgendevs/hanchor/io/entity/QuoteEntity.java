package com.nextgendevs.hanchor.io.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "quote_tb")
public class QuoteEntity implements Serializable {
    private static final long serialVersionUID = -4197133818142498182L;

    @Id @GeneratedValue
    private long id;

    @Column(columnDefinition="text")
    private String quote;
//    @ManyToOne @JoinColumn(name = "user_tb_id")
//    private UserEntity userEntityQuote;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

//    public UserEntity getUserEntityQuote() {
//        return userEntityQuote;
//    }
//
//    public void setUserEntityQuote(UserEntity userEntityQuote) {
//        this.userEntityQuote = userEntityQuote;
//    }
}
