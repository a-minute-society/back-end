package com.project.aminutesociety.domain;

import com.project.aminutesociety.util.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ATTENDANCES")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Attendance extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id")
    private Long id; // 자동 생성 PK

    @Column(name = "atendance_date")
    private String date; // 출석 날짜

    @Column(name = "viewing_time")
    private Integer viewingTime; // 시청 시간

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 유저 FK

    @OneToMany(mappedBy = "attendance", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<AttendanceVideo> attendanceVideos = new ArrayList<>(); // 시청한 영상
}
