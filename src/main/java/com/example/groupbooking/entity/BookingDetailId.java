package com.example.groupbooking.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDetailId implements Serializable {
    
    private Integer fileId;
    private Integer rowNum;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingDetailId that = (BookingDetailId) o;
        return Objects.equals(fileId, that.fileId) && Objects.equals(rowNum, that.rowNum);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(fileId, rowNum);
    }
}