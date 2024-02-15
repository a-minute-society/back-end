package com.project.aminutesociety.attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor()
@AllArgsConstructor()
public class MyPageDetailResDto {
    private Long videoId;
    private Long categoryId;
    private String url;
    private String title;
    private boolean isScrap;
}
