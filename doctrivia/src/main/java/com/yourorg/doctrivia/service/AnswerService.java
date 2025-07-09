package com.yourorg.doctrivia.service;

import com.yourorg.doctrivia.model.Answers;
import com.yourorg.doctrivia.model.Question;
import com.yourorg.doctrivia.model.User;
import com.yourorg.doctrivia.model.document;
import com.yourorg.doctrivia.repository.AnswerRepository;
import com.yourorg.doctrivia.repository.DocumentRepository;
import com.yourorg.doctrivia.repository.QuestionRepository;
import com.yourorg.doctrivia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;
    private final QuestionRepository questionRepository;

    public Answers saveAnswer(Long userId, Long documentId, Long questionId, String selectedAnswer) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        boolean isCorrect = selectedAnswer.equals(question.getCorrectAnswer());

        Answers answer = Answers.builder()
                .user(user)
                .document(document)
                .question(question)
                .selectedAnswer(selectedAnswer)
                .isCorrect(isCorrect)
                .answeredAt(LocalDateTime.now())
                .build();

        return answerRepository.save(answer);
    }
}
