package com.project.aminutesociety.domain;

import com.project.aminutesociety.domain.Category;
import com.project.aminutesociety.domain.Scrap;
import com.project.aminutesociety.util.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "VIDEOS")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Video extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id")
    private Long id; // 자동 생성 PK

    @Column(name = "file_name")
    private String fileName; // 파일명

    @Column(name = "run_time")
    private Integer runTime; // 영상 길이 (초 단위)

    @Column(columnDefinition = "TEXT", name = "url")
    private String url; // 영상이 실제로 존재하는 S3 경로

    @Column(name = "video_title")
    private String title; // 영상 제목

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category; // 카테고리 FK

    @OneToMany(mappedBy = "video", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Scrap> scraps = new ArrayList<>();
}
