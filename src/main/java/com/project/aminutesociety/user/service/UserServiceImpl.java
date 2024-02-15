package com.project.aminutesociety.user.service;

import com.project.aminutesociety.category.dto.CategoryResponseDto;
import com.project.aminutesociety.user.dto.*;
import com.project.aminutesociety.domain.User;
import com.project.aminutesociety.user.repository.UserRepository;
import com.project.aminutesociety.domain.UserCategory;
import com.project.aminutesociety.util.exception.EntityNotFoundException;
import com.project.aminutesociety.util.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<ApiResponse<?>> signUp(UserSignUpDto.Req req) {
        Optional<User> optionalUser = userRepository.findUserByUserId(req.getUserId());

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
        String userId = userLoginRequestDto.getUserId();

        // 유저가 존재하는지 확인하고 유저 가져오기
        User user = userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId + "인 사용자는 존재하지 않습니다."));

        String savedUserPW = user.getUserPw();

        // 비밀번호가 일치하는지 확인
        if(!passwordEncoder.matches(userLoginRequestDto.getUserPw(), savedUserPW)){
            ApiResponse<UserLoginResponseDto> res = ApiResponse.loginFailWithoutData(400, "비밀번호가 일치하지 않습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        }

        List<CategoryResponseDto> categoryResponseDtos = new ArrayList<>();
        for (UserCategory userCategory : user.getUserCategories()) {
            categoryResponseDtos.add(CategoryResponseDto.fromUserCategory(userCategory));
        }

        UserLoginResponseDto userLoginResponseDto = UserLoginResponseDto.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .userName(user.getUserName())
                .build();

        ApiResponse<UserLoginResponseDto> res = ApiResponse.loginSuccessWithoutData(userLoginResponseDto, "로그인이 완료되었습니다.");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @Override
    public ResponseEntity<ApiResponse<?>> checkUserId(String userId) {
        // 유저가 존재하는지 확인하고 유저 가져오기
        User user = userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId + "인 사용자는 존재하지 않습니다."));

        // 유저 정보 임시 호출
        UserLoginResponseDto userLoginResponseDto = UserLoginResponseDto.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .userName(user.getUserName())
                .build();

        ApiResponse<UserLoginResponseDto> res = ApiResponse.checkUserIdSuccessWithData(userLoginResponseDto, "마이페이지 API 호출 성공");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @Override
    public ResponseEntity<ApiResponse<?>> userInfo(String userId) {
        // 유저가 존재하는지 확인하고 유저 가져오기
        User user = userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId + "인 사용자는 존재하지 않습니다."));

        List<CategoryResponseDto> categoryResponseDtos = new ArrayList<>();
        List<UserCategory> userCategories = user.getUserCategories();

        for (UserCategory userCategory : userCategories) {
            CategoryResponseDto categoryResponseDto = CategoryResponseDto.fromUserCategory(userCategory);
            categoryResponseDtos.add(categoryResponseDto);
        }

        UesrInfoResponseDto userInfoResponseDto = UesrInfoResponseDto.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .userName(user.getUserName())
                .userCategories(categoryResponseDtos)
                .time(user.getTime())
                .build();

        ApiResponse<UesrInfoResponseDto> response = ApiResponse.checkUserIdSuccessWithData(userInfoResponseDto, "사용자 정보 조회가 완료되었습니다.");
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @Override
    public ResponseEntity<ApiResponse<?>> changeTime(String userId, ChangeTimeDto changeTimeDto) {
        // 유저가 존재하는지 확인하고 유저 가져오기
        User user = userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId + "인 사용자는 존재하지 않습니다."));

        Integer time = changeTimeDto.getTime();

        // 시간 변경
        user.changeTime(time);
        userRepository.save(user);

        ApiResponse<UesrInfoResponseDto> response = ApiResponse.createFailWithoutData(200, "시간 설정이 완료되었습니다.");
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}
