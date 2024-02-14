package com.project.aminutesociety.video.service;

import com.project.aminutesociety.util.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface VideoService {

    ResponseEntity<ApiResponse<?>> recommendVideo(String userId, Integer time); // 영상 추천
}
