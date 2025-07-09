package com.yourorg.doctrivia.model;

import jakarta.persistence.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Entity
@Table(name = "scores")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "document_id")
    private document document;

    private int totalQuestions;
    private int correctAnswers;
    private int score;

    @Builder.Default
    private LocalDateTime playedAt = LocalDateTime.now();
}
