package com.project.aminutesociety.example.service;

import com.project.aminutesociety.example.dto.UserLoginRequestDto;
import com.project.aminutesociety.example.dto.UserLoginResponseDto;
import com.project.aminutesociety.example.dto.UserSignUpDto;
import com.project.aminutesociety.example.entity.User;
import com.project.aminutesociety.example.repository.userRepository;
import com.project.aminutesociety.util.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final userRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<ApiResponse<?>> signUp(UserSignUpDto.Req req) {
        Optional<User> optionalUser = userRepository.findMemberByUserId(req.getUserId());

        // 이미 존재하는 회원인지 확인
        if(optionalUser.isPresent()) {
            ApiResponse<UserSignUpDto.Res> res = ApiResponse.createFailWithoutData(400, "이미 아이디가 존재합니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        }

        // 비밀번호 암호화
        String hashedPassword = passwordEncoder.encode(req.getUserPw());

        // user 저장
        User user = User.builder()
                .userId(req.getUserId())
                .userName(req.getUserName())
                .userPw(hashedPassword)
                .build();

        userRepository.save(user);

        UserSignUpDto.Res data = new UserSignUpDto.Res(user.getId(), user.getUserName(), user.getUserId());
        ApiResponse<UserSignUpDto.Res> res = ApiResponse.createSuccessWithData(data, "회원가입이 정상적으로 처리되었습니다.");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @Override
    public ResponseEntity<ApiResponse<?>> login(UserLoginRequestDto userLoginRequestDto){
        // 아이디 존재하는지 확인
        Optional<User> optionalUser = userRepository.findMemberByUserId(userLoginRequestDto.getUserId());
        if(!optionalUser.isPresent()){
            ApiResponse<UserLoginResponseDto> res = ApiResponse.loginFailWithoutData(400, "존재하지 않는 아이디입니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        }

        User savedUser = optionalUser.get();
        String savedUserPW = savedUser.getUserPw();

        // 비밀번호가 일치하는지 확인
        if(!passwordEncoder.matches(userLoginRequestDto.getUserPw(), savedUserPW)){
            ApiResponse<UserLoginResponseDto> res = ApiResponse.loginFailWithoutData(400, "비밀번호가 일치하지 않습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        }

        UserLoginResponseDto userLoginResponseDto = UserLoginResponseDto.builder()
                .id(savedUser.getId())
                .userId(savedUser.getUserId())
                .userName(savedUser.getUserName())
                .build();

        ApiResponse<UserLoginResponseDto> res = ApiResponse.loginSuccessWithoutData(userLoginResponseDto, "로그인이 완료되었습니다.");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @Override
    public ResponseEntity<ApiResponse<?>> checkUserId(String userId) {
        Optional<User> optionalUser = userRepository.findMemberByUserId(userId);

        // 존재하는 회원인지 확인
        if(!optionalUser.isPresent()) {
            ApiResponse<UserSignUpDto.Res> res = ApiResponse.createFailWithoutData(400, "회원을 찾을 수 없습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        }

        User savedUser = optionalUser.get();

        // 유저 정보 임시 호출
        UserLoginResponseDto userLoginResponseDto = UserLoginResponseDto.builder()
                .id(savedUser.getId())
                .userId(savedUser.getUserId())
                .userName(savedUser.getUserName())
                .build();

        ApiResponse<UserLoginResponseDto> res = ApiResponse.checkUserIdSuccessWithData(userLoginResponseDto, "마이페이지 API 호출 성공");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
