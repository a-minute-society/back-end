package com.project.aminutesociety.scrap.repository;

import com.project.aminutesociety.domain.Scrap;
import com.project.aminutesociety.domain.User;
import com.project.aminutesociety.domain.Video;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    Optional<Scrap> findByUserAndVideo(User user, Video video);
    void deleteByUserAndVideo(User user, Video video);

    // 사용자 ID로 스크랩 목록 조회, 최신 순으로 정렬
    @Query("SELECT s FROM Scrap s WHERE s.user.id = :userId ORDER BY s.createdAt DESC")
    List<Scrap> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
}
