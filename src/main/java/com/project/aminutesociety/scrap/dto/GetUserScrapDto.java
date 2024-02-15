package com.project.aminutesociety.scrap.dto;

import com.project.aminutesociety.domain.Scrap;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

public class GetUserScrapDto {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Res {
        List<ScrapList> scraps = new ArrayList<>();

        @Getter
        @Builder
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        @AllArgsConstructor(access = AccessLevel.PROTECTED)
        public static class ScrapList {
            private Long categoryId;
            private Long videoId;
            private String videoTitle;
            private String videoRunTime;
            private String url;

            public static ScrapList EntityToDto(Scrap scrap) {
                return ScrapList.builder()
                        .categoryId(scrap.getVideo().getId())
                        .videoId(scrap.getVideo().getId())
                        .videoTitle(scrap.getVideo().getTitle())
                        .url(scrap.getVideo().getUrl())
                        .videoRunTime(formatRunTime(scrap.getVideo().getRunTime()))
                        .build();
            }

            // 재생 시간 응답을 위해 포맷 작성
            private static String formatRunTime(Integer runTime) {
                int hours = runTime / 3600;
                int minutes = (runTime % 3600) / 60;
                int seconds = runTime % 60;
                return String.format("%02d:%02d:%02d", hours, minutes, seconds);
            }
        }
    }
}
