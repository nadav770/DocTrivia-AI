package com.yourorg.doctrivia.controller;

import com.yourorg.doctrivia.dto.DocumentContentRequest;
import com.yourorg.doctrivia.dto.TriviaQuestionDto;
import com.yourorg.doctrivia.service.AiQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiQuestionService aiQuestionService;

    @PostMapping("/generate-questions")
    public List<TriviaQuestionDto> generateQuestions(@RequestBody DocumentContentRequest request) throws Exception {
        return aiQuestionService.generateTriviaQuestions(request.getContent());
    }
}
