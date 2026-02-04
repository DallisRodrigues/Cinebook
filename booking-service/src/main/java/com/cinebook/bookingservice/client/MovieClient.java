package com.cinebook.bookingservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

// "MOVIE-SERVICE" must match the spring.application.name in movie-service.properties
@FeignClient(name = "MOVIE-SERVICE")
public interface MovieClient {

    // We need to add this endpoint to Movie Service later!
    @PutMapping("/movies/seats/{seatId}/book")
    boolean bookSeat(@PathVariable("seatId") Long seatId);
}