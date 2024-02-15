package com.project.aminutesociety.scrap.controller;

import com.project.aminutesociety.scrap.service.ScrapServiceImpl;
import com.project.aminutesociety.util.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
