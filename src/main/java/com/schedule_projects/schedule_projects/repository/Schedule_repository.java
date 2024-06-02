package com.schedule_projects.schedule_projects.repository;

import com.schedule_projects.schedule_projects.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Schedule_repository extends JpaRepository<Schedule, Integer> {
    // userId로 스케줄 조회
    List<Schedule> findByUserId(int userId);
}

