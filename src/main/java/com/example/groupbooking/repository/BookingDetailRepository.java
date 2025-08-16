package com.example.groupbooking.repository;

import com.example.groupbooking.entity.BookingDetail;
import com.example.groupbooking.entity.BookingDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingDetailRepository extends JpaRepository<BookingDetail, BookingDetailId> {
    
    List<BookingDetail> findByFileId(Integer fileId);
    
    List<BookingDetail> findByStatus(String status);
    
    BookingDetail findByFileIdAndRowNum(Integer fileId, Integer rowNum);
}