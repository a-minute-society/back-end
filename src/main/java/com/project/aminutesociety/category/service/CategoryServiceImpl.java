package com.project.aminutesociety.category.service;

import com.project.aminutesociety.category.dto.CategoryResponseDto;
import com.project.aminutesociety.category.dto.CategorySetDto;
import com.project.aminutesociety.domain.Category;
import com.project.aminutesociety.category.repository.CategoryRepository;
import com.project.aminutesociety.user.dto.UserSignUpDto;
import com.project.aminutesociety.domain.User;
import com.project.aminutesociety.user.repository.UserRepository;
import com.project.aminutesociety.domain.UserCategory;
import com.project.aminutesociety.util.exception.EntityNotFoundException;
import com.project.aminutesociety.util.response.ApiResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    // 카테고리 전체 응답
    @Override
    public ResponseEntity<ApiResponse<?>> readCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryResponseDto> categoryResponseDtos = new ArrayList<>();

        for (Category category : categories) {
            CategoryResponseDto categoryResponseDto = CategoryResponseDto.builder()
                    .categoryId(category.getId())
                    .categoryName(category.getName())
                    .build();
            categoryResponseDtos.add(categoryResponseDto);
        }

        ApiResponse<List<CategoryResponseDto>> res = ApiResponse.readCategoriesSuccessWithData(categoryResponseDtos, "카테고리 조회가 완료되었습니다.");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    // 관심있는 분야 설정
    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> setCateogires(String userId, CategorySetDto categorySetDto) {

        // 유저가 존재하는지 확인하고 유저 가져오기
        User user = userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId + "인 사용자는 존재하지 않습니다."));

        // 새로운 관심분야 설정
        List<UserCategory> userCategories = new ArrayList<>();

        for (Long categoryId : categorySetDto.getCategoryIds()) {
            Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
            // category id가 유효한지 확인
            if (!optionalCategory.isPresent()) {
                // id 가 유효하지 않다면 기존에 세팅했던 카테고리 초기화
                user.getUserCategories().clear();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.readCategoriesFailWithoutData(400, "id가 "+categoryId + "인 카테고리가 존재하지 않습니다."));
            }
            Category category = optionalCategory.get();

            // UserCategory 엔티티 생성
            UserCategory userCategory =
                    UserCategory.builder()
                    .user(user)
                    .category(category)
                    .build();
            // 유저와 연결
            user.getUserCategories().add(userCategory);
        }

        ApiResponse<String> response = ApiResponse.createSuccessWithoutData(200, "카테고리 설정이 완료되었습니다.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<ApiResponse<?>> changeCategories(String userId, CategorySetDto categorySetDto) {

        // 유저가 존재하는지 확인하고 유저 가져오기
        User user = userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId + "인 사용자는 존재하지 않습니다."));

        List<Category> categories = new ArrayList<>();

        // category id가 유효한지 먼저 확인
        for (Long categoryId : categorySetDto.getCategoryIds()) {
            Optional<Category> optionalCategory = categoryRepository.findById(categoryId);

            if (!optionalCategory.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.readCategoriesFailWithoutData(400, "id가 " + categoryId + "인 카테고리가 존재하지 않습니다."));
            }
            Category category = optionalCategory.get();
            categories.add(category);
        }

        // 모두  유효한 id -> 새로운 관심분야 설정
        user.getUserCategories().clear();
        List<UserCategory> userCategories = new ArrayList<>();

        for(Category category : categories ) {
            UserCategory userCategory =
                    UserCategory.builder()
                            .user(user)
                            .category(category)
                            .build();
            // 유저와 연결
            user.getUserCategories().add(userCategory);
        }

        ApiResponse<String> response = ApiResponse.createSuccessWithoutData(200, "카테고리 수정이 완료되었습니다.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}