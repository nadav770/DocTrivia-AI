package com.yourorg.doctrivia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreSummaryDto {
    private int gamesPlayed;
    private int totalPoints;
    private double avgScore;
}