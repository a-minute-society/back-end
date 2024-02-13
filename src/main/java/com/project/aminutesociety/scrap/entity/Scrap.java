package com.project.aminutesociety.scrap.entity;

import com.project.aminutesociety.user.entity.User;
import com.project.aminutesociety.video.entity.Video;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "SCRAPS")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Scrap {

    @Id @GeneratedValue
    @Column(name = "scrap_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id")
    private Video video;
}
