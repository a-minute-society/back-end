package com.project.aminutesociety.attendance.service;

import com.project.aminutesociety.attendance.dto.SetAttendanceDto;
import com.project.aminutesociety.util.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AttendanceService {
    ResponseEntity<ApiResponse<?>> setSaveTime(String userId, SetAttendanceDto setAttendanceDto);

    ResponseEntity<ApiResponse<?>> readAttendanceAll(String userId);
}