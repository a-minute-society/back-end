package com.project.aminutesociety.attendance.repository;

import com.project.aminutesociety.domain.Attendance;
import com.project.aminutesociety.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByUser(User user);

    Attendance findByDate(String date);
}