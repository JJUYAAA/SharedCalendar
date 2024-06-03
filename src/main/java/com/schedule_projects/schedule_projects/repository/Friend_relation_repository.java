package com.schedule_projects.schedule_projects.repository;

import com.schedule_projects.schedule_projects.domain.Friend_relation;
import com.schedule_projects.schedule_projects.domain.User_info;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface Friend_relation_repository extends JpaRepository<Friend_relation, Integer> {
    // 특정 사용자 간의 친구 관계 조회
    Friend_relation findByUserId1AndUserId2(User_info user1, User_info user2);

    // 특정 사용자의 모든 친구 관계 조회
    List<Friend_relation> findAllByUserId1OrUserId2(User_info userId1, User_info userId2);
}
