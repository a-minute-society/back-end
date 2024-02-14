package com.project.aminutesociety.video.controller;

import com.project.aminutesociety.util.response.ApiResponse;
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
}
