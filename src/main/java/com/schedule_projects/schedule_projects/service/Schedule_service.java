package com.schedule_projects.schedule_projects.service;

import com.schedule_projects.schedule_projects.domain.Schedule;
import com.schedule_projects.schedule_projects.domain.User_info;
import com.schedule_projects.schedule_projects.repository.Schedule_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Schedule_service {
    @Autowired
    private Schedule_repository schedule_repository;

    // 내 스케줄 조회 서비스 구현
    public List<Schedule> getSchedulesByUser(User_info user) {
        return schedule_repository.findByUser(user);
    }
    public Schedule saveSchedule(Schedule schedule) {
        return schedule_repository.save(schedule);
    }
}

