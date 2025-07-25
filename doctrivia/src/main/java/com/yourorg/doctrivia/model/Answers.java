package com.yourorg.doctrivia.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "answers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Answers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String selectedAnswer;
    private boolean isCorrect;

    @ManyToOne
    private User user;

    @ManyToOne
    private Document document;

    @ManyToOne
    private Question question;

    private LocalDateTime answeredAt = LocalDateTime.now();
}
