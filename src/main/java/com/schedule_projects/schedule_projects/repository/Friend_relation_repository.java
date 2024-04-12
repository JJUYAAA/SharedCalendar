package com.schedule_projects.schedule_projects.repository;

import com.schedule_projects.schedule_projects.domain.Friend_relationId;
import com.schedule_projects.schedule_projects.domain.Friend_relation;
import com.schedule_projects.schedule_projects.domain.Relationship_status;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface Friend_relation_repository extends JpaRepository<Friend_relation, Friend_relationId> {
}
