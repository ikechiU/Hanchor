package com.nextgendevs.hanchor.ui.model.response;

import java.sql.Timestamp;
import java.util.List;

public class UserRest {
    private String userId;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private Boolean emailVerificationStatus = false;
    private List<GratitudeRest> gratitudeRests;
    private List<QuoteRest> quoteRests;
    private List<LifeHackRest> lifeHackRests;
    private List<AffirmationRest> affirmationRests;
    private List<TodoRest> todoRests;
    private Timestamp timestamp;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEmailVerificationStatus() {
        return emailVerificationStatus;
    }

    public void setEmailVerificationStatus(Boolean emailVerificationStatus) {
        this.emailVerificationStatus = emailVerificationStatus;
    }

    public List<GratitudeRest> getGratitudeRests() {
        return gratitudeRests;
    }

    public void setGratitudeRests(List<GratitudeRest> gratitudeRests) {
        this.gratitudeRests = gratitudeRests;
    }

    public List<QuoteRest> getQuoteRests() {
        return quoteRests;
    }

    public void setQuoteRests(List<QuoteRest> quoteRests) {
        this.quoteRests = quoteRests;
    }

    public List<LifeHackRest> getLifeHackRests() {
        return lifeHackRests;
    }

    public void setLifeHackRests(List<LifeHackRest> lifeHackRests) {
        this.lifeHackRests = lifeHackRests;
    }

    public List<AffirmationRest> getAffirmationRests() {
        return affirmationRests;
    }

    public void setAffirmationRests(List<AffirmationRest> affirmationRests) {
        this.affirmationRests = affirmationRests;
    }

    public List<TodoRest> getTodoRests() {
        return todoRests;
    }

    public void setTodoRests(List<TodoRest> todoRests) {
        this.todoRests = todoRests;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

}
