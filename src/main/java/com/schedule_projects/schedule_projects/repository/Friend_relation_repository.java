package com.schedule_projects.schedule_projects.repository;

import com.schedule_projects.schedule_projects.domain.Friend_relationId;
import com.schedule_projects.schedule_projects.domain.Friend_relation;
import com.schedule_projects.schedule_projects.domain.Relationship_status;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface Friend_relation_repository extends JpaRepository<Friend_relation, Friend_relationId> {
    // 특정 사용자 간의 친구 관계 조회
    Friend_relation findByUserId1AndUserId2(int userId1, int userId2);

    // 특정 사용자의 모든 친구 관계 조회
    List<Friend_relation> findAllByUserId1OrUserId2(int userId1, int userId2);
}
