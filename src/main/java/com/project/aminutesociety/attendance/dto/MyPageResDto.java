package com.project.aminutesociety.attendance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
@Builder
@Getter
@NoArgsConstructor()
@AllArgsConstructor()
public class MyPageResDto {
    private String totalTime;
    private List<AttendanceResDto> calendar;
}
