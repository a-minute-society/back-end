package com.project.aminutesociety.attendance.controller;

import com.project.aminutesociety.attendance.dto.SetAttendanceDto;
import com.project.aminutesociety.attendance.service.AttendanceServiceImpl;
import com.project.aminutesociety.util.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/attendance")
public class AttendanceController {
    private final AttendanceServiceImpl attendanceService;

    // 영상 시청 후 틈새시간 증가 및 출석 확인
    @PostMapping("/{userId}")
    public ResponseEntity<ApiResponse<?>> setSaveTime(@PathVariable("userId") String userId,
                                                      @RequestBody SetAttendanceDto setAttendanceDto) {
        return attendanceService.setSaveTime(userId, setAttendanceDto);
    }
}
