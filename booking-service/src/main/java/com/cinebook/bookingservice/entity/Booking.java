package com.cinebook.bookingservice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Correctly defined as String to hold MongoDB ObjectId
    private String userId; 
    
    private Long movieId;
    private Long seatId;
    private LocalDateTime bookingTime;

    // --- MANUAL GETTERS AND SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    // FIXED: Changed return type to String
    public String getUserId() { return userId; } 
    
    // FIXED: Changed parameter type to String
    public void setUserId(String userId) { this.userId = userId; } 

    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }

    public Long getSeatId() { return seatId; }
    public void setSeatId(Long seatId) { this.seatId = seatId; }

    public LocalDateTime getBookingTime() { return bookingTime; }
    public void setBookingTime(LocalDateTime bookingTime) { this.bookingTime = bookingTime; }
}