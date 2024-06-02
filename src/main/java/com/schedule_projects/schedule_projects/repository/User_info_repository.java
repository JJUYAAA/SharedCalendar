package com.schedule_projects.schedule_projects.repository;

import com.schedule_projects.schedule_projects.domain.User_info;
import org.springframework.data.jpa.repository.JpaRepository;

public interface User_info_repository extends JpaRepository<User_info, String> {
    // identifier와 password로 사용자 조회
    User_info findByIdentifierAndPassword(String identifier, String password);
}
