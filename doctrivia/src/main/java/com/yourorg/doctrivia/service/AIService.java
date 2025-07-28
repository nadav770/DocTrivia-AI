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
        String prompt = "אתה מחולל שאלות טריוויה חכם עבור מערכת לימודית. קבל קטע טקסט, וכתוב 10 שאלות טריוויה מאתגרות בעברית, בכל שאלה:\n" +
                "- כתוב את השאלה, תשובה נכונה אחת, ועוד שלוש תשובות לא נכונות אך הגיוניות (על פי החומר בקטע).\n" +
                "- וודא שהשאלות מגוונות – שאלות עובדתיות, הסקת מסקנות, השוואות או \"מה יקרה אם\".\n" +
                "- השאלות יופיעו כ־JSON במבנה הבא:\n" +
                "[\n" +
                "  {\n" +
                "    \"question\": \"כאן השאלה\",\n" +
                "    \"correctAnswer\": \"תשובה נכונה\",\n" +
                "    \"option2\": \"טעות 1\",\n" +
                "    \"option3\": \"טעות 2\",\n" +
                "    \"option4\": \"טעות 3\"\n" +
                "  },\n" +
                "  ...\n" +
                "]\n" +
                "השתמש רק בידע שמופיע בטקסט המצורף.\n" +
                "קטע הטקסט:\n" +
                "---\n" +
                "{כאן להכניס את הטקסט}\n " + text;
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
