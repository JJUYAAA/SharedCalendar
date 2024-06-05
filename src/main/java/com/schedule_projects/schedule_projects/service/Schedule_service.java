package com.schedule_projects.schedule_projects.service;

import com.schedule_projects.schedule_projects.domain.Schedule;
import com.schedule_projects.schedule_projects.domain.User_info;
import com.schedule_projects.schedule_projects.repository.Schedule_repository;
import com.schedule_projects.schedule_projects.repository.User_info_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Schedule_service {
    @Autowired
    private Schedule_repository schedule_repository;
    @Autowired
    private User_info_repository user_info_repository;

    // 내 스케줄 조회 서비스 구현
    public List<Schedule> findSchedulesByUsername(String username) {
        User_info user = user_info_repository.findByUserName(username);
        if (user != null) {
            return schedule_repository.findByUser(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }
    public Schedule saveSchedule(Schedule schedule) {
        return schedule_repository.save(schedule);
    }
}

