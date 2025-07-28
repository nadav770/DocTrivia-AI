package com.yourorg.doctrivia.repository;

import com.yourorg.doctrivia.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> findByUserId(Long userId);
    List<Score> findByUserIdAndDocumentId(Long userId, Long documentId);
    Score findTopByUserIdAndDocumentIdOrderByPlayedAtDesc(Long userId, Long documentId);
    void deleteByDocumentId(Long documentId);

}
