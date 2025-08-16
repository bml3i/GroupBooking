package com.example.groupbooking.service;

import com.example.groupbooking.dto.BookingRequestDto;
import com.example.groupbooking.dto.BookingResponseDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;

@Service
public class BookingService {
    
    private final Random random = new Random();
    
    public BookingResponseDto processBooking(BookingRequestDto request) {
        // 验证入住日期不能是过去的日期
        if (request.getArrivialDt().isBefore(LocalDate.now())) {
            return BookingResponseDto.error("入住日期不能是过去的日期");
        }
        
        // 验证入住日期必须早于离店日期
        if (request.getArrivialDt().isAfter(request.getDepartureDt()) || 
            request.getArrivialDt().isEqual(request.getDepartureDt())) {
            return BookingResponseDto.error("入住日期必须早于离店日期");
        }
        
        // 生成随机订单号（6-10位数字）
        String orderNumber = generateOrderNumber();
        
        return BookingResponseDto.success(orderNumber);
    }
    
    private String generateOrderNumber() {
        // 生成6-10位随机数字字符串
        int length = 6 + random.nextInt(5); // 6到10位
        StringBuilder orderNumber = new StringBuilder();
        
        for (int i = 0; i < length; i++) {
            orderNumber.append(random.nextInt(10));
        }
        
        return orderNumber.toString();
    }
}