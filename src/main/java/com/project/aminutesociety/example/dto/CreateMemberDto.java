package com.project.aminutesociety.example.dto;

import com.project.aminutesociety.example.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CreateMemberDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Req {
        private String userId;
        private String userPw;

        public Member toEntity() {
            return Member.builder()
                    .userId(this.userId)
                    .userPw(this.userPw)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Res {
        private Long id;
        private String userId;

        @Builder
        public Res(Long id, String userId) {
            this.id = id;
            this.userId = userId;
        }
    }
}
