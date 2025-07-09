package com.yourorg.doctrivia.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yourorg.doctrivia.model.document;
import com.yourorg.doctrivia.model.Question;
import com.yourorg.doctrivia.repository.DocumentRepository;
import com.yourorg.doctrivia.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final AIService aiService;
    private final QuestionRepository questionRepository;
    private final DocumentRepository documentRepository;
    private final ObjectMapper mapper = new ObjectMapper();

    public List<Question> generateQuestionsFromDocument(Long documentId) {
        // 1. שלוף את כל הטקסט של ה-PDF מתוך document
        document doc = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        String pdfText = doc.getContent();

        // 2. בנה prompt מדויק לטריוויה אמיתית בלבד
        String prompt =
                "Generate up to 6 trivia questions in Hebrew based only on the facts that appear in the following text. " +
                        "Do not invent or guess any information. " +
                        "If there is not enough information, generate fewer questions or reply with an empty JSON array. " +
                        "Return only a valid JSON array with the following fields: question, correctAnswer, option2, option3, option4. " +
                        "Here is the text: " + pdfText;

        String aiResponse;
        try {
            aiResponse = aiService.generateTriviaWithPrompt(pdfText, prompt);
        } catch (Exception e) {
            System.out.println("=== AI EXCEPTION ===");
            e.printStackTrace();
            throw new RuntimeException("AI Service failed: " + e.getMessage());
        }

        // 3. הדפס ללוג את תשובת ה-AI
        System.out.println("=== AI RESPONSE START ===");
        System.out.println(aiResponse);
        System.out.println("=== AI RESPONSE END ===");

        // 4. מצא את ה-JSON האמיתי בתשובה (ממזער תקלות פרסור)
        String jsonArray;
        int start = aiResponse.indexOf('[');
        int end = aiResponse.lastIndexOf(']');
        if (start >= 0 && end > start) {
            jsonArray = aiResponse.substring(start, end + 1);
        } else {
            throw new RuntimeException("No JSON array found in AI response");
        }

        // 5. פרסר את ה-JSON, בנה ושמור שאלות ל-DB
        try {
            List<Question> questions = new ArrayList<>();
            JsonNode root = mapper.readTree(jsonArray);

            for (JsonNode qNode : root) {
                Question q = Question.builder()
                        .document(doc)
                        .question(qNode.get("question").asText())
                        .correctAnswer(qNode.get("correctAnswer").asText())
                        .option2(qNode.get("option2").asText())
                        .option3(qNode.get("option3").asText())
                        .option4(qNode.get("option4").asText())
                        .build();
                questions.add(questionRepository.save(q));
            }
            return questions;
        } catch (Exception e) {
            System.out.println("=== JSON PARSE ERROR ===");
            e.printStackTrace();
            throw new RuntimeException("Failed parsing trivia JSON", e);
        }

    }

    // שליפת כל השאלות
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    //  שליפת שאלה בודדת
    public Question getQuestionById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));
    }

    //  שליפת שאלות לפי מזהה מסמך
    public List<Question> getQuestionsByDocument(Long documentId) {
        document doc = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        return questionRepository.findByDocument(doc);
    }

    // ==================== מחיקת שאלה ====================
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }
}
