package com.project.aminutesociety.video.service;

import com.project.aminutesociety.util.response.ApiResponse;
import com.project.aminutesociety.video.dto.EditRecommendVideo;
import org.springframework.http.ResponseEntity;

public interface VideoService {

    ResponseEntity<ApiResponse<?>> recommendVideo(String userId, Integer time); // 시간 설정 후 영상 추천 - 핵심 기능
    ResponseEntity<ApiResponse<?>> editRecommendVideo(String userId, Integer time, EditRecommendVideo.Req req); // 영상 추천 수정
    ResponseEntity<ApiResponse<?>> getVideoRecommend(String userId); // 홈에서 영상 추천
}
