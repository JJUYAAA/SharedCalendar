package com.schedule_projects.schedule_projects.domain;

import java.io.Serializable;
import java.util.Objects;
import lombok.Data;

@Data
public class Friend_relationId implements Serializable {
    private int userId1; // FriendRelation 엔티티의 user_id1과 일치
    private int userId2; // FriendRelation 엔티티의 user_id2와 일치

    // equals()와 hashCode() 오버라이드
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friend_relationId that = (Friend_relationId) o;
        return Objects.equals(userId1, that.userId1) && Objects.equals(userId2, that.userId2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId1, userId2);
    }
}
