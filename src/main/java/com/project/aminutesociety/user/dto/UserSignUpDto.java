package com.project.aminutesociety.user.dto;

import com.project.aminutesociety.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserSignUpDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Req {
        private String userId;
        private String userName;
        private String userPw;

        public User toEntity() {
            return User.builder()
                    .userId(this.userId)
                    .userName(this.userName)
                    .userPw(this.userPw)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Res {
        private Long id;
        private String userName;
        private String userId;

        @Builder
        public Res(Long id, String userName, String userId) {
            this.id = id;
            this.userName = userName;
            this.userId = userId;
        }
    }
}
