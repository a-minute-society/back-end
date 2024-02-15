package com.project.aminutesociety.scrap.service;

import com.project.aminutesociety.util.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ScrapService {

    ResponseEntity<ApiResponse<?>> createScrap(String userId, Long videoId);
    ResponseEntity<ApiResponse<?>> deleteScrap(String userId, Long videoId);
}
