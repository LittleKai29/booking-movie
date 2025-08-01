package com.project.spring.controller;

import com.project.spring.dto.request.BookingRequestDTO;
import com.project.spring.dto.response.BookingResponseDTO;
import com.project.spring.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // API này yêu cầu người dùng phải đăng nhập (có vai trò USER hoặc ADMIN)
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<BookingResponseDTO> createBooking(@RequestBody BookingRequestDTO requestDTO) {
        BookingResponseDTO responseDTO = bookingService.createBooking(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }
}
