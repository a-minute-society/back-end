package com.project.aminutesociety.domain;

import com.project.aminutesociety.user.dto.UserSignUpDto;
import com.project.aminutesociety.util.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/*
setter 사용 금지
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId; // 아이디
    private String userName; // 유저 이름
    private String userPw; // 비밀번호

    private Integer time; // 소요시간
    private String depart; // 출발지
    private String arrive; // 도착지
    private Integer savedTime; // 절약한 시간

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<UserCategory> userCategories = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Scrap> scraps = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Attendance> attendances = new ArrayList<>();

    public static User signUp(UserSignUpDto.Req req) { return req.toEntity(); }

    // 소요시간 변경
    public void changeTime(Integer time) {
        this.time = time;
    }

    // 절약한 시간 누적
    public void addSaveTime(Integer savedTime) {
        if(this.savedTime != null )
            this.savedTime += savedTime;
        else
            this.savedTime = savedTime;
    }

}
