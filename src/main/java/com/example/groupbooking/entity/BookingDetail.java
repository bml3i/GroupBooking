package com.example.groupbooking.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "booking_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(BookingDetailId.class)
public class BookingDetail {
    
    @Id
    @Column(name = "file_id")
    private Integer fileId;
    
    @Id
    @Column(name = "row_num")
    private Integer rowNum;
    
    @Column(name = "hotel_code", nullable = false, length = 5)
    private String hotelCode;
    
    @Column(name = "arrivial_dt", nullable = false)
    private LocalDate arrivialDt;
    
    @Column(name = "departure_dt", nullable = false)
    private LocalDate departureDt;
    
    @Column(name = "room_type", nullable = false, length = 4)
    private String roomType;
    
    @Column(name = "guest_name", nullable = false, length = 100)
    private String guestName;
    
    @Column(name = "status", nullable = false, length = 1)
    private String status;
    
    @Column(name = "error_msg", length = 500)
    private String errorMsg;
    
    @Column(name = "cnf_num", length = 50)
    private String cnfNum;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", insertable = false, updatable = false)
    private BookingFile bookingFile;
    
    @OneToMany(mappedBy = "bookingDetail", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BookingTrans> bookingTrans;
}