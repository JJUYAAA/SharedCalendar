package com.schedule_projects.schedule_projects.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "friend_relation")
public class Friend_relation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relation_id", length = 20)
    private int relation_id;

    @ManyToOne
    @JoinColumn(name = "user_id1", referencedColumnName = "user_id")
    private User_info userId1;

    @ManyToOne
    @JoinColumn(name = "user_id2", referencedColumnName = "user_id")
    private User_info userId2;

    @Enumerated(EnumType.STRING)
    @Column(name = "relationship_status", nullable = false)
    private Relationship_status relationshipStatus;

    @Column(name = "created_time", nullable = false, updatable = false)
    private LocalDateTime createdTime;

    @Column(name = "update_time", insertable = false)
    private LocalDateTime updateTime;
}

