package com.project.aminutesociety.video.controller;

import com.project.aminutesociety.util.response.ApiResponse;
import com.project.aminutesociety.video.dto.EditRecommendVideo;
import com.project.aminutesociety.video.service.VideoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/video")
@RequiredArgsConstructor
public class VideoController {

    private final VideoServiceImpl videoService;

    @GetMapping("recommend/{userId}")
    public ResponseEntity<ApiResponse<?>> recommendVideo(@PathVariable("userId") String userId, @RequestParam("time") Integer time) {
        ResponseEntity<ApiResponse<?>> result = videoService.recommendVideo(userId, time);
        return result;
    }

    @PutMapping("recommend/{userId}")
    public ResponseEntity<ApiResponse<?>> editRecommendVideo(@PathVariable("userId") String userId,
                                                             @RequestParam("time") Integer time,
                                                            @RequestBody EditRecommendVideo.Req req) {
        ResponseEntity<ApiResponse<?>> result = videoService.editRecommendVideo(userId, time, req);
        return result;
    }

    @GetMapping("home/recommend/{userId}")
    public ResponseEntity<ApiResponse<?>> getVideoRecommend(@PathVariable("userId") String userId) {
        ResponseEntity<ApiResponse<?>> result = videoService.getVideoRecommend(userId);
        return result;
    }
}
