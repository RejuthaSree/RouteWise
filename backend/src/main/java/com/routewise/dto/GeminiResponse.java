package com.routewise.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)     //ignores properties like id,signature,status,usage
@Data
public class GeminiResponse {
    //in gemini api response the postman response has step list which has content,type..and not exposing id,signature..

    private List<Step> steps;

    public List<Step> getSteps() {
        return steps;
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class Step {

        private String type;
        private List<Content> content;

        public String getType() {
            return type;
        }


        public List<Content> getContent() {
            return content;
        }

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class Content {

        private String text;
        private String type;

        public String getText() {
            return text;
        }

        public String getType() {
            return type;
        }
    }
}