package com.schedule_projects.schedule_projects.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @Column(name = "event_id", length = 255, nullable = false)
    private String eventId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User_info user;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "location")
    private String location;

    @Column(name = "created_time", nullable = false, updatable = false)
    private LocalDateTime createdTime;

    @Column(name = "update_time", insertable = false)
    private LocalDateTime updateTime;

    //생산자
    public Schedule() {};

    public Schedule(String eventId, String title, String description, LocalDateTime startTime, LocalDateTime endTime, String location, LocalDateTime createdTime, LocalDateTime updateTime) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = LocalDateTime.now();
    }
    //getter
    public String getEventId() {return eventId;}
    public String getTitle() {return title;}
    public String getDescription() {return description;}
    public LocalDateTime getStartTime() {return startTime;}
    public void setEventId(String eventId) {this.eventId = eventId;}
}

