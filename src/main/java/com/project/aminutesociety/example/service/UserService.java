package com.project.aminutesociety.example.service;

import com.project.aminutesociety.example.dto.UserLoginRequestDto;
import com.project.aminutesociety.example.dto.UserSignUpDto;
import com.project.aminutesociety.util.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<ApiResponse<?>> signUp(UserSignUpDto.Req req);
    ResponseEntity<ApiResponse<?>> login(UserLoginRequestDto userLoginDto);

    ResponseEntity<ApiResponse<?>> checkUserId(String userId);
}
