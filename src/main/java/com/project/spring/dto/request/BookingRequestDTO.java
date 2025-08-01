package com.project.spring.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class BookingRequestDTO {
    private Long showtimeId;
    private List<String> seatNumbers; // Danh sách các số ghế muốn đặt, ví dụ: ["A1", "A2"]
}
