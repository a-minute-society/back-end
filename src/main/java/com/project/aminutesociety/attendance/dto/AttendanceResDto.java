package com.project.aminutesociety.attendance.dto;

import lombok.Builder;

@Builder
public class AttendanceResDto {
    private Long id;
    private String date;
    private Integer viewingTime;
}
