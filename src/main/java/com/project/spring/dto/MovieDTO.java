package com.project.spring.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class MovieDTO {
    private Long id;
    private String title;
    private String description;
    private String director;
    private Integer durationInMinutes;
    private LocalDate releaseDate;
    private String posterUrl;
}
