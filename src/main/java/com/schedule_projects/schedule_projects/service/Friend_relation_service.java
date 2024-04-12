package com.schedule_projects.schedule_projects.service;

import com.schedule_projects.schedule_projects.domain.Friend_relation;
import com.schedule_projects.schedule_projects.domain.Friend_relationId;
import com.schedule_projects.schedule_projects.repository.Friend_relation_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class Friend_relation_service {

    @Autowired
    private Friend_relation_repository friendRelationRepository;

    @Transactional(readOnly = true)
    public List<Friend_relation> getAllRelations() {
        return friendRelationRepository.findAll();
    }

    /**
     * 상태를 REQUESTED로 설정하고 새 친구 관계를 추가합니다.
     *
     * @param userIdA 첫 번째 사용자 ID
     * @param userIdB 두 번째 사용자 ID
     */
    @Transactional
    public void addFriendRelation(String userIdA, String userIdB) {
        String userId1 = userIdA.compareTo(userIdB) < 0 ? userIdA : userIdB;
        String userId2 = userIdA.compareTo(userIdB) > 0 ? userIdA : userIdB;

        Friend_relation friendRelation = new Friend_relation();
        friendRelation.setUserId1(userId1);
        friendRelation.setUserId2(userId2);
        friendRelation.setRelationshipStatus(Relationship_status.REQUESTED);
        friendRelation.setCreatedTime(LocalDateTime.now());
        friendRelation.setUpdateTime(null); // 데이터베이스 업데이트 작업 시 updateTime은 자동으로 설정됩니다
        friendRelationRepository.save(friendRelation);
    }

    /**
     * 사용자 관계의 상태를 검사하고 상태에 따라 적절한 메시지를 반환합니다.
     *
     * @param userId1 첫 번째 사용자 ID
     * @param userId2 두 번째 사용자 ID
     * @return 관계 상태에 따른 메시지
     */
    @Transactional
    public String acceptFriendRelation(String userId1, String userId2) {
        Optional<Friend_relation> existingRelation = friendRelationRepository.findByUserId1AndUserId2(userId1, userId2);
        //userId에 맞는 쿼리 결과가 있으면
        if (existingRelation.isPresent()) {
            Friend_relation friendRelation = existingRelation.get();
            //status가 blocked상태가 아닐 경우 status를 accepted로 할당
            if (friendRelation.getRelationshipStatus() != Relationship_status.blocked) {
                friendRelation.setRelationshipStatus(Relationship_status.accepted);
                friendRelationRepository.save(friendRelation);
                return "상태가 ACCEPTED로 변경되었습니다.";
            } else {
                return "차단 상태인 상대입니다.";
            }
        } else {
            return "신청 이력이 없습니다.";
        }
    }

    /**
     * 두 사용자 간의 친구 관계를 삭제합니다.
     * @param userId1 사용자 ID 1
     * @param userId2 사용자 ID 2
     * @return 삭제 결과 메시지
     */
    public String deleteFriendRelation(String userId1, String userId2) {
        // 두 사용자 간의 친구 관계를 조회
        Optional<FriendRelation> existingRelation = friendRelationRepository.findByUserId1AndUserId2(userId1, userId2);

        // 관계가 존재하는 경우 삭제
        if (existingRelation.isPresent()) {
            FriendRelation friendRelation = existingRelation.get();
            friendRelationRepository.delete(friendRelation);
            return "친구 관계가 삭제되었습니다.";
        } else {
            return "친구 관계가 존재하지 않습니다.";
        }
    }

    /**
     * 두 사용자 간의 친구 관계를 블록 상태로 변경합니다.
     * @param userId1 사용자 ID 1
     * @param userId2 사용자 ID 2
     * @return 변경 결과 메시지
     */
    public String blockFriendRelation(String userId1, String userId2) {
        // 두 사용자 간의 친구 관계를 조회
        Optional<FriendRelation> existingRelation = friendRelationRepository.findByUserId1AndUserId2(userId1, userId2);

        // 관계가 존재하는 경우 상태를 블록으로 변경
        if (existingRelation.isPresent()) {
            FriendRelation friendRelation = existingRelation.get();
            friendRelation.setRelationshipStatus(RelationshipStatus.BLOCKED);
            friendRelationRepository.save(friendRelation);
            return "친구 관계가 블록되었습니다.";
        } else {
            return "친구 관계가 존재하지 않습니다.";
        }
    }

    /**
     * 두 사용자 ID 사이의 ACCEPTED 상태의 친구 관계를 반환합니다.
     *
     * @param userId1 첫 번째 사용자 ID
     * @param userId2 두 번째 사용자 ID
     * @return ACCEPTED 상태의 친구 관계, 존재하지 않으면 null 반환
     */
    @Transactional
    public Friend_relation getAcceptedFriendRelation(String userId1, String userId2) {
        Optional<Friend_relation> relation = friendRelationRepository.findByUserId1AndUserId2AndRelationshipStatus(
                userId1, userId2, Relationship_status.ACCEPTED);
        return relation.orElse(null);  // relation이 존재하면 해당 객체를 반환하고, 없으면 null 반환
    }

    @Transactional
    public void removeRelation(Friend_relationId relationId) {friendRelationRepository.deleteById(relationId);}
}