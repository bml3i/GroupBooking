package com.example.groupbooking.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "booking_trans")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingTrans {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "file_id", nullable = false)
    private Integer fileId;
    
    @Column(name = "row_num", nullable = false)
    private Integer rowNum;
    
    @Column(name = "payload", nullable = false, columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private String payload;
    
    @Column(name = "response", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private String response;
    
    @Column(name = "retry_cnt", nullable = false)
    private Integer retryCnt = 0;
    
    // 修正：使用 CHAR 类型而不是 VARCHAR
    @Column(name = "status", nullable = false, length = 1, columnDefinition = "CHAR(1)")
    private String status;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "file_id", referencedColumnName = "file_id", insertable = false, updatable = false),
        @JoinColumn(name = "row_num", referencedColumnName = "row_num", insertable = false, updatable = false)
    })
    private BookingDetail bookingDetail;
}