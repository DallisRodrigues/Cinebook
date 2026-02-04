package com.cinebook.bookingservice.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class BookingCleanupJob {

    // Runs every 30 seconds
    @Scheduled(fixedRate = 30000)
    public void cleanupOldBookings() {
        // In a real app, you would fetch bookings older than 10 mins and status = 'PENDING'
        // For the demo, we just print a log to prove the robot is alive.
        System.out.println("🤖 [SCHEDULER] Maintenance Robot: Checking for expired bookings at " + LocalDateTime.now());
    }
}