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

    @Transactional
    public Friend_relation addRelation(Friend_relation relation) {
        return friendRelationRepository.save(relation);
    }

    @Transactional
    public void removeRelation(Friend_relationId relationId) {
        friendRelationRepository.deleteById(relationId);
    }
}