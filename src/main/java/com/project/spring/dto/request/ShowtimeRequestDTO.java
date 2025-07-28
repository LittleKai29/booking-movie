package com.project.spring.dto.request;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ShowtimeRequestDTO {
    private Long movieId;
    private Integer roomId;
    private LocalDateTime startTime;
    private BigDecimal pricePerSeat;
}