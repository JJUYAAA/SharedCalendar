package com.schedule_projects.schedule_projects.controller;

import com.schedule_projects.schedule_projects.domain.Friend_relation;
import com.schedule_projects.schedule_projects.domain.Relationship_status;
import com.schedule_projects.schedule_projects.domain.Schedule;
import com.schedule_projects.schedule_projects.domain.User_info;
import com.schedule_projects.schedule_projects.service.Friend_relation_service;
import com.schedule_projects.schedule_projects.service.Schedule_service;
import com.schedule_projects.schedule_projects.service.User_info_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.logging.Logger;

@Controller
@RequestMapping("/api")
public class WebController {
    @Autowired
    private User_info_service user_info_service;

    @Autowired
    private Schedule_service schedule_service;

    @Autowired
    private Friend_relation_service friend_relation_service;

    private static final Logger logger = Logger.getLogger(WebController.class.getName());

    @GetMapping("/login")
    public String loginForm() {
        return "login"; // login.html 반환
    }

    @GetMapping("/newuser")
    public String newUserForm() {
        return "newuser"; // newuser.html 반환
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, String> user) {
        logger.info("Register attempt: identifier=" + user.get("identifier") + ", userName=" + user.get("userName"));
        String identifier = user.get("identifier");
        String password = user.get("password");
        String userName = user.get("userName");

        Map<String, Object> response = new HashMap<>();
        try {
            boolean success = user_info_service.registerUser(identifier, password, userName);

            if (success) {
                logger.info("Registration successful for user: " + identifier);
                response.put("success", true);
                response.put("message", "회원가입이 성공적으로 완료되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                logger.info("Registration failed for user: " + identifier);
                response.put("success", false);
                response.put("message", "사용자가 이미 존재합니다.");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }
        } catch (Exception e) {
            logger.info("Registration failed: " + e.getMessage());
            response.put("success", false);
            response.put("message", "서버 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/index")
    public String indexForm() {
        return "index"; // index.html 반환
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
        if (friend_relation_service.getFriends(userId1).stream()
                .anyMatch(f -> f.getUserId2().getUserId() == userId2 && f.getRelationshipStatus() == Relationship_status.accepted)) {
            return "스케줄 비교할 친구 지정 완료";
        }
        return "친구관계가 아닌 대상입니다";
    }
    @PostMapping("/api/login")
    public Map<String, Object> login(@RequestBody Map<String, String> user) {
        String identifier = user.get("identifier");
        String password = user.get("password");

        Map<String, Object> response = new HashMap<>();
        Optional<User_info> optionalUser = user_info_service.findByIdentifier(identifier);

        if (optionalUser.isPresent()) {
            User_info userInfo = optionalUser.get();
            if (userInfo.getPassword().equals(password)) {
                response.put("success", true);
                response.put("message", "로그인 성공");
                // 여기에서 세션 또는 JWT 토큰을 생성하여 클라이언트에게 전달할 수 있습니다.
            } else {
                response.put("success", false);
                response.put("message", "비밀번호가 올바르지 않습니다.");
            }
        } else {
            response.put("success", false);
            response.put("message", "사용자를 찾을 수 없습니다.");
        }
        return response;
    }
}