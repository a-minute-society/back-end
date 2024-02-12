package com.project.aminutesociety.example.controller;

import com.project.aminutesociety.example.dto.CreateMemberDto;
import com.project.aminutesociety.example.service.MemberService;
import com.project.aminutesociety.util.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/member/new")
    public ResponseEntity<ApiResponse<?>> createMember(@RequestBody CreateMemberDto.Req req) {
        ResponseEntity<ApiResponse<?>> result = memberService.createMember(req);
        return result;
    }
}
