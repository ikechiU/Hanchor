package com.nextgendevs.hanchor.io.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "todo_tb")
public class TodoEntity implements Serializable {
    private static final long serialVersionUID = -4197133818142498182L;

    @Id @GeneratedValue
    private long id;
    private String title;

    @Column(columnDefinition="text")
    private String task;
    private long date;
    private Boolean isCompleted = false;
    @ManyToOne @JoinColumn(name = "user_tb_id")
    private UserEntity userEntityTodo;

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

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public UserEntity getUserEntityTodo() {
        return userEntityTodo;
    }

    public void setUserEntityTodo(UserEntity userEntityTodo) {
        this.userEntityTodo = userEntityTodo;
    }
}
