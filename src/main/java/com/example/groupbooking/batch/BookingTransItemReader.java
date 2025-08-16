package com.example.groupbooking.batch;

import com.example.groupbooking.entity.BookingTrans;
import com.example.groupbooking.repository.BookingTransRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

@Component
public class BookingTransItemReader implements ItemReader<BookingTrans> {

    @Autowired
    private BookingTransRepository bookingTransRepository;

    private Iterator<BookingTrans> bookingTransIterator;

    @Override
    public BookingTrans read() throws Exception {
        if (bookingTransIterator == null) {
            List<BookingTrans> bookingTransList = bookingTransRepository
                    .findEligibleBookingTrans();
            bookingTransIterator = bookingTransList.iterator();
        }

        if (bookingTransIterator.hasNext()) {
            return bookingTransIterator.next();
        } else {
            // 重置迭代器以便下次读取
            bookingTransIterator = null;
            return null;
        }
    }
}