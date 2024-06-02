package com.schedule_projects.schedule_projects.service;

import com.schedule_projects.schedule_projects.domain.Friend_relation;
import com.schedule_projects.schedule_projects.domain.Relationship_status;
import com.schedule_projects.schedule_projects.repository.Friend_relation_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class Friend_relation_service {
    @Autowired
    private Friend_relation_repository friend_relation_repository;

    // 친구 신청 서비스 구현
    public String requestFriend(int userId1, int userId2) {
        // 이미 친구 관계인지 확인
        Friend_relation existingRelation = friend_relation_repository.findByUserId1AndUserId2(userId1, userId2);
        if (existingRelation != null) {
            return "이미 친구인 대상입니다";
        }
        // 친구 신청 저장
        Friend_relation newRelation = new Friend_relation();
        newRelation.setUserId1(userId1);
        newRelation.setUserId2(userId2);
        newRelation.setRelationshipStatus(Relationship_status.requested);
        newRelation.setCreatedTime(LocalDateTime.now());
        newRelation.setUpdateTime(LocalDateTime.now());
        friend_relation_repository.save(newRelation);
        return "친구 신청이 완료되었습니다";
    }

    // 친구 신청 수락 서비스 구현
    public void acceptFriend(int userId1, int userId2) {
        Friend_relation relation = friend_relation_repository.findByUserId1AndUserId2(userId1, userId2);
        if (relation != null && relation.getRelationshipStatus() == Relationship_status.requested) {
            relation.setRelationshipStatus(Relationship_status.accepted);
            relation.setUpdateTime(LocalDateTime.now());
            friend_relation_repository.save(relation);
        }
    }

    // 친구 삭제 서비스 구현
    public String deleteFriend(int userId1, int userId2) {
        Friend_relation relation = friend_relation_repository.findByUserId1AndUserId2(userId1, userId2);
        if (relation == null || relation.getRelationshipStatus() != Relationship_status.accepted) {
            return "친구관계가 아닌 대상입니다";
        }
        friend_relation_repository.delete(relation);
        return "친구 삭제가 완료되었습니다";
    }

    // 친구 관계 조회 서비스 구현
    public List<Friend_relation> getFriends(int userId) {
        return friend_relation_repository.findAllByUserId1OrUserId2(userId, userId);
    }
}
