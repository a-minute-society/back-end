package com.project.aminutesociety.user.controller;

import com.project.aminutesociety.user.dto.ChangeTimeDto;
import com.project.aminutesociety.user.dto.UserLoginRequestDto;
import com.project.aminutesociety.user.dto.UserSignUpDto;
import com.project.aminutesociety.user.service.UserService;
import com.project.aminutesociety.util.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @GetMapping("")
    public String hello() {
        return "hello";
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<?>> signUp(@RequestBody UserSignUpDto.Req req) {
        ResponseEntity<ApiResponse<?>> result = userService.signUp(req);
        return result;
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        ResponseEntity<ApiResponse<?>> result = userService.login(userLoginRequestDto);
        return result;
    }

    // userId 존재 확인
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<?>> checkUserId(@PathVariable String userId) {
        ResponseEntity<ApiResponse<?>> result = userService.checkUserId(userId);
        return result;
    }

    // 설정된 카테고리 확인 및 유저 정보 추가 응답 필요
    @GetMapping("/{userId}/user-info")
    public ResponseEntity<ApiResponse<?>> userInfo(@PathVariable String userId) {
        ResponseEntity<ApiResponse<?>> result = userService.userInfo(userId);
        return result;
    }

    // 소요시간 변경
    @PutMapping("/{userId}/change-time")
    public ResponseEntity<ApiResponse<?>> changeTime(@PathVariable String userId, @RequestBody ChangeTimeDto changeTimeDto) {
        ResponseEntity<ApiResponse<?>> result = userService.changeTime(userId, changeTimeDto);
        return result;
    }

    // 회원 탈퇴
    @DeleteMapping("/{userId}/signout")
    public ResponseEntity<ApiResponse<?>> logout(@PathVariable String userId) {
        ResponseEntity<ApiResponse<?>> result = userService.SignOut(userId);
        return result;
    }

}
