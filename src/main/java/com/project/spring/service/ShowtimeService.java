package com.project.spring.service;

import com.project.spring.dto.request.ShowtimeRequestDTO;
import com.project.spring.dto.response.ShowtimeResponseDTO;
import com.project.spring.entity.Movie;
import com.project.spring.entity.Room;
import com.project.spring.entity.Showtime;
import com.project.spring.repository.MovieRepository;
import com.project.spring.repository.RoomRepository;
import com.project.spring.repository.ShowtimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowtimeService {

    @Autowired
    private ShowtimeRepository showtimeRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private RoomRepository roomRepository;

    public ShowtimeResponseDTO createShowtime(ShowtimeRequestDTO requestDTO) {
        // 1. Tìm Movie và Room từ ID
        Movie movie = movieRepository.findById(requestDTO.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        Room room = roomRepository.findById(requestDTO.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        // 2. Tạo đối tượng Showtime mới
        Showtime showtime = new Showtime();
        showtime.setMovie(movie);
        showtime.setRoom(room);
        showtime.setStartTime(requestDTO.getStartTime());
        showtime.setPricePerSeat(requestDTO.getPricePerSeat());

        // 3. Logic quan trọng: Tự động tính toán thời gian kết thúc
        showtime.setEndTime(
                requestDTO.getStartTime().plusMinutes(movie.getDurationInMinutes())
        );

        // 4. Lưu vào CSDL
        Showtime savedShowtime = showtimeRepository.save(showtime);

        // 5. Chuyển đổi sang DTO để trả về cho client
        return convertToResponseDto(savedShowtime);
    }

    public List<ShowtimeResponseDTO> getShowtimesByMovie(Long movieId) {
        List<Showtime> showtimes = showtimeRepository.findByMovieId(movieId);
        return showtimes.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    // Hàm tiện ích để chuyển đổi
    private ShowtimeResponseDTO convertToResponseDto(Showtime showtime) {
        ShowtimeResponseDTO responseDTO = new ShowtimeResponseDTO();
        responseDTO.setId(showtime.getId());
        responseDTO.setStartTime(showtime.getStartTime());
        responseDTO.setEndTime(showtime.getEndTime());
        responseDTO.setPricePerSeat(showtime.getPricePerSeat());
        responseDTO.setMovieTitle(showtime.getMovie().getTitle()); // Lấy thông tin từ quan hệ
        responseDTO.setRoomName(showtime.getRoom().getName());     // Lấy thông tin từ quan hệ
        return responseDTO;
    }
}