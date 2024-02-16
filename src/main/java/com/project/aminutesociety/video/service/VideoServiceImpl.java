package com.project.aminutesociety.video.service;

import com.project.aminutesociety.domain.Category;
import com.project.aminutesociety.domain.User;
import com.project.aminutesociety.domain.UserCategory;
import com.project.aminutesociety.domain.Video;
import com.project.aminutesociety.user.repository.UserRepository;
import com.project.aminutesociety.util.exception.EntityNotFoundException;
import com.project.aminutesociety.util.response.ApiResponse;
import com.project.aminutesociety.video.dto.EditRecommendVideo;
import com.project.aminutesociety.video.dto.RecommendVideo;
import com.project.aminutesociety.video.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Collections;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService{

    private final VideoRepository videoRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<ApiResponse<?>> recommendVideo(String userId, Integer time) {

        // 유저가 존재하는지 확인하고 유저 가져오기
        User user = userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId + "인 사용자는 존재하지 않습니다."));

        // 유저의 관심 카테고리 가져오기
        List<UserCategory> userCategories = user.getUserCategories();
        List<Video> categoryVideos = new ArrayList<>();

        // 해당 카테고리의 영상들 가져오기
        for (UserCategory userCategory : userCategories) {
            Category category = userCategory.getCategory();
            List<Video> videos = videoRepository.findByCategory(category);
            categoryVideos.addAll(videos);
        }

        // 영상들을 runTime 기준으로 오름차순 정렬
        categoryVideos.sort(Comparator.comparingInt(Video::getRunTime));

        // time 내에 시청할 수 있는 재생목록 만들기
        List<Video> recommendVideoList = new ArrayList<>();
        int currentTime = 0;
        for (Video video : categoryVideos) {
            if (currentTime + video.getRunTime() <= time) {
                recommendVideoList.add(video);
                currentTime += video.getRunTime();
            }
        }

        // DTO로 변환
        List<RecommendVideo.Res.RecommendVideoDto> recommendVideoDtos = recommendVideoList.stream()
                .map(video -> RecommendVideo.Res.RecommendVideoDto.builder()
                        .videoId(video.getId())
                        .categoryId(video.getCategory().getId())
                        .runTime(formatRunTime(video.getRunTime()))
                        .url(video.getUrl())
                        .videoTitle(video.getTitle())
                        .build())
                .collect(Collectors.toList());

        // 응답 객체 생성 및 반환
        RecommendVideo.Res responseDto = new RecommendVideo.Res();
        responseDto.setVideos(recommendVideoDtos);

        ApiResponse<RecommendVideo.Res> response = ApiResponse.createSuccessWithData(responseDto, "영상 추천에 성공하였습니다.");
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ApiResponse<?>> editRecommendVideo(String userId, Integer time, EditRecommendVideo.Req req) {

        // 유저가 존재하는지 확인하고 유저 가져오기
        User user = userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId + "인 사용자는 존재하지 않습니다."));

        // 유저의 관심 카테고리 가져오기
        List<UserCategory> userCategories = user.getUserCategories();
        List<Video> categoryVideos = new ArrayList<>();

        // 해당 카테고리의 영상들 가져오기
        for (UserCategory userCategory : userCategories) {
            Category category = userCategory.getCategory();
            List<Video> videos = videoRepository.findByCategory(category);
            categoryVideos.addAll(videos);
        }

        // includeVideoIds에 포함되지 않고, excludeVideoId에 해당하지 않는 영상 필터링
        List<Video> filteredVideos = categoryVideos.stream()
                .filter(video -> !req.getIncludeVideoIds().contains(video.getId().intValue()) && !video.getId().equals(req.getExcludeVideoId().longValue()))
                .collect(Collectors.toList());

        // excludeVideoId의 영상 시간을 제외한 남은 시간 계산
        Video excludeVideo = videoRepository.findById(req.getExcludeVideoId().longValue())
                .orElse(null);
        int excludeTime = excludeVideo != null ? excludeVideo.getRunTime() : 0;
        int remainTime = time - excludeTime;

        // 남은 시간에 맞는 영상 하나 선택
        Video selectedVideo = filteredVideos.stream()
                .filter(video -> video.getRunTime() <= remainTime)
                .findFirst()
                .orElse(null);

        // dto로 변환 사용 Builder 패턴
        EditRecommendVideo.Res response = null;
        if (selectedVideo != null) {
            EditRecommendVideo.Res.EditedVideo editedVideo = EditRecommendVideo.Res.EditedVideo.builder()
                    .categoryId(selectedVideo.getCategory().getId())
                    .videoId(selectedVideo.getId())
                    .runTime(formatRunTime(selectedVideo.getRunTime()))
                    .url(selectedVideo.getUrl())
                    .videoTitle(selectedVideo.getTitle())
                    .build();

            response = EditRecommendVideo.Res.builder()
                    .editedVideo(editedVideo)
                    .build();
        }

        // 응답 반환
        ApiResponse<EditRecommendVideo.Res> apiResponse = ApiResponse.createSuccessWithData(response, "영상 추천에 성공하였습니다.");
        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public ResponseEntity<ApiResponse<?>> getVideoRecommend(String userId) {

        // 유저가 존재하는지 확인하고 유저 가져오기
        User user = userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId + "인 사용자는 존재하지 않습니다."));

        // 유저의 관심 카테고리 가져오기
        List<UserCategory> userCategories = user.getUserCategories();
        List<Video> categoryVideos = new ArrayList<>();

        // 해당 카테고리의 영상들 가져오기
        for (UserCategory userCategory : userCategories) {
            Category category = userCategory.getCategory();
            List<Video> videos = videoRepository.findByCategory(category);
            categoryVideos.addAll(videos);
        }

        // recommendVideoList = categoryId가 같은 것들 중 랜덤으로 2개씩의 영상을 추출
        List<Video> recommendVideoList = new ArrayList<>();

        // 카테고리별로 영상을 가져와서 각 카테고리별로 2개의 랜덤 영상을 선택
        for (UserCategory userCategory : userCategories) {
            Category category = userCategory.getCategory();
            List<Video> videos = videoRepository.findByCategory(category);

            // 리스트를 섞고 처음 2개의 영상을 선택
            Collections.shuffle(videos);
            List<Video> randomVideos = videos.stream().limit(2).collect(Collectors.toList());
            recommendVideoList.addAll(randomVideos);
        }

        // DTO로 변환
        List<RecommendVideo.Res.RecommendVideoDto> recommendVideoDtos = recommendVideoList.stream()
                .map(video -> RecommendVideo.Res.RecommendVideoDto.builder()
                        .videoId(video.getId())
                        .categoryId(video.getCategory().getId())
                        .runTime(formatRunTime(video.getRunTime()))
                        .url(video.getUrl())
                        .videoTitle(video.getTitle())
                        .build())
                .collect(Collectors.toList());

        // 응답 객체 생성 및 반환
        RecommendVideo.Res responseDto = new RecommendVideo.Res();
        responseDto.setVideos(recommendVideoDtos);

        // 응답 반환
        ApiResponse<RecommendVideo.Res> response = ApiResponse.createSuccessWithData(responseDto, "영상 추천에 성공하였습니다.");
        return ResponseEntity.ok(response);

    }

    // 재생 시간 응답을 위해 포맷 작성
    private String formatRunTime(Integer runTime) {
        int hours = runTime / 3600;
        int minutes = (runTime % 3600) / 60;
        int seconds = runTime % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
