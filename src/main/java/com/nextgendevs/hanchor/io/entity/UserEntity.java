package com.nextgendevs.hanchor.io.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user_tb")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = -23714379761985116L;

    @Id @GeneratedValue private Long id;
    @Column(length = 30) private String userId;
    @Column(length = 100) private String firstname;
    @Column(length = 100) private String lastname;
    @Column(length = 100) private String username;
    @Column(length = 150) private String email;
    @Column(nullable = false) private String encryptedPassword;
    private String emailVerificationToken;
    @Column(nullable = false) private Boolean emailVerificationStatus = false;

    @OneToMany(mappedBy = "userEntityGratitude", cascade = CascadeType.ALL)
    private List<GratitudeEntity> gratitudeEntities;

    @OneToMany(mappedBy = "userEntityAffirmation", cascade = CascadeType.ALL)
    private List<AffirmationEntity> affirmationEntities;

    @OneToMany(mappedBy = "userEntityTodo", cascade = CascadeType.ALL)
    private List<TodoEntity> todoEntities;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS, timezone = UTC")
    @CreationTimestamp
    @Column private Timestamp timestamp;

    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "user_tb_roles",
            joinColumns = @JoinColumn (name = "user_tb_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"))
    private Collection<RolesEntity> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public List<GratitudeEntity> getGratitudeEntities() {
        return gratitudeEntities;
    }

    public void setGratitudeEntities(List<GratitudeEntity> gratitudeEntities) {
        this.gratitudeEntities = gratitudeEntities;
    }

    public List<AffirmationEntity> getAffirmationEntities() {
        return affirmationEntities;
    }

    public void setAffirmationEntities(List<AffirmationEntity> affirmationEntities) {
        this.affirmationEntities = affirmationEntities;
    }

    public List<TodoEntity> getTodoEntities() {
        return todoEntities;
    }

    public void setTodoEntities(List<TodoEntity> todoEntities) {
        this.todoEntities = todoEntities;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }


    public Collection<RolesEntity> getRoles() {
        return roles;
    }

    public void setRoles(Collection<RolesEntity> roles) {
        this.roles = roles;
    }
}
