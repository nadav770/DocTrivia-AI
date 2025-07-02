package com.yourorg.doctrivia.service;

import com.yourorg.doctrivia.model.Question;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionGenerationService {

    public List<Question> generateQuestions(String documentContect) {
        List<Question> questions = new ArrayList<>();
        questions.add(Question.builder()
                .text("what is the main topic of thr document?")
                .answer("Artificial Intelligence")
                .build());
        questions.add(Question.builder()
                .text("Who is the intended audience of the document?")
                .answer("Developers")
                .build());
        return questions;

    }
}
