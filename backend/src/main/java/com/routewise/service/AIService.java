package com.routewise.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.routewise.dto.GeminiResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor

public class AIService {
    private final WebClient webClient;

    private final ObjectMapper objectMapper;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    public String generateItinerary(String destination,long days){
        String prompt= """
                create a %d days travel Itinerary for %s
                
                Include:
                -Requirements
                - Day wise plan
                - Famous attractions
                - Food recommendations
                - Travel tips
                - Estimated daily activities
                
                Format nicely.
                
                """.formatted(days,destination);
        Map<String,Object> requestBody =new HashMap<>();
        requestBody.put("model","gemini-3.5-flash");
        requestBody.put("input",prompt);
        requestBody.put("stream",false);

        String response=
                webClient.post()
                        .uri(geminiApiUrl)
                        .header("x-goog-api-key",geminiApiKey)
                        .header("Content-Type", "application/json")
                        .bodyValue(requestBody)
                        .exchangeToMono(clientResponse ->
                                clientResponse.bodyToMono(String.class)
                                        .map(body -> {
                                            System.out.println("Status: " + clientResponse.statusCode());
                                            System.out.println("Body: " + body);
                                            return body;
                                        })
                        )
                        .block();

        return extractResponse(response);
    }

    private String extractResponse(String response) {
        try {

            GeminiResponse geminiResponse =
                    objectMapper.readValue(
                            response,
                            GeminiResponse.class
                    );

            if (geminiResponse.getSteps() != null) {

                for (GeminiResponse.Step step :
                        geminiResponse.getSteps()) {

                    if (step.getContent() != null
                            && !step.getContent().isEmpty()) {

                        return step.getContent()
                                .get(0)
                                .getText();
                    }
                }
            }

            return "No itinerary generated";

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to parse Gemini response"
            );
        }
    }
}




