package com.project.aminutesociety.category.service;

import com.project.aminutesociety.category.dto.CategorySetDto;
import com.project.aminutesociety.util.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface CategoryService {

    ResponseEntity<ApiResponse<?>> readCategories();

    ResponseEntity<ApiResponse<?>> setCateogires(String userId, CategorySetDto categorySetDto);

    ResponseEntity<ApiResponse<?>> changeCategories(String userId, CategorySetDto categorySetDto);
}
