package com.cinebook.notificationservice.dto;

public class BookingMessage {
    private Long bookingId;
    private String movieName;
    private String seatNumber;
    private String status;

    // Default Constructor
    public BookingMessage() {
    }

    // Parameterized Constructor
    public BookingMessage(Long bookingId, String movieName, String seatNumber, String status) {
        this.bookingId = bookingId;
        this.movieName = movieName;
        this.seatNumber = seatNumber;
        this.status = status;
    }

    // --- GETTERS (This fixes your error) ---
    public Long getBookingId() {
        return bookingId;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public String getStatus() {
        return status;
    }

    // --- SETTERS ---
    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // toString() for printing logs
    @Override
    public String toString() {
        return "BookingMessage [bookingId=" + bookingId + ", movieName=" + movieName + ", seatNumber=" + seatNumber + "]";
    }
}