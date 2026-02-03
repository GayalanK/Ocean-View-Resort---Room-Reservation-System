package com.oceanview.resort.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;
    private String reservationNumber;
    private Guest guest;
    private Room room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int numberOfNights;
    private double totalAmount;
    private String status;
    private LocalDate reservationDate;
    
    public Reservation() {
        this.status = "PENDING";
        this.reservationDate = LocalDate.now();
    }
    
    public Reservation(String reservationNumber, Guest guest, Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        this();
        this.reservationNumber = reservationNumber;
        this.guest = guest;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        calculateNights();
        calculateTotalAmount();
    }
    
    public void calculateNights() {
        if (checkInDate != null && checkOutDate != null) {
            numberOfNights = (int) java.time.temporal.ChronoUnit.DAYS.between(checkInDate, checkOutDate);
            if (numberOfNights <= 0) numberOfNights = 1;
        }
    }
    
    public void calculateTotalAmount() {
        if (room != null && numberOfNights > 0) {
            totalAmount = room.getRate() * numberOfNights;
        }
    }
    
    public String getReservationNumber() { return reservationNumber; }
    public void setReservationNumber(String reservationNumber) { this.reservationNumber = reservationNumber; }
    public Guest getGuest() { return guest; }
    public void setGuest(Guest guest) { this.guest = guest; }
    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; calculateTotalAmount(); }
    public LocalDate getCheckInDate() { return checkInDate; }
    public void setCheckInDate(LocalDate checkInDate) { this.checkInDate = checkInDate; calculateNights(); calculateTotalAmount(); }
    public LocalDate getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(LocalDate checkOutDate) { this.checkOutDate = checkOutDate; calculateNights(); calculateTotalAmount(); }
    public int getNumberOfNights() { return numberOfNights; }
    public void setNumberOfNights(int numberOfNights) { this.numberOfNights = numberOfNights; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDate getReservationDate() { return reservationDate; }
    public void setReservationDate(LocalDate reservationDate) { this.reservationDate = reservationDate; }
}
