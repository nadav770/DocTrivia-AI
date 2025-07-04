package com.yourorg.doctrivia.dto;

import lombok.Data;
import java.util.List;

@Data
public class TriviaQuestionDto {
    private String question;
    private List<String> options;
    private int correctIndex;
}
