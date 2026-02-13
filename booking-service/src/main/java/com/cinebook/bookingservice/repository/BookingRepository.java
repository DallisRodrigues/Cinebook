package com.cinebook.bookingservice.repository;

import com.cinebook.bookingservice.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Custom query to find history by MongoDB String ID
    List<Booking> findByUserId(String userId);
}