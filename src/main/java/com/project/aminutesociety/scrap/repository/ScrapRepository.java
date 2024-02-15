package com.project.aminutesociety.scrap.repository;

import com.project.aminutesociety.domain.Scrap;
import com.project.aminutesociety.domain.User;
import com.project.aminutesociety.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    Optional<Scrap> findByUserAndVideo(User user, Video video);
}
