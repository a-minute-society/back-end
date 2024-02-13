package com.project.aminutesociety.category.dto;

import com.project.aminutesociety.usercategory.entity.UserCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CategoryResponseDto {
    private Long categoryId;
    private String categoryName;

    public static CategoryResponseDto fromUserCategory(UserCategory userCategory) {
        return CategoryResponseDto.builder()
                .categoryId(userCategory.getCategory().getId())
                .categoryName(userCategory.getCategory().getName())
                .build();
    }
}
