package com.example.groupbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDto {
    
    private String message;
    private String orderNumber;
    private boolean success;
    
    public static BookingResponseDto success(String orderNumber) {
        return new BookingResponseDto("预订成功", orderNumber, true);
    }
    
    public static BookingResponseDto error(String message) {
        return new BookingResponseDto(message, null, false);
    }
}