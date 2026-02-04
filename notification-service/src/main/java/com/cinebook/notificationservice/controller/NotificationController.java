package com.cinebook.notificationservice.controller;

import com.cinebook.notificationservice.util.TicketGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private TicketGenerator ticketGenerator;

    @PostMapping("/send")
    public String sendNotification(@RequestParam Long bookingId, 
                                   @RequestParam String movieName, 
                                   @RequestParam String seatNumber) {
        // In a real app, this would email the user.
        // For the demo, we generate a PDF file in the project folder.
        ticketGenerator.generateTicket(bookingId, movieName, seatNumber);
        return "Ticket Generated Successfully";
    }
}