package com.project.aminutesociety.video.service;

import com.project.aminutesociety.util.response.ApiResponse;
import com.project.aminutesociety.video.dto.EditRecommendVideo;
import org.springframework.http.ResponseEntity;

public interface VideoService {

    ResponseEntity<ApiResponse<?>> recommendVideo(String userId, Integer time); // 영상 추천
    ResponseEntity<ApiResponse<?>> editRecommendVideo(String userId, Integer time, EditRecommendVideo.Req req); // 영상 추천 수정
}
