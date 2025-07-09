package com.yourorg.doctrivia.controller;

import com.yourorg.doctrivia.dto.AnswerRequest;
import com.yourorg.doctrivia.model.Answers;
import com.yourorg.doctrivia.model.User;
import com.yourorg.doctrivia.model.Question;
import com.yourorg.doctrivia.model.document;
import com.yourorg.doctrivia.repository.AnswerRepository;
import com.yourorg.doctrivia.repository.UserRepository;
import com.yourorg.doctrivia.repository.QuestionRepository;
import com.yourorg.doctrivia.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/answers")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final DocumentRepository documentRepository;

    // 1. הגשת תשובה (POST)
    @PostMapping
    public ResponseEntity<Answers> submitAnswer(@RequestBody AnswerRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Question question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found"));
        document doc = documentRepository.findById(request.getDocumentId())
                .orElseThrow(() -> new RuntimeException("Document not found"));

        boolean isCorrect = question.getCorrectAnswer().equals(request.getSelectedAnswer());

        Answers answer = Answers.builder()
                .user(user)
                .question(question)
                .document(doc)
                .selectedAnswer(request.getSelectedAnswer())
                .isCorrect(isCorrect)
                .build();

        Answers saved = answerRepository.save(answer);
        return ResponseEntity.ok(saved);
    }

    // 2. שליפת כל התשובות של משתמש (GET)
    @GetMapping("/app_user/{userId}")
    public ResponseEntity<List<Answers>> getUserAnswers(@PathVariable Long userId) {
        List<Answers> answers = answerRepository.findByUserId(userId);
        return ResponseEntity.ok(answers);
    }

    // 3. (רשות) שליפת כל התשובות של משתמש עבור מסמך
    @GetMapping("/app_user/{userId}/document/{docId}")
    public ResponseEntity<List<Answers>> getUserAnswersByDocument(
            @PathVariable Long userId, @PathVariable Long docId) {
        List<Answers> answers = answerRepository.findByUserIdAndDocumentId(userId, docId);
        return ResponseEntity.ok(answers);
    }
}
