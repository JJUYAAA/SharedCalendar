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
     * 주어진 사용자의 모든 일정에서 시작 날짜와 종료 날짜 사이의 모든 날짜를 가져옵니다.
     * @param userId 사용자 ID
     * @return 일정이 있는 모든 날짜 목록
     */
    public List<LocalDate> getAllScheduleByUser(String userId) {
        // 사용자의 모든 일정 조회
        List<Schedule> schedules = scheduleRepository.findByUserId(userId);

        // 일정이 있는 모든 날짜를 저장할 리스트 초기화
        List<LocalDate> allDatesWithSchedule = new ArrayList<>();

        // 모든 일정에서 시작 날짜와 종료 날짜 사이의 모든 날짜를 생성하여 리스트에 추가
        for (Schedule schedule : schedules) {
            LocalDateTime startDate = schedule.getStartTime();
            LocalDateTime endDate = schedule.getEndTime();
            List<LocalDate> datesBetween = getAllDatesBetween(startDate.toLocalDate(), endDate.toLocalDate());
            allDatesWithSchedule.addAll(datesBetween);
        }

        return allDatesWithSchedule;
    }

    /**
     * 시작 날짜와 종료 날짜 사이의 모든 날짜를 가져옵니다.
     * @param startDate 시작 날짜
     * @param endDate 종료 날짜
     * @return 시작 날짜와 종료 날짜 사이의 모든 날짜 목록
     */
    private List<LocalDate> getAllDatesBetween(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> allDates = new ArrayList<>();
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            allDates.add(currentDate);
            currentDate = currentDate.plusDays(1);
        }

        return allDates;
    }

    /**
     * 주어진 두 사용자의 일정이 모두 없는 날짜를 가져옵니다.
     * @param startDate    시작 날짜
     * @param endDate      종료 날짜
     * @param userId1      첫 번째 사용자의 ID
     * @param userId2      두 번째 사용자의 ID
     * @return             두 사용자의 일정이 모두 없는 날짜 목록
     */
    public List<LocalDate> getDatesWithNoSchedules(LocalDate startDate, LocalDate endDate, String userId1, String userId2) {
        // 주어진 기간 동안 첫 번째 사용자와 두 번째 사용자의 모든 일정을 가져옴
        List<Schedule> schedules1 = scheduleRepository.findByUserIdAndDateRange(userId1, startDate, endDate);
        List<Schedule> schedules2 = scheduleRepository.findByUserIdAndDateRange(userId2, startDate, endDate);

        // 모든 일정의 날짜를 추출하여 중복을 제거하고 정렬
        List<LocalDate> allDates = new ArrayList<>();
        allDates.addAll(schedules1.stream().map(Schedule::getStartTime).map(LocalDateTime::toLocalDate).collect(Collectors.toList()));
        allDates.addAll(schedules2.stream().map(Schedule::getStartTime).map(LocalDateTime::toLocalDate).collect(Collectors.toList()));
        allDates.sort(LocalDate::compareTo);

        // 두 사용자의 일정이 모두 없는 날짜를 찾아 반환
        List<LocalDate> datesWithNoSchedules = new ArrayList<>();
        LocalDate currentDate = startDate;
        for (LocalDate date : allDates) {
            while (currentDate.isBefore(date)) {
                datesWithNoSchedules.add(currentDate);
                currentDate = currentDate.plusDays(1);
            }
            currentDate = currentDate.plusDays(1);
        }
        while (!currentDate.isAfter(endDate)) {
            datesWithNoSchedules.add(currentDate);
            currentDate = currentDate.plusDays(1);
        return datesWithNoSchedules;
    }
}
