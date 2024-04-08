package com.schedule_projects.schedule_projects.domain;

import java.io.Serializable;
import java.util.Objects;

public class FriendRelationId implements Serializable {
    private String userId1; // FriendRelation 엔티티의 user_id1과 일치
    private String userId2; // FriendRelation 엔티티의 user_id2와 일치

    // 기본 생성자
    public FriendRelationId() {
    }

    // 모든 필드를 인자로 받는 생성자
    public FriendRelationId(String userId1, String userId2) {
        this.userId1 = userId1;
        this.userId2 = userId2;
    }

    // 게터와 세터
    public String getUserId1() {
        return userId1;
    }

    public void setUserId1(String userId1) {
        this.userId1 = userId1;
    }

    public String getUserId2() {
        return userId2;
    }

    public void setUserId2(String userId2) {
        this.userId2 = userId2;
    }

    // equals()와 hashCode() 오버라이드
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendRelationId that = (FriendRelationId) o;
        return Objects.equals(userId1, that.userId1) && Objects.equals(userId2, that.userId2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId1, userId2);
    }
}
