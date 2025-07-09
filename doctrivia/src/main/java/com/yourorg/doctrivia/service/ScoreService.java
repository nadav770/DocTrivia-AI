package com.yourorg.doctrivia.service;

import com.yourorg.doctrivia.model.Score;
import com.yourorg.doctrivia.model.User;
import com.yourorg.doctrivia.model.document;
import com.yourorg.doctrivia.repository.ScoreRepository;
import com.yourorg.doctrivia.repository.UserRepository;
import com.yourorg.doctrivia.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScoreService {
    private final ScoreRepository scoreRepository;
    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;

    // שמירת תוצאה
    public Score saveScore(Long userId, Long documentId, int totalQuestions, int correctAnswers, int score) {
        User user = userRepository.findById(userId).orElseThrow();
        document doc = documentRepository.findById(documentId).orElseThrow();
        Score newScore = Score.builder()
                .user(user)
                .document(doc)
                .totalQuestions(totalQuestions)
                .correctAnswers(correctAnswers)
                .score(score)
                .build();
        return scoreRepository.save(newScore);
    }

    // שליפת ניקוד אחרון למשתמש+מסמך
    public Score getLatestScore(Long userId, Long documentId) {
        return scoreRepository.findTopByUserIdAndDocumentIdOrderByPlayedAtDesc(userId, documentId);
    }

    // שליפת כל התוצאות למשתמש
    public List<Score> getUserScores(Long userId) {
        return scoreRepository.findByUserId(userId);
    }
}
