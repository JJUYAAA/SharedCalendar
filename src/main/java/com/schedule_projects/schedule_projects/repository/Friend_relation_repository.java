package com.schedule_projects.schedule_projects.repository;

import com.schedule_projects.schedule_projects.domain.Friend_relationId;
import com.schedule_projects.schedule_projects.domain.Friend_relation;
import com.schedule_projects.schedule_projects.domain.Relationship_status;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface Friend_relation_repository extends JpaRepository<Friend_relation, Friend_relationId> {
    Optional<Friend_relation> findByUserId1AndUserId2(String userId1, String userId2);
    Optional<Friend_relation> findByUserId1AndUserId2AndRelationshipStatus(String userId1, String userId2, Relationship_status status);
}
