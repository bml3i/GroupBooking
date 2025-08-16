package com.example.groupbooking.repository;

import com.example.groupbooking.entity.BookingTrans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingTransRepository extends JpaRepository<BookingTrans, Integer> {
    
    List<BookingTrans> findByFileIdAndRowNum(Integer fileId, Integer rowNum);
    
    List<BookingTrans> findByStatus(String status);
}