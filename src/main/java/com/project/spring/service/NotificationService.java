package com.project.spring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @KafkaListener(topics = "booking-notifications", groupId = "booking-group")
    public void consume(String message) {
        logger.info(String.format("#### <- Consumed message <- %s ####", message));

        // Logic xử lý ở đây
        // Ví dụ: Dùng bookingId (chính là message) để truy vấn CSDL lấy thông tin
        // và gửi email xác nhận.
        // Để đơn giản, chúng ta chỉ in ra log.
        logger.info("Simulating sending confirmation email for booking ID: " + message);
    }
}
