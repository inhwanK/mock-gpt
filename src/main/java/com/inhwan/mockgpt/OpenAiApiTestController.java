package com.inhwan.mockgpt;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class OpenAiApiTestController {

    @GetMapping("/test")
    public String test() {
        OpenAiService service = new OpenAiService("your_token");
        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt("Somebody once told me the world is gonna roll me")
                .model("ada")
                .echo(true)
                .build();

        log.info("?안되냐?");
        service.createCompletion(completionRequest).getChoices().forEach(System.out::println);
        return "되냐?";
    }
}
