package com.project.aminutesociety.category.controller;

import com.project.aminutesociety.category.dto.CategorySetDto;
import com.project.aminutesociety.category.service.CategoryServiceImpl;
import com.project.aminutesociety.util.response.ApiResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Transactional
public class CategoryController {
    private final CategoryServiceImpl categorySeviceImpl;

    // 카테고리 전체 조회
    @GetMapping("/read-all")
    public ResponseEntity<ApiResponse<?>> readCategories() {
        return categorySeviceImpl.readCategories();
    }

    // 유저 관심분야 설정
    @PostMapping("/{userId}/set-categories")
    public ResponseEntity<ApiResponse<?>> setCategories(@PathVariable String userId, @RequestBody CategorySetDto categorySetDto) {
        return categorySeviceImpl.setCateogires(userId, categorySetDto);
    }
}