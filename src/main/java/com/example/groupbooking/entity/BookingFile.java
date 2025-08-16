package com.example.groupbooking.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "booking_file")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingFile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;
    
    @Column(name = "create_dttm", nullable = false)
    private LocalDateTime createDttm;
    
    @Column(name = "total_rows", nullable = false)
    private Integer totalRows = 0;
    
    @Column(name = "complete_rows", nullable = false)
    private Integer completeRows = 0;
    
    @OneToMany(mappedBy = "bookingFile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BookingDetail> bookingDetails;
    
    @PrePersist
    protected void onCreate() {
        if (createDttm == null) {
            createDttm = LocalDateTime.now();
        }
    }
}