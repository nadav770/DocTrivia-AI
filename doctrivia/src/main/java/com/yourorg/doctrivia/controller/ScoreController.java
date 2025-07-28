package com.yourorg.doctrivia.controller;

import com.yourorg.doctrivia.dto.ScoreSummaryDto;
import com.yourorg.doctrivia.model.Score;
import com.yourorg.doctrivia.model.User;
import com.yourorg.doctrivia.repository.UserRepository;
import com.yourorg.doctrivia.security.JwtUtil;
import com.yourorg.doctrivia.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// controller/ScoreController.java
@RestController
@RequestMapping("/api/scores")
@RequiredArgsConstructor
public class ScoreController {
    private final ScoreService scoreService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    // שליפת כל התוצאות של המשתמש המחובר בלבד
    @GetMapping("/me")
    public ResponseEntity<List<Score>> getMyScores(@RequestHeader("Authorization") String authHeader) {
        String username = jwtUtil.getUsernameFromJwt(authHeader.substring(7));
        User user = userRepository.findByUsername(username).orElseThrow();
        return ResponseEntity.ok(scoreService.getUserScores(user.getId()));
    }

    // שליפת סיכום סטטיסטיקה אישי
    @GetMapping("/me/summary")
    public ResponseEntity<ScoreSummaryDto> getMyScoreSummary(@RequestHeader("Authorization") String authHeader) {
        String username = jwtUtil.getUsernameFromJwt(authHeader.substring(7));
        User user = userRepository.findByUsername(username).orElseThrow();
        List<Score> scores = scoreService.getUserScores(user.getId());
        int gamesPlayed = scores.size();
        int totalPoints = scores.stream().mapToInt(Score::getScore).sum();
        double avgScore = gamesPlayed == 0 ? 0 : (double) totalPoints / gamesPlayed;
        ScoreSummaryDto summary = new ScoreSummaryDto(gamesPlayed, totalPoints, avgScore);
        return ResponseEntity.ok(summary);
    }

    // שמירת תוצאה חדשה
    @PostMapping("/save")
    public ResponseEntity<Score> saveScore(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam Long documentId,
            @RequestParam int totalQuestions,
            @RequestParam int correctAnswers,
            @RequestParam int score) {
        String username = jwtUtil.getUsernameFromJwt(authHeader.substring(7));
        User user = userRepository.findByUsername(username).orElseThrow();
        Score saved = scoreService.saveScore(user.getId(), documentId, totalQuestions, correctAnswers, score);
        return ResponseEntity.ok(saved);
    }
}
