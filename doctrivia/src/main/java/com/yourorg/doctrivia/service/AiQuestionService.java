package com.yourorg.doctrivia.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yourorg.doctrivia.dto.TriviaQuestionDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class AiQuestionService {

    @Value("${openai.api.key}")
    private String openaiApiKey;

    private static final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";

    public List<TriviaQuestionDto> generateTriviaQuestions(String documentContent) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(openaiApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // ----------- בונים אובייקט כמו שצריך ולא String ! ----------------
        Map<String, Object> payload = new HashMap<>();
        payload.put("model", "gpt-3.5-turbo");

        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "system", "content", "You are a trivia generator."));
        String prompt = "Based on the following document, generate 5 trivia questions. Each question should have 4 answer options (a-d) and mark the correct one. Respond strictly in valid JSON array like this: [ {\"question\": \"...\", \"options\": [\"...\",\"...\",\"...\",\"...\"], \"correctIndex\": 2 }, ... ]. The document:\n" + documentContent;
        messages.add(Map.of("role", "user", "content", prompt));
        payload.put("messages", messages);
        payload.put("max_tokens", 800);

        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(payload);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                OPENAI_URL, HttpMethod.POST, entity, String.class
        );

        // נשלוף את התשובה מתוך ה-choices
        JsonNode root = mapper.readTree(response.getBody());
        String content = root.path("choices").get(0).path("message").path("content").asText();

        // מפענחים את התשובה ל-List<TriviaQuestionDto>
        List<TriviaQuestionDto> questions = new ArrayList<>();
        try {
            JsonNode questionsNode = mapper.readTree(content);
            if (questionsNode.isArray()) {
                for (JsonNode node : questionsNode) {
                    TriviaQuestionDto q = new TriviaQuestionDto();
                    q.setQuestion(node.get("question").asText());
                    List<String> opts = new ArrayList<>();
                    node.get("options").forEach(opt -> opts.add(opt.asText()));
                    q.setOptions(opts);
                    q.setCorrectIndex(node.get("correctIndex").asInt());
                    questions.add(q);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("AI returned malformed JSON! Content: " + content, e);
        }

        return questions;
    }
}
