package com.schedule_projects.schedule_projects.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_info")
public class User_info {
    @Id
    @Column(name = "user_id", length = 20)
    private String userId;

    @Column(name = "user_name", length = 10, nullable = false)
    private String userName;

    @Column(name = "identifier", unique = true, length = 30, nullable = false)
    private String identifier;

    @Column(name = "password", unique = true, length = 30, nullable = false)
    private String password;

    @Column(name = "created_time", nullable = false, updatable = false)
    private LocalDateTime createdTime;

    @Column(name = "update_time", insertable = false)
    private LocalDateTime updateTime;

    // 기본 생성자, 게터, 세터
    public User_info() {};

    public User_info(String userId, String userName, String identifier, String password) {
        this.userId = userId;
        this.userName = userName;
        this.identifier = identifier;
        this.password = password;
    }

    public String getUserId() {return userId;}
    public String getUserName() {return userName;}
    public String getIdentifier() {return identifier;}
    public String getPassword() {return password;}
    public LocalDateTime getCreatedTime() {return createdTime;}
    public LocalDateTime getUpdateTime() {return updateTime;}

    public void setUserId(String userId) {this.userId = userId;}
    public void setUserName(String userName) {this.userName = userName;}
    public void setIdentifier(String identifier) {this.identifier = identifier;}
    public void setPassword(String password) {this.password = password;}
    public void setCreatedTime(LocalDateTime createdTime) {this.createdTime = createdTime;}
    public void setUpdateTime(LocalDateTime updateTime) {this.updateTime = updateTime;}
}
