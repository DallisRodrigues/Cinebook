package com.cinebook.bookingservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// This name MUST match the application name in notification-service.properties
@FeignClient(name = "NOTIFICATION-SERVICE")
public interface NotificationClient {

    @PostMapping("/notifications/send")
    String sendNotification(@RequestParam Long bookingId, 
                            @RequestParam String movieName, 
                            @RequestParam String seatNumber);
}