package com.project.aminutesociety.example.service;

import com.project.aminutesociety.example.dto.CreateMemberDto;
import com.project.aminutesociety.util.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface MemberService {
    ResponseEntity<ApiResponse<?>> createMember(CreateMemberDto.Req req);
}
