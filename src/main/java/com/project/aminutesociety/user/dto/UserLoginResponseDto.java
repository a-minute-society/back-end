package com.project.aminutesociety.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserLoginResponseDto {
    private Long id;
    private String userId;
    private String userName;
}
