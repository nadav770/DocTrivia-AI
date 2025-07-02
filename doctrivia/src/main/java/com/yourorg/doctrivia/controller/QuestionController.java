package com.yourorg.doctrivia.controller;

import com.yourorg.doctrivia.model.Question;
import com.yourorg.doctrivia.repository.QuestionRepository;
import com.yourorg.doctrivia.repository.DocumentRepository;
import com.yourorg.doctrivia.service.QuestionGenerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionRepository questionRepository;
    private final DocumentRepository documentRepository;
    private final QuestionGenerationService questionGenerationService;

    @GetMapping
    public List<Question> getAll() {
        return questionRepository.findAll();
    }

    @GetMapping("/document/{documentId}")
    public List<Question> getByDocumentId(@PathVariable Long documentId) {
        return questionRepository.findByDocumentId(documentId);
    }

    @PostMapping
    public Question create(@RequestBody Question q) {
        q.setId(null); // מוודאים שלא מזינים id
        return questionRepository.save(q);
    }

    @GetMapping("/{id}")
    public Question getById(@PathVariable Long id) {
        return questionRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        questionRepository.deleteById(id);
    }
    @PostMapping("/generate/{documentId}")
    public List<Question> generateQuestionsForDocument(@PathVariable Long documentId) {
        var document = documentRepository.findById(documentId)
                .orElseThrow(() -> new IllegalArgumentException("Document not found"));

        List<Question> questions = questionGenerationService.generateQuestions(document.getContent());
        // שמירת כל שאלה ב-DB, קשירה ל-documentId
        questions.forEach(q -> q.setDocumentId(documentId));
        return questionRepository.saveAll(questions);
}
    }
