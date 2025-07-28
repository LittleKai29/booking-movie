package com.project.spring.controller;

import com.project.spring.dto.request.ShowtimeRequestDTO;
import com.project.spring.dto.response.ShowtimeResponseDTO;
import com.project.spring.service.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ShowtimeController {

    @Autowired
    private ShowtimeService showtimeService;

    // API để tạo một suất chiếu mới
    @PostMapping("/showtimes")
    public ResponseEntity<ShowtimeResponseDTO> createShowtime(@RequestBody ShowtimeRequestDTO requestDTO) {
        ShowtimeResponseDTO createdShowtime = showtimeService.createShowtime(requestDTO);
        return ResponseEntity.ok(createdShowtime);
    }

    // API để lấy tất cả suất chiếu của một phim cụ thể
    @GetMapping("/movies/{movieId}/showtimes")
    public ResponseEntity<List<ShowtimeResponseDTO>> getShowtimesByMovie(@PathVariable Long movieId) {
        List<ShowtimeResponseDTO> showtimes = showtimeService.getShowtimesByMovie(movieId);
        return ResponseEntity.ok(showtimes);
    }
}