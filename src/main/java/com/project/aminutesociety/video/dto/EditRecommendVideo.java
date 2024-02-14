package com.project.aminutesociety.video.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

public class EditRecommendVideo {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PUBLIC)
    public static class Req {
        private List<Integer> includeVideoIds = new ArrayList<>();
        private Integer excludeVideoId;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PUBLIC)
    @AllArgsConstructor(access = AccessLevel.PUBLIC)
    public static class Res {

        private EditedVideo editedVideo;

        @Getter
        @Builder
        @NoArgsConstructor(access = AccessLevel.PUBLIC)
        @AllArgsConstructor(access = AccessLevel.PUBLIC)
        public static class EditedVideo {
            private Long categoryId;
            private Long videoId;
            private String runTime;
            private String url;
            private String videoTitle;
        }

    }
}
