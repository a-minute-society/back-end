package com.project.aminutesociety.scrap.service;

import com.project.aminutesociety.domain.Scrap;
import com.project.aminutesociety.domain.User;
import com.project.aminutesociety.domain.Video;
import com.project.aminutesociety.scrap.dto.GetUserScrapDto;
import com.project.aminutesociety.scrap.repository.ScrapRepository;
import com.project.aminutesociety.user.repository.UserRepository;
import com.project.aminutesociety.util.exception.EntityDuplicatedException;
import com.project.aminutesociety.util.exception.EntityNotFoundException;
import com.project.aminutesociety.util.response.ApiResponse;
import com.project.aminutesociety.video.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    @Override
    public ResponseEntity<ApiResponse<?>> deleteScrap(String userId, Long videoId) {

        // 유저가 존재하는지 확인하고 유저 가져오기
        User user = userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId + "인 사용자는 존재하지 않습니다."));

        // 비디오가 존재하는지 확인하고 비디오 가져오기
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new EntityNotFoundException("비디오 아이디가 " + videoId + "인 비디오는 존재하지 않습니다."));

        // 스크랩되지 않은 영상이면 오류 발생
        if(scrapRepository.findByUserAndVideo(user, video).isEmpty()) {
            ApiResponse<String> data = ApiResponse.createFailWithoutData(HttpStatus.BAD_REQUEST.value(), "스크랩하지 않은 영상입니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(data);
        }

        // 스크랩 취소
        scrapRepository.deleteByUserAndVideo(user, video);

        // 응답 반환
        ApiResponse<String> data = ApiResponse.createSuccessWithoutData(HttpStatus.ACCEPTED.value(), "스크랩이 취소되었습니다.");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(data);
    }

    @Override
    public ResponseEntity<ApiResponse<?>> getUserScrap(String userId, String type) {

        // 유저가 존재하는지 확인하고 유저 가져오기
        User user = userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId + "인 사용자는 존재하지 않습니다."));

        List<GetUserScrapDto.Res.ScrapList> scraps = new ArrayList<>();

        if ("home".equals(type)) { // 홈에서 조회하는 경우
            scraps = scrapRepository.findByUserIdOrderByCreatedAtDesc(user.getId(), PageRequest.of(0, 5))
                    .stream()
                    .map(GetUserScrapDto.Res.ScrapList::EntityToDto)
                    .collect(Collectors.toList());
        } else { // 모든 스크랩 글을 조회하는 경우
            scraps = scrapRepository.findByUserIdOrderByCreatedAtDesc(user.getId(), Pageable.unpaged())
                    .stream()
                    .map(GetUserScrapDto.Res.ScrapList::EntityToDto)
                    .collect(Collectors.toList());
        }

        GetUserScrapDto.Res response = GetUserScrapDto.Res.builder().scraps(scraps).build();

        // 응답 반환
        ApiResponse<GetUserScrapDto.Res> data = ApiResponse.createSuccessWithData(response, "스크랩 영상이 정상적으로 조회되었습니다.");
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

}
