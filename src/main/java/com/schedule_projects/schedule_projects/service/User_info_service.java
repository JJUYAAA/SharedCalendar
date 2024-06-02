package com.schedule_projects.schedule_projects.service;

import com.schedule_projects.schedule_projects.domain.User_info;
import com.schedule_projects.schedule_projects.repository.User_info_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class User_info_service {
    @Autowired
    private User_info_repository user_info_repository;

    // 로그인 서비스 구현
    public int login(String identifier, String password) {
        // 입력된 identifier와 password로 쿼리
        User_info user = user_info_repository.findByIdentifierAndPassword(identifier, password);
        // user_id가 null이 아니면 로그인 성공
        if (user != null) {
            return user.getUserId();
        } else {
            // 로그인 실패 시 -1 리턴
            return -1;
        }
    }
}