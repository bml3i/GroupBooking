package com.example.groupbooking.repository;

import com.example.groupbooking.entity.BookingFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingFileRepository extends JpaRepository<BookingFile, Integer> {
    
    Optional<BookingFile> findByFileName(String fileName);
}