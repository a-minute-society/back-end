package com.project.aminutesociety.user.repository;

import com.project.aminutesociety.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface userRepository extends JpaRepository<User, Long> {
    Optional<User> findMemberByUserId(String userId);
}
