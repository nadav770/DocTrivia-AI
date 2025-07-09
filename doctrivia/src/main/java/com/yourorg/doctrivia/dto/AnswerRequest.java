package com.yourorg.doctrivia.dto;
import lombok.*;



@Data
public class AnswerRequest {
    private Long userId;
    private Long documentId;
    private Long questionId;
    private String selectedAnswer;
}
