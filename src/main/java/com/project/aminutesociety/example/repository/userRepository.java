package com.project.aminutesociety.example.repository;

import com.project.aminutesociety.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface userRepository extends JpaRepository<User, Long> {
    Optional<User> findMemberByUserId(String userId);
}
