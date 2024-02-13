package com.project.aminutesociety.video.entity;

import com.project.aminutesociety.util.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "VIDEOS")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Video extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "video_id")
    private Long id; // 자동 생성 PK

    @Column(name = "file_name")
    private String fileName; // 파일명

    @Column(name = "run_time")
    private Integer runTime; // 영상 길이 (초 단위)

    @Column(columnDefinition = "TEXT", name = "url")
    private String url; // 영상이 실제로 존재하는 S3 경로

    // TODO : 카테고리 (FK) 추가


}
