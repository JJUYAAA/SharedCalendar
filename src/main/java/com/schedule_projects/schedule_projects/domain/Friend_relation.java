package com.schedule_projects.schedule_projects.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "friend_relation")
@IdClass(FriendRelationId.class) // 복합키 클래스
public class Friend_relation {
    @Id
    @Column(name = "user_id1", length = 20)
    private String userId1;

    @Id
    @Column(name = "user_id2", length = 20)
    private String userId2;

    @Enumerated(EnumType.STRING)
    @Column(name = "relationship_status", nullable = false)
    private RelationshipStatus relationshipStatus;

    @Column(name = "created_time", nullable = false, updatable = false)
    private LocalDateTime createdTime;

    @Column(name = "update_time", insertable = false)
    private LocalDateTime updateTime;

    // 기본 생성자, 게터, 세터
}

