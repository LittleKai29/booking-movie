package com.project.spring.service;

import com.project.spring.dto.MovieDTO;
import com.project.spring.entity.Movie;
import com.project.spring.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    // Logic để tạo phim mới
    public MovieDTO createMovie(MovieDTO movieDTO) {
        Movie movie = new Movie();
        // Chuyển dữ liệu từ DTO sang Entity
        movie.setTitle(movieDTO.getTitle());
        movie.setDescription(movieDTO.getDescription());
        movie.setDirector(movieDTO.getDirector());
        movie.setDurationInMinutes(movieDTO.getDurationInMinutes());
        movie.setReleaseDate(movieDTO.getReleaseDate());
        movie.setPosterUrl(movieDTO.getPosterUrl());

        Movie savedMovie = movieRepository.save(movie);

        return convertToDto(savedMovie);
    }

    // Logic để lấy tất cả phim
    public List<MovieDTO> getAllMovies() {
        return movieRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Logic để lấy phim theo ID
    public MovieDTO getMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));
        return convertToDto(movie);
    }

    // Hàm tiện ích để chuyển từ Entity sang DTO
    private MovieDTO convertToDto(Movie movie) {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(movie.getId());
        movieDTO.setTitle(movie.getTitle());
        movieDTO.setDescription(movie.getDescription());
        movieDTO.setDirector(movie.getDirector());
        movieDTO.setDurationInMinutes(movie.getDurationInMinutes());
        movieDTO.setReleaseDate(movie.getReleaseDate());
        movieDTO.setPosterUrl(movie.getPosterUrl());
        return movieDTO;
    }

    // Anh có thể viết thêm các hàm updateMovie và deleteMovie ở đây
}
