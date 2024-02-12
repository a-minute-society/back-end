package com.project.aminutesociety.user.controller;

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

}
