package com.project.aminutesociety.video.dto;

import lombok.*;

import java.util.List;

public class RecommendVideoRes {

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PUBLIC)
    public static class Res {

        List<RecommendVideoDto> videos;

        @Getter
        @Builder
        @NoArgsConstructor(access = AccessLevel.PUBLIC)
        @AllArgsConstructor(access = AccessLevel.PUBLIC)
        public static class RecommendVideoDto {
            private Long categoryId;
            private Long videoId;
            private String runTime;
            private String url;
            private String videoTitle;
        }
    }
}
