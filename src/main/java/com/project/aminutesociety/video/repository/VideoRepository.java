package com.project.aminutesociety.video.repository;

import com.project.aminutesociety.domain.Category;
import com.project.aminutesociety.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findByCategory(Category category);
}
