package com.example.Practice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Practice.entity.User;
public interface UserRepository
        extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    
}