package com.yourorg.doctrivia.model;

import jakarta.persistence.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "questions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "document_id")
    private document document;

    @Column(columnDefinition = "TEXT")
    private String question;

    private String correctAnswer;
    private String option2;
    private String option3;
    private String option4;
}
