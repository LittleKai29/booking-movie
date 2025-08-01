package com.project.spring.repository;

import com.project.spring.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Hiện tại, chúng ta chưa cần thêm phương thức tùy chỉnh nào ở đây.
    // Các phương thức như save(), findById(),... đã đủ dùng cho nghiệp vụ đặt vé.
    // Sau này nếu cần làm chức năng "Lịch sử đặt vé của người dùng",
    // chúng ta sẽ thêm phương thức findByUserId() vào đây.
}
