package com.project.aminutesociety.attendance.service;

import com.project.aminutesociety.attendance.dto.AttendanceResDto;
import com.project.aminutesociety.attendance.dto.MyPageResDto;
import com.project.aminutesociety.attendance.dto.SetAttendanceDto;
import com.project.aminutesociety.attendance.repository.AttendanceRepository;
import com.project.aminutesociety.domain.Attendance;
import com.project.aminutesociety.domain.AttendanceVideo;
import com.project.aminutesociety.domain.User;
import com.project.aminutesociety.domain.Video;
import com.project.aminutesociety.user.repository.UserRepository;
import com.project.aminutesociety.util.exception.EntityNotFoundException;
import com.project.aminutesociety.util.response.ApiResponse;
import com.project.aminutesociety.video.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.valueOf;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService{
    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;

    @Override
    public ResponseEntity<ApiResponse<?>> setSaveTime(String userId, SetAttendanceDto setAttendanceDto) {
        // 유저가 존재하는지 확인하고 유저 가져오기
        User user = userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId + "인 사용자는 존재하지 않습니다."));

        List<String> viewingTimeList = setAttendanceDto.getViewingTime();

        // 리스트로 들어온 시청 기록들을 더함
        Integer sumViewingTime = sumStringTime(viewingTimeList);

        // 유저의 틈새시간 누적
        user.addSaveTime(sumViewingTime);

        // 출석 생성
        Attendance attendance = Attendance.builder()
                .date(formatDate())
                .viewingTime(sumViewingTime)
                .user(user)
                .attendanceVideos(new ArrayList<>())
                .build();
        attendanceRepository.save(attendance);

        List<Integer> videoIdList = setAttendanceDto.getVideoId();

        videoIdList.forEach(videoId -> {
            // 영상이 존재하는지 확인
            Video video = videoRepository.findById(valueOf(videoId))
                    .orElseThrow(() -> new EntityNotFoundException("id가 " +videoId + "인 영상은 존재하지 않습니다."));

            // attendanceVideo 생성
            AttendanceVideo attendanceVideo = AttendanceVideo.builder()
                    .attendance(attendance)
                    .video(video)
                    .build();
            attendance.getAttendanceVideos().add(attendanceVideo);
        });

        user.getAttendances().add(attendance);
        userRepository.save(user);

        ApiResponse<String> response = ApiResponse.createSuccessWithoutData(201, "출석이 기록되었습니다.");
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ApiResponse<?>> readAttendanceAll(String userId) {

        // 유저가 존재하는지 확인하고 유저 가져오기
        User user = userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId + "인 사용자는 존재하지 않습니다."));

        List<AttendanceResDto> attendanceResDtos = new ArrayList<>();

        // 유저의 모든 출석 가져오기
        List<Attendance> attendances = attendanceRepository.findByUser(user);

        attendances.forEach( attendance -> {
            // Dto로 변환
            AttendanceResDto attendanceResDto = AttendanceResDto.builder()
                    .id(attendance.getId())
                    .date(attendance.getDate())
                    .accumulatedTime(formateTime(attendance.getViewingTime()))
                    .build();
            attendanceResDtos.add(attendanceResDto);
        });

        MyPageResDto myPageResDto = MyPageResDto.builder()
                .totalTime(user.getSavedTime())
                .calendar(attendanceResDtos)
                .build();

        ApiResponse<MyPageResDto> response = ApiResponse.createSuccessWithData(myPageResDto, "마이페이지 API 호출 성공");
        return ResponseEntity.ok(response);
    }

    // 각 시청 시간을 분과 초로 나누어 초로 변환하여 더함
    private static Integer sumStringTime(List<String> viewingTime) {
        Integer sumViewingTimeInSeconds = 0;

        for (String time : viewingTime) {
            // 시간과 분을 나눔
            String[] parts = time.split(":");

            int minutes = Integer.parseInt(parts[0]); // 분
            int seconds = Integer.parseInt(parts[1]); // 초
            sumViewingTimeInSeconds += minutes * 60 + seconds; // 초로 변환하여 더함
        }
        return sumViewingTimeInSeconds;
    }

    // 현재 날짜 yyyy-mm-dd 형태롤 리턴
    private static String formatDate() {

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 날짜를 문자열로 변환
        String formattedDate = currentDate.format(formatter);
        return formattedDate;
    }

    // 초를 mm-ss로 변환
    private static String formateTime(Integer totalSeconds) {

        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;

        String formattedTime = String.format("%02d:%02d", minutes, seconds);
        return formattedTime;
    }
}