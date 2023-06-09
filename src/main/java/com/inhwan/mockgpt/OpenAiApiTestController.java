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

import java.time.Duration;
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

    // timeout 문제 - https://community.openai.com/t/http-timeout-error-at-the-maximum-token-limit/211767
    // https://github.com/TheoKanning/openai-java/issues/241
    @GetMapping("/chat/completion")
    public ChatCompletionResult testChatCompletion() {
        OpenAiService service = new OpenAiService(apiKey, Duration.ofMinutes(3));

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo-0301")
                .temperature(0.8)
                .n(1)
                .messages(List.of(new ChatMessage("user", "더미 데이터로 사용할 게시글 내용을 100개 생성해줘")))
                .build();

        service.createChatCompletion(chatCompletionRequest).getChoices().forEach(System.out::println);
        return service.createChatCompletion(chatCompletionRequest);
    }
}
