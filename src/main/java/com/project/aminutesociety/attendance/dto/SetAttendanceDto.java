package com.project.aminutesociety.attendance.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class SetAttendanceDto {

    private List<Integer> videoId;
    private List<String> viewingTime;

}
