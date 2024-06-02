package com.schedule_projects.schedule_projects.controller;

import com.schedule_projects.schedule_projects.domain.Friend_relation;
import com.schedule_projects.schedule_projects.domain.Relationship_status;
import com.schedule_projects.schedule_projects.domain.Schedule;
import com.schedule_projects.schedule_projects.service.Friend_relation_service;
import com.schedule_projects.schedule_projects.service.Schedule_service;
import com.schedule_projects.schedule_projects.service.User_info_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class WebController {
    @Autowired
    private User_info_service user_info_service;

    @Autowired
    private Schedule_service schedule_service;

    @Autowired
    private Friend_relation_service friend_relation_service;

    // 로그인 서비스 엔드포인트
    @PostMapping("/login")
    public int login(@RequestParam String identifier, @RequestParam String password) {
        return user_info_service.login(identifier, password);
    }

    // 내 스케줄 조회 엔드포인트
    @GetMapping("/my-schedule")
    public List<Schedule> getMySchedule(@RequestParam int userId) {
        return schedule_service.getMySchedule(userId);
    }

    // 친구 신청 엔드포인트
    @PostMapping("/request-friend")
    public String requestFriend(@RequestParam int userId1, @RequestParam int userId2) {
        return friend_relation_service.requestFriend(userId1, userId2);
    }

    // 친구 신청 수락 엔드포인트
    @PostMapping("/accept-friend")
    public void acceptFriend(@RequestParam int userId1, @RequestParam int userId2) {
        friend_relation_service.acceptFriend(userId1, userId2);
    }

    // 친구 삭제 엔드포인트
    @DeleteMapping("/delete-friend")
    public String deleteFriend(@RequestParam int userId1, @RequestParam int userId2) {
        return friend_relation_service.deleteFriend(userId1, userId2);
    }

    // 친구 관계 조회 엔드포인트
    @GetMapping("/friends")
    public List<Friend_relation> getFriends(@RequestParam int userId) {
        return friend_relation_service.getFriends(userId);
    }

    // 스케줄 비교할 친구 지정 엔드포인트
    @PostMapping("/set-schedule-user")
    public String setScheduleUser(@RequestParam int userId1, @RequestParam int userId2) {
        if (friend_relation_service.getFriends(userId1).stream().anyMatch(f -> f.getUserId2() == userId2 && f.getRelationshipStatus() == Relationship_status.accepted)) {
            return "스케줄 비교할 친구 지정 완료";
        }
        return "친구관계가 아닌 대상입니다";
    }

    // 공통 스케줄 없는 날짜 조회 엔드포인트
    @GetMapping("/empty-schedule")
    public List<String> getEmptySchedule(@RequestParam int userId1, @RequestParam int userId2) {
        // 비즈니스 로직 추가 필요 (해당 메서드는 구현되지 않았음)
        return List.of();
    }

    // 새로운 일정 설정 엔드포인트
    @PostMapping("/set-new-schedule")
    public String setNewSchedule(@RequestParam int userId1, @RequestParam int userId2, @RequestBody Schedule schedule) {
        // 비즈니스 로직 추가 필요 (해당 메서드는 구현되지 않았음)
        return "새 일정 설정 완료";
    }
}
