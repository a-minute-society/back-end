package com.project.aminutesociety.attendancevideo.entity;

import com.project.aminutesociety.attendance.entity.Attendance;
import com.project.aminutesociety.util.entity.BaseEntity;
import com.project.aminutesociety.video.entity.Video;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ATTENDANCES_VIDEOS")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class AttendanceVideo extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "attendance_video_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attendance_id")
    private Attendance attendance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id")
    private Video video;
}
