package com.example.groupbooking.controller;

import com.example.groupbooking.dto.BookingRequestDto;
import com.example.groupbooking.dto.BookingResponseDto;
import com.example.groupbooking.dto.ErrorResponseDto;
import com.example.groupbooking.service.BookingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BookingController {
    
    @Autowired
    private BookingService bookingService;
    
    @PostMapping("/book")
    public ResponseEntity<?> createBooking(
            @Valid @RequestBody BookingRequestDto request,
            BindingResult bindingResult,
            HttpServletRequest httpRequest) {
        
        // 检查参数验证错误
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .findFirst()
                    .orElse("请求参数无效");
            
            ErrorResponseDto errorResponse = ErrorResponseDto.of(
                    errorMessage, 
                    HttpStatus.BAD_REQUEST.value(), 
                    httpRequest.getRequestURI()
            );
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
        
        // 处理预订逻辑
        BookingResponseDto response = bookingService.processBooking(request);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            ErrorResponseDto errorResponse = ErrorResponseDto.of(
                    response.getMessage(), 
                    HttpStatus.BAD_REQUEST.value(), 
                    httpRequest.getRequestURI()
            );
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}