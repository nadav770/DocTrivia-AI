package com.yourorg.doctrivia.controller;

import com.yourorg.doctrivia.model.Score;
import com.yourorg.doctrivia.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scores")
@RequiredArgsConstructor
public class ScoreController {
    private final ScoreService scoreService;

    // שליפת ניקוד אחרון למשתמש ולמסמך
    @GetMapping("/user/{userId}/document/{documentId}")
    public ResponseEntity<Score> getScore(
            @PathVariable Long userId,
            @PathVariable Long documentId) {
        Score score = scoreService.getLatestScore(userId, documentId);
        return ResponseEntity.ok(score);
    }

    // שמירת תוצאה (רשות – אפשר לקרוא לה בסיום משחק)
    @PostMapping("/save")
    public ResponseEntity<Score> saveScore(
            @RequestParam Long userId,
            @RequestParam Long documentId,
            @RequestParam int totalQuestions,
            @RequestParam int correctAnswers,
            @RequestParam int score) {
        Score saved = scoreService.saveScore(userId, documentId, totalQuestions, correctAnswers, score);
        return ResponseEntity.ok(saved);
    }

    // שליפת כל התוצאות של משתמש (leaderboard פרטי)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Score>> getUserScores(@PathVariable Long userId) {
        return ResponseEntity.ok(scoreService.getUserScores(userId));
    }
}
