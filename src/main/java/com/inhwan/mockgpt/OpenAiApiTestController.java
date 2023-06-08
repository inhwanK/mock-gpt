package com.inhwan.mockgpt;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class OpenAiApiTestController {

    //    private final OpenAiService openAiService;
    private static String apiKey;

    @Value("${secret.key}")
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @GetMapping("/completion")
    public CompletionResult testCompletion() {
        OpenAiService service = new OpenAiService(apiKey);

        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt("사람을 짜증나게하는 방법 두가지 중 첫번째는 말을 끝까지 하지 않는 것이고, 두번째는 ")
                .model("ada")
                .echo(true)
                .build();

//        service.createCompletion(completionRequest).getChoices().forEach(System.out::println);
        return service.createCompletion(completionRequest);
    }

    @GetMapping("/chat/completion")
    public ChatCompletionResult testChatCompletion() {
        OpenAiService service = new OpenAiService(apiKey);

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo-0301")
                .temperature(0.8)
                .n(10)
                .messages(List.of(new ChatMessage("user", "Create 10 sentences to use as post dummy data in Korean")))
                .build();

//        service.createCompletion(completionRequest).getChoices().forEach(System.out::println);
        return service.createChatCompletion(chatCompletionRequest);
    }
}
