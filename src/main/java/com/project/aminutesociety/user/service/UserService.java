package com.project.aminutesociety.user.service;

import com.project.aminutesociety.user.dto.ChangeTimeDto;
import com.project.aminutesociety.user.dto.UserLoginRequestDto;
import com.project.aminutesociety.user.dto.UserSignUpDto;
import com.project.aminutesociety.util.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<ApiResponse<?>> signUp(UserSignUpDto.Req req);
    ResponseEntity<ApiResponse<?>> login(UserLoginRequestDto userLoginDto);

    ResponseEntity<ApiResponse<?>> checkUserId(String userId);

    ResponseEntity<ApiResponse<?>> userInfo(String userId);

    ResponseEntity<ApiResponse<?>> changeTime(String userId, ChangeTimeDto changeTimeDto);

    ResponseEntity<ApiResponse<?>> SignOut(String userId);
}
