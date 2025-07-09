package com.yourorg.doctrivia.service;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AIService {

    private final OpenAiService openAiService;

    public AIService(@Value("${openai.api.key}") String apiKey) {
        this.openAiService = new OpenAiService(apiKey);
    }

    // דיפולט
    public String generateTrivia(String text) {
        String prompt = "Generate 5 trivia questions in JSON format with question, correctAnswer, option2, option3, option4 based on this text: " + text;
        return generateTriviaWithPrompt(text, prompt);
    }

    // עם prompt דינאמי
    public String generateTriviaWithPrompt(String text, String prompt) {
        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(List.of(new ChatMessage("user", prompt)))
                .temperature(0.5)
                .build();

        return openAiService.createChatCompletion(request)
                .getChoices().get(0).getMessage().getContent();

    }

}
