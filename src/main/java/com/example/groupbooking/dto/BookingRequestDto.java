package com.example.groupbooking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingRequestDto {
    
    @NotBlank(message = "房间类型不能为空")
    @JsonProperty("room_type")
    private String roomType;
    
    @NotBlank(message = "客人姓名不能为空")
    @JsonProperty("guest_name")
    private String guestName;
    
    @NotBlank(message = "酒店代码不能为空")
    @JsonProperty("hotel_code")
    private String hotelCode;
    
    @NotNull(message = "入住日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("arrivial_dt")
    private LocalDate arrivialDt;
    
    @NotNull(message = "离店日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("departure_dt")
    private LocalDate departureDt;
}