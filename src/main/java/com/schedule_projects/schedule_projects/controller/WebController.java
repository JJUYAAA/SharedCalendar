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

    @GetMapping("/login+")
    public String login1Form() {
        return "login+"; // login.html 반환
    }

    @GetMapping("/index_1")
    public String index1Form() {
        return "index_1"; // login.html 반환
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
    @PostMapping("/request_login")
    public ResponseEntity<Map<String, Object>> requestLogin(@RequestBody Map<String, String> user) {
        String username = user.get("username");
        String password = user.get("password");

        Map<String, Object> response = new HashMap<>();
        try {
            boolean isAuthenticated = user_info_service.validateUser(username, password);

            if (isAuthenticated) {
                response.put("success", true);
                response.put("message", "로그인이 성공적으로 완료되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "잘못된 사용자 이름 또는 비밀번호입니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "서버 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @GetMapping("/schedules")
    public ResponseEntity<List<Schedule>> getSchedules(@RequestParam String username) {
        List<Schedule> schedules = schedule_service.findSchedulesByUsername(username);
        return ResponseEntity.ok(schedules);
    }
}