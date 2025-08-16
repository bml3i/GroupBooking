package com.example.groupbooking.batch;

import com.example.groupbooking.entity.BookingDetail;
import com.example.groupbooking.entity.BookingTrans;
import com.example.groupbooking.repository.BookingDetailRepository;
import com.example.groupbooking.repository.BookingTransRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingTransItemWriter implements ItemWriter<BookingTrans> {

    @Autowired
    private BookingTransRepository bookingTransRepository;

    @Autowired
    private BookingDetailRepository bookingDetailRepository;

    @Override
    public void write(Chunk<? extends BookingTrans> chunk) throws Exception {
        for (BookingTrans bookingTrans : chunk) {
            // 保存BookingTrans
            bookingTransRepository.save(bookingTrans);

            // 保存关联的BookingDetail
            BookingDetail bookingDetail = bookingDetailRepository
                    .findByFileIdAndRowNum(bookingTrans.getFileId(), bookingTrans.getRowNum());
            if (bookingDetail != null) {
                bookingDetailRepository.save(bookingDetail);
            }
        }
    }
}