package com.project.aminutesociety.scrap.controller;

import com.project.aminutesociety.scrap.service.ScrapServiceImpl;
import com.project.aminutesociety.util.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/scrap")
public class ScrapController {

    private final ScrapServiceImpl scrapService;

    @PostMapping("{userId}/{videoId}")
    public ResponseEntity<ApiResponse<?>> createScrap(@PathVariable("userId") String userId, @PathVariable("videoId") Long videoId) {
        ResponseEntity<ApiResponse<?>> result = scrapService.createScrap(userId, videoId);
        return result;
    }

    @DeleteMapping("{userId}/{videoId}")
    public ResponseEntity<ApiResponse<?>> deleteScrap(@PathVariable("userId") String userId, @PathVariable("videoId") Long videoId) {
        ResponseEntity<ApiResponse<?>> result = scrapService.deleteScrap(userId, videoId);
        return result;
    }

    @GetMapping("{userId}")
    public ResponseEntity<ApiResponse<?>> getUserScrap(@PathVariable("userId") String userId, @RequestParam("type") String type) {
        ResponseEntity<ApiResponse<?>> result = scrapService.getUserScrap(userId, type);
        return result;
    }
}
