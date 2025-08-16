package com.example.groupbooking.batch;

import com.example.groupbooking.entity.BookingDetail;
import com.example.groupbooking.entity.BookingTrans;
import com.example.groupbooking.repository.BookingDetailRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class BookingTransItemProcessor implements ItemProcessor<BookingTrans, BookingTrans> {

    @Autowired
    private BookingDetailRepository bookingDetailRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String BOOKING_API_URL = "http://localhost:8080/api/book";

    @Override
    public BookingTrans process(BookingTrans bookingTrans) throws Exception {
        BookingDetail bookingDetail = bookingDetailRepository
                .findByFileIdAndRowNum(bookingTrans.getFileId(), bookingTrans.getRowNum());

        if (bookingDetail == null) {
            return null; // 跳过处理
        }

        try {
            // 发送POST请求
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<>(bookingTrans.getPayload(), headers);

            ResponseEntity<String> response = restTemplate.postForEntity(BOOKING_API_URL, request, String.class);
            
            // 保存响应
            bookingTrans.setResponse(response.getBody());

            if (response.getStatusCode() == HttpStatus.OK) {
                JsonNode responseJson = objectMapper.readTree(response.getBody());
                boolean success = responseJson.get("success").asBoolean();

                if (success) {
                    // 预定成功
                    String orderNumber = responseJson.get("orderNumber").asText();
                    bookingDetail.setStatus("C");
                    bookingDetail.setCnfNum(orderNumber);
                    bookingTrans.setStatus("C");
                } else {
                    // 预定失败
                    String message = responseJson.get("message").asText();
                    bookingDetail.setStatus("F");
                    bookingDetail.setErrorMsg(message);
                    bookingTrans.setStatus("F");
                }
            }

        } catch (HttpClientErrorException e) {
            // HTTP 4xx 错误
            bookingTrans.setResponse(e.getResponseBodyAsString());
            
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                try {
                    JsonNode errorJson = objectMapper.readTree(e.getResponseBodyAsString());
                    String message = errorJson.get("message").asText();
                    bookingDetail.setStatus("F");
                    bookingDetail.setErrorMsg(message);
                    bookingTrans.setStatus("F");
                } catch (Exception ex) {
                    bookingDetail.setStatus("F");
                    bookingDetail.setErrorMsg("Bad Request");
                    bookingTrans.setStatus("F");
                }
            }

        } catch (HttpServerErrorException e) {
            // HTTP 5xx 错误 - 增加重试次数
            bookingTrans.setResponse(e.getResponseBodyAsString());
            bookingTrans.setRetryCnt(bookingTrans.getRetryCnt() + 1);

            if (bookingTrans.getRetryCnt() >= 3) {
                // 达到最大重试次数
                bookingDetail.setStatus("F");
                bookingDetail.setErrorMsg("Internal Server Error");
                bookingTrans.setStatus("F");
            }
            // 如果未达到最大重试次数，状态保持为"N"，下次会重新处理

        } catch (Exception e) {
            // 其他异常
            bookingTrans.setResponse(e.getMessage());
            bookingTrans.setRetryCnt(bookingTrans.getRetryCnt() + 1);

            if (bookingTrans.getRetryCnt() >= 3) {
                bookingDetail.setStatus("F");
                bookingDetail.setErrorMsg("Internal Server Error");
                bookingTrans.setStatus("F");
            }
        }

        return bookingTrans;
    }
}