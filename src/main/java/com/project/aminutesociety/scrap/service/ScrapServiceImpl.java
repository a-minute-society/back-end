package com.project.aminutesociety.scrap.service;

import com.project.aminutesociety.domain.Scrap;
import com.project.aminutesociety.domain.User;
import com.project.aminutesociety.domain.Video;
import com.project.aminutesociety.scrap.repository.ScrapRepository;
import com.project.aminutesociety.user.repository.UserRepository;
import com.project.aminutesociety.util.exception.EntityDuplicatedException;
import com.project.aminutesociety.util.exception.EntityNotFoundException;
import com.project.aminutesociety.util.response.ApiResponse;
import com.project.aminutesociety.video.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScrapServiceImpl implements ScrapService{

    private final ScrapRepository scrapRepository;
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;

    @Override
    public ResponseEntity<ApiResponse<?>> createScrap(String userId, Long videoId) {

        // 유저가 존재하는지 확인하고 유저 가져오기
        User user = userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId + "인 사용자는 존재하지 않습니다."));

        // 비디오가 존재하는지 확인하고 비디오 가져오기
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new EntityNotFoundException("비디오 아이디가 " + videoId + "인 비디오는 존재하지 않습니다."));

        // 이미 스크랩이 된 상태라면 오류 발생
        if(scrapRepository.findByUserAndVideo(user, video).isPresent()) {
            throw new EntityDuplicatedException("이미 스크랩 한 영상입니다.");
        }

        // 스크랩 저장
        scrapRepository.save(Scrap.createScrapEntity(user, video));

        // 응답 반환
        ApiResponse<String> data = ApiResponse.createSuccessWithoutData(HttpStatus.CREATED.value(), "스크랩이 완료되었습니다.");
        return ResponseEntity.status(HttpStatus.CREATED).body(data);
    }
}
