package com.project.spring.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingResponseDTO {
    private Long bookingId;
    private String movieTitle;
    private String roomName;
    private LocalDateTime startTime;
    private List<String> seatNumbers;
    private BigDecimal totalAmount;
    private LocalDateTime bookingTime;
    private String status;
}
