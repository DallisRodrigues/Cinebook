package com.cinebook.bookingservice.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cinebook.bookingservice.client.MovieClient;
import com.cinebook.bookingservice.config.RabbitMQConfig;
import com.cinebook.bookingservice.dto.BookingMessage;
import com.cinebook.bookingservice.entity.Booking;
import com.cinebook.bookingservice.repository.BookingRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private MovieClient movieClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // --- NEW ENDPOINT FOR TICKETS DASHBOARD ---
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getUserBookings(@PathVariable String userId) {
        // userId is now a String to match MongoDB ObjectId
        List<Booking> userBookings = bookingRepo.findByUserId(userId);
        return ResponseEntity.ok(userBookings);
    }

    @GetMapping("/create")
    @CircuitBreaker(name = "movieService", fallbackMethod = "bookingFallback")
    public String createBooking(
            @RequestParam String userId, // Changed to String for MongoDB ID
            @RequestParam Long seatId, 
            @RequestParam Long movieId,
            @RequestParam String email,
            @RequestParam String movieTitle) {
        
        System.out.println("DEBUG: Processing Booking for: " + movieTitle + " (User: " + userId + ")");

        boolean isEvent = (movieId == 0);
        boolean success = false;

        if (isEvent) {
            success = true; 
        } else {
            // Note: If your MovieService still uses Long for userId internally, 
            // you may need to handle the conversion or update MovieClient.
            success = movieClient.bookSeat(seatId, 1L); // Using dummy Long 1L if movie service isn't Mongo-ready yet
        }

        if (success) {
            Booking b = new Booking();
            b.setUserId(userId); // Saving the MongoDB String ID to MariaDB
            b.setSeatId(seatId);
            b.setMovieId(movieId);
            b.setBookingTime(LocalDateTime.now());
            // Optionally set movieTitle if your entity has that field
            bookingRepo.save(b);

            try {
                String seatInfo = isEvent ? "General Admission" : "Seat ID: " + seatId;

                BookingMessage message = new BookingMessage(
                    b.getId(), 
                    movieTitle, 
                    seatInfo, 
                    "CONFIRMED", 
                    email
                );
                
                rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, message);
                return "Success";
            } catch (Exception e) {
                e.printStackTrace();
                return "Booking Saved, Notification Failed.";
            }
        }
        return "Failed";
    }

    // Fallback updated to match the String userId parameter
    public String bookingFallback(String userId, Long seatId, Long movieId, String email, String movieTitle, Throwable t) {
        System.err.println("Circuit Breaker Triggered: " + t.getMessage());
        return "⚠️ Service Unavailable. Please try again later.";
    }
}