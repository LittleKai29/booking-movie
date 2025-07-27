package com.project.spring.repository;

import com.project.spring.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    // Spring Data JPA sẽ tự động cung cấp các phương thức CRUD cơ bản
    // như save(), findById(), findAll(), deleteById()...
    // Tạm thời chúng ta chưa cần viết thêm phương thức nào ở đây.
}
