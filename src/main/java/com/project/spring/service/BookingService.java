package com.project.spring.service;

import com.project.spring.dto.request.BookingRequestDTO;
import com.project.spring.dto.response.BookingResponseDTO;
import com.project.spring.entity.*;
import com.project.spring.kafka.KafkaProducerService;
import com.project.spring.repository.BookingRepository;
import com.project.spring.repository.ShowtimeRepository;
import com.project.spring.repository.TicketRepository;
import com.project.spring.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final Logger logger = LoggerFactory.getLogger(BookingService.class);
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ShowtimeRepository showtimeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Transactional // Rất quan trọng: Đảm bảo tất cả các thao tác CSDL trong hàm này là một giao dịch
    public BookingResponseDTO createBooking(BookingRequestDTO requestDTO) {
        // 1. Lấy thông tin người dùng đang đăng nhập từ Spring Security Context
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Lấy thông tin suất chiếu
        Showtime showtime = showtimeRepository.findById(requestDTO.getShowtimeId())
                .orElseThrow(() -> new RuntimeException("Showtime not found"));

        // 3. KIỂM TRA LOGIC QUAN TRỌNG: Ghế đã được đặt chưa?
        List<Ticket> existingTickets = ticketRepository.findByShowtimeIdAndSeatNumberIn(
                showtime.getId(), requestDTO.getSeatNumbers()
        );
        if (!existingTickets.isEmpty()) {
            // Nếu danh sách không rỗng, tức là có ít nhất 1 ghế đã được đặt
            String bookedSeats = existingTickets.stream()
                    .map(Ticket::getSeatNumber)
                    .collect(Collectors.joining(", "));
            throw new RuntimeException("Seats are already booked: " + bookedSeats);
        }

        // 4. Tạo đơn đặt vé (Booking)
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShowtime(showtime);
        booking.setBookingTime(LocalDateTime.now());
        booking.setStatus(BookingStatus.CONFIRMED);

        // 5. Tính tổng tiền
        BigDecimal totalAmount = showtime.getPricePerSeat()
                .multiply(new BigDecimal(requestDTO.getSeatNumbers().size()));
        booking.setTotalAmount(totalAmount);

        // 6. Tạo các vé (Tickets) chi tiết
        Set<Ticket> tickets = new HashSet<>();
        for (String seatNumber : requestDTO.getSeatNumbers()) {
            System.out.println(seatNumber);
            Ticket ticket = new Ticket();
//            ticket.setBooking(booking);
            ticket.setShowtime(showtime);
            ticket.setSeatNumber(seatNumber);
            ticket.setPrice(showtime.getPricePerSeat());

            booking.addTicket(ticket);
        }
//        booking.setTickets(tickets);

        // 7. Lưu đơn đặt vé (do có cascade, các vé con cũng sẽ được lưu theo)
        Booking savedBooking = bookingRepository.save(booking);

        // 8. GỬI MESSAGE TỚI KAFKA (Bước mới)
        // Chúng ta sẽ gửi ID của booking vừa được lưu
        try {
            kafkaProducerService.sendMessage(savedBooking.getId().toString());
        } catch (Exception e) {
            // Ghi log nếu gửi message thất bại, nhưng không làm ảnh hưởng đến giao dịch chính
            // Người dùng vẫn nhận được kết quả đặt vé thành công
            logger.error("Failed to send booking notification to Kafka", e);
        }

        // 9. Chuyển đổi sang DTO để trả về
        return convertToResponseDto(savedBooking);
    }

    private BookingResponseDTO convertToResponseDto(Booking booking) {
        BookingResponseDTO dto = new BookingResponseDTO();
        dto.setBookingId(booking.getId());
        dto.setMovieTitle(booking.getShowtime().getMovie().getTitle());
        dto.setRoomName(booking.getShowtime().getRoom().getName());
        dto.setStartTime(booking.getShowtime().getStartTime());
        dto.setTotalAmount(booking.getTotalAmount());
        dto.setBookingTime(booking.getBookingTime());
        dto.setStatus(booking.getStatus().name());
        dto.setSeatNumbers(
                booking.getTickets().stream()
                        .map(Ticket::getSeatNumber)
                        .collect(Collectors.toList())
        );
        return dto;
    }
}
