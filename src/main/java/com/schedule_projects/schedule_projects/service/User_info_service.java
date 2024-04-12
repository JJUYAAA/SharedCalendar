package com.schedule_projects.schedule_projects.service;

import com.schedule_projects.schedule_projects.domain.User_info;
import com.schedule_projects.schedule_projects.repository.User_info_repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class User_info_service {

    private User_info_repository userInfoRepository;

    @Transactional(readOnly = true)
    public User_info getUserById(String userId) {
        return userInfoRepository.findById(userId).orElse(null);
    }

    @Transactional
    public User_info createUser(User_info user) {
        return userInfoRepository.save(user);
    }

    @Transactional
    public User_info updateUser(User_info user) {
        return userInfoRepository.save(user);
    }

    @Transactional
    public void deleteUser(String userId) {
        userInfoRepository.deleteById(userId);
    }

    @Transactional(readOnly = true)
    public List<User_info> getAllUsers() {
        return userInfoRepository.findAll();
    }
}