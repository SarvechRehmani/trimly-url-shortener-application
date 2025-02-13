package com.trimly.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class LinkResponseDto {
    private String status;
    private String message;
    private String shortUrl;
    private Map<String, String> errors;  // Hold field-specific errors

    public LinkResponseDto(String status, String message, Map<String, String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public LinkResponseDto(String status, String message, String shortUrl) {
        this.status = status;
        this.message = message;
        this.shortUrl = shortUrl;
    }
}
