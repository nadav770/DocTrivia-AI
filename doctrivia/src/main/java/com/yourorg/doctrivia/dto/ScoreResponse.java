package com.yourorg.doctrivia.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScoreResponse {
    private int totalScore;
    private int correctAnswers;
    private int totalQuestions;
}
