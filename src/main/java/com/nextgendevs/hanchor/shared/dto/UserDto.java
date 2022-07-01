package com.nextgendevs.hanchor.shared.dto;

import com.nextgendevs.hanchor.ui.model.response.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class UserDto implements Serializable {
    private static final long serialVersionUID = -3197131818142498182L;

    private long id;
    private String userId;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String password;
    private String encryptedPassword;
    private String emailVerificationToken;
    private Boolean emailVerificationStatus = false;
    private List<GratitudeDto> gratitudeDtos;
    private List<QuoteDto> quoteDtos;
    private List<LifeHackDto> lifeHackDtos;
    private List<AffirmationDto> affirmationDtos;
    private List<TodoDto> todoDtos;
    private List<GratitudeRest> gratitudeRests;
    private List<QuoteRest> quoteRests;
    private List<LifeHackRest> lifeHackRests;
    private List<AffirmationRest> affirmationRests;
    private List<TodoRest> todoRests;
    private Timestamp timestamp;

//    private Collection<String> roles;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getEmailVerificationToken() {
        return emailVerificationToken;
    }

    public void setEmailVerificationToken(String emailVerificationToken) {
        this.emailVerificationToken = emailVerificationToken;
    }

    public Boolean getEmailVerificationStatus() {
        return emailVerificationStatus;
    }

    public void setEmailVerificationStatus(Boolean emailVerificationStatus) {
        this.emailVerificationStatus = emailVerificationStatus;
    }

    public List<GratitudeDto> getGratitudeDtos() {
        return gratitudeDtos;
    }

    public void setGratitudeDtos(List<GratitudeDto> gratitudeDtos) {
        this.gratitudeDtos = gratitudeDtos;
    }

    public List<QuoteDto> getQuoteDtos() {
        return quoteDtos;
    }

    public void setQuoteDtos(List<QuoteDto> quoteDtos) {
        this.quoteDtos = quoteDtos;
    }

    public List<LifeHackDto> getLifeHackDtos() {
        return lifeHackDtos;
    }

    public void setLifeHackDtos(List<LifeHackDto> lifeHackDtos) {
        this.lifeHackDtos = lifeHackDtos;
    }

    public List<AffirmationDto> getAffirmationDtos() {
        return affirmationDtos;
    }

    public void setAffirmationDtos(List<AffirmationDto> affirmationDtos) {
        this.affirmationDtos = affirmationDtos;
    }

    public List<TodoDto> getTodoDtos() {
        return todoDtos;
    }

    public void setTodoDtos(List<TodoDto> todoDtos) {
        this.todoDtos = todoDtos;
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


//    public Collection<String> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Collection<String> roles) {
//        this.roles = roles;
//    }
}
