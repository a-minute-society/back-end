package com.project.aminutesociety.video.service;

import com.project.aminutesociety.domain.Category;
import com.project.aminutesociety.domain.User;
import com.project.aminutesociety.domain.UserCategory;
import com.project.aminutesociety.domain.Video;
import com.project.aminutesociety.user.repository.UserRepository;
import com.project.aminutesociety.util.exception.EntityNotFoundException;
import com.project.aminutesociety.util.response.ApiResponse;
import com.project.aminutesociety.video.dto.RecommendVideoRes;
import com.project.aminutesociety.video.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
        List<RecommendVideoRes.Res.RecommendVideoDto> recommendVideoDtos = recommendVideoList.stream()
                .map(video -> RecommendVideoRes.Res.RecommendVideoDto.builder()
                        .videoId(video.getId())
                        .categoryId(video.getCategory().getId())
                        .runTime(formatRunTime(video.getRunTime()))
                        .url(video.getUrl())
                        .videoTitle(video.getTitle())
                        .build())
                .collect(Collectors.toList());

        // 응답 객체 생성 및 반환
        RecommendVideoRes.Res responseDto = new RecommendVideoRes.Res();
        responseDto.setVideos(recommendVideoDtos);

        ApiResponse<RecommendVideoRes.Res> response = ApiResponse.createSuccessWithData(responseDto, "영상 추천에 성공하였습니다.");
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
