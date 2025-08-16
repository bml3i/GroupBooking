package com.example.groupbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDto {
    
    private String message;
    private int status;
    private LocalDateTime timestamp;
    private String path;
    
    public static ErrorResponseDto of(String message, int status, String path) {
        return new ErrorResponseDto(message, status, LocalDateTime.now(), path);
    }
}