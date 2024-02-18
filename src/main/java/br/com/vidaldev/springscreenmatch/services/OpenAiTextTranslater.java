package br.com.vidaldev.springscreenmatch.services;

import br.com.vidaldev.springscreenmatch.enums.TextLanguage;
import br.com.vidaldev.springscreenmatch.interfaces.ITextTranslater;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OpenAiTextTranslater implements ITextTranslater {

    @Value(value = "${openai.key}")
    String openAiKey;

    @Override
    public String translate(String text, TextLanguage language) {
        OpenAiService openAiService = new OpenAiService(openAiKey);
        var prompt = "translate the text into "+language.label+": "+text;
        System.out.println(prompt);
        CompletionRequest request = CompletionRequest.builder()
                .model("babbage-002")
                .prompt(prompt)
                .maxTokens(1000)
                .temperature(0.7)
                .build();

        var response = openAiService.createCompletion(request);
        return response.getChoices().getFirst().getText();
    }
}