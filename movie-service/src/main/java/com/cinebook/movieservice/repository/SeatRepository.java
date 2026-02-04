package com.cinebook.movieservice.repository;

import com.cinebook.movieservice.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByMovieId(Long movieId);
    
    // Add this new method:
    long countByMovieId(Long movieId);
}