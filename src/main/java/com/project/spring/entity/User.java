package com.project.spring.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    private String fullName;

    @Enumerated(EnumType.STRING) // Lưu Enum dưới dạng String trong DB
    @Column(nullable = false)
    private Role role;

    // Một User có nhiều Bookings
    // mappedBy="user" chỉ ra rằng quan hệ này được quản lý bởi trường "user" trong class Booking
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Booking> bookings;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
