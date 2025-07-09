package com.yourorg.doctrivia.controller;

import com.yourorg.doctrivia.model.Question;
import com.yourorg.doctrivia.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    // יצירת שאלות (כמו שהיה)
    @PostMapping("/generate/{documentId}")
    public ResponseEntity<?> generateTrivia(@PathVariable Long documentId) {
        try {
            List<Question> questions = questionService.generateQuestionsFromDocument(documentId);
            return ResponseEntity.ok(questions);
        } catch (RuntimeException ex) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: " + ex.getMessage() +
                            " — בדוק בקונסול את תשובת ה-AI (רמז: כנראה בעיית פורמט או prompt לא מספיק מדויק)");
        } catch (Exception ex) {
            return ResponseEntity
                    .status(500)
                    .body("Internal server error: " + ex.getMessage());
        }
    }

    // שליפת כל השאלות
    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    // שליפת שאלה בודדת לפי id
    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id) {
        Question question = questionService.getQuestionById(id);
        return ResponseEntity.ok(question);
    }

    // שליפת שאלות לפי מזהה מסמך
    @GetMapping("/document/{documentId}")
    public ResponseEntity<List<Question>> getQuestionsByDocument(@PathVariable Long documentId) {
        List<Question> questions = questionService.getQuestionsByDocument(documentId);
        return ResponseEntity.ok(questions);
    }

    // מחיקת שאלה
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}
