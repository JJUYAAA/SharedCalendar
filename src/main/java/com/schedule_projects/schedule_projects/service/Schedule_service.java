package com.schedule_projects.schedule_projects.service;

import com.schedule_projects.schedule_projects.domain.Schedule;
import com.schedule_projects.schedule_projects.repository.Schedule_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Service
public class Schedule_service {

    @Autowired
    private Schedule_repository scheduleRepository;
    @Transactional(readOnly = true)
    public Optional<Schedule> getUserSchedules(String userId) {
        return scheduleRepository.findById(userId);
    }

    @Transactional
    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Transactional
    public void updateSchedule(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    @Transactional
    public void deleteSchedule(String eventId) {
        scheduleRepository.deleteById(eventId);
    }

    /**
     * 주어진 시간 범위에 해당하는 모든 스케줄의 시작 시간과 종료 시간 사이의 시간 차이를 계산합니다.
     * @param start 조회할 스케줄의 시작 시간
     * @param end 조회할 스케줄의 종료 시간
     * @return 모든 스케줄의 시작 시간과 종료 시간 사이의 총 시간 차이 (초)
     */
    public long calculateTimeDifferenceForSchedules(LocalDateTime start, LocalDateTime end) {
        // 주어진 시간 범위에 해당하는 스케줄 조회
        List<Schedule> schedules = scheduleRepository.findByStartTimeBetween(start, end);
        // 총 시간 차이를 저장할 변수 초기화
        long totalTimeDifference = 0;

        // 조회된 각 스케줄에 대해 시작 시간과 종료 시간 사이의 시간 차이를 계산하여 총 시간 차이에 더함
        for (Schedule schedule : schedules) {
            long timeDifference = calculateTimeDifference(schedule.getStartTime(), schedule.getEndTime());
            totalTimeDifference += timeDifference;
        }

        return totalTimeDifference;
    }

    /**
     * 두 시간 사이의 시간 차이를 계산하여 반환합니다.
     * @param startTime 시작 시간
     * @param endTime 종료 시간
     * @return 시작 시간과 종료 시간 사이의 시간 차이 (초)
     */
    private long calculateTimeDifference(LocalDateTime startTime, LocalDateTime endTime) {
        // 시작 시간과 종료 시간을 유닉스 타임으로 변환하여 시간 차이를 계산하여 반환
        return endTime.toEpochSecond(ZoneOffset.UTC) - startTime.toEpochSecond(ZoneOffset.UTC);
    }
}
