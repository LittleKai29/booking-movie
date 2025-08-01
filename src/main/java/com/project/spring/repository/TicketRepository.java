package com.project.spring.repository;

import com.project.spring.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    // Tìm danh sách các vé dựa trên showtimeId và danh sách các số ghế
    List<Ticket> findByShowtimeIdAndSeatNumberIn(Long showtimeId, List<String> seatNumbers);
}
