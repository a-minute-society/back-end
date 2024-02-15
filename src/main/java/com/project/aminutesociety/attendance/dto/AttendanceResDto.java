package com.project.aminutesociety.attendance.dto;

import com.project.aminutesociety.video.dto.RecommendVideo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor()
@AllArgsConstructor()
public class AttendanceResDto {
    private Long id;
    private String date;
    private String accumulatedTime;
}