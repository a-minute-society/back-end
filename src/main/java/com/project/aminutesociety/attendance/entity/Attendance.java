package com.project.aminutesociety.attendance.entity;

import com.project.aminutesociety.user.entity.User;
import com.project.aminutesociety.util.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ATTENDANCES")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Attendance extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "attendance_id")
    private Long id; // 자동 생성 PK

    @Column(name = "atendance_date")
    private String date; // 출석 날짜

    @Column(name = "viewing_time")
    private Integer viewingTime; // 시청 시간

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 유저 FK
}
