package com.project.aminutesociety.example.service;

import com.project.aminutesociety.example.dto.CreateMemberDto;
import com.project.aminutesociety.example.entity.Member;
import com.project.aminutesociety.example.repository.MemberRepository;
import com.project.aminutesociety.util.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    public ResponseEntity<ApiResponse<?>> createMember(CreateMemberDto.Req req) {
        Optional<Member> optionalMember = memberRepository.findMemberByUserId(req.getUserId());

        if(optionalMember.isPresent()) {
            ApiResponse<CreateMemberDto.Res> res = ApiResponse.createFailWithoutData(400, "이미 존재하는 회원입니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        }

        Member save = memberRepository.save(req.toEntity());

        CreateMemberDto.Res data = new CreateMemberDto.Res(save.getId(), save.getUserId());
        ApiResponse<CreateMemberDto.Res> res = ApiResponse.createSuccessWithData(data, "회원가입이 정상적으로 처리되었습니다.");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
