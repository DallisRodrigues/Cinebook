package com.cinebook.notificationservice.consumer;

import com.cinebook.notificationservice.dto.BookingMessage;
import com.cinebook.notificationservice.util.TicketGenerator;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    @Autowired
    private TicketGenerator ticketGenerator;

    @RabbitListener(queues = "notification_queue") // Must match the queue name in Booking Config
    public void consumeMessageFromQueue(BookingMessage message) {
        System.out.println("🐇 Received Message from Queue: " + message);
        
        // Trigger the actual PDF generation
        ticketGenerator.generateTicket(message.getBookingId(), message.getMovieName(), message.getSeatNumber());
    }
}