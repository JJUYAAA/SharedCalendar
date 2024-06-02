package com.schedule_projects.schedule_projects.service;

import com.schedule_projects.schedule_projects.domain.Schedule;
import com.schedule_projects.schedule_projects.repository.Schedule_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Schedule_service {
    @Autowired
    private Schedule_repository schedule_repository;

    // 내 스케줄 조회 서비스 구현
    public List<Schedule> getMySchedule(int userId) {
        // userId로 스케줄 조회
        return schedule_repository.findByUserId(userId);
    }
}

