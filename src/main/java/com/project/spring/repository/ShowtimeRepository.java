package com.project.spring.repository;

import com.project.spring.entity.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {

    // Spring Data JPA sẽ tự động tạo câu lệnh query dựa trên tên phương thức
    // Tìm tất cả các Showtime có trường movie với id khớp với movieId được truyền vào
    List<Showtime> findByMovieId(Long movieId);
}
