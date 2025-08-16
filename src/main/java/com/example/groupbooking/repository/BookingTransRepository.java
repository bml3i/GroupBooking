package com.example.groupbooking.repository;

import com.example.groupbooking.entity.BookingTrans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingTransRepository extends JpaRepository<BookingTrans, Long> {

    @Query("SELECT bt FROM BookingTrans bt " +
           "JOIN BookingDetail bd ON bt.fileId = bd.fileId AND bt.rowNum = bd.rowNum " +
           "WHERE bt.retryCnt < 3 " +
           "AND bt.status = 'N' " +
           "AND bd.status IN ('N', 'S')")
    List<BookingTrans> findEligibleBookingTrans();
}
