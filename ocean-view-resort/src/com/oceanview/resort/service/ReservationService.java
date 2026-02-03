package com.oceanview.resort.service;

import com.oceanview.resort.dao.ReservationDAO;
import com.oceanview.resort.dao.RoomDAO;
import com.oceanview.resort.factory.ReservationFactory;
import com.oceanview.resort.model.Guest;
import com.oceanview.resort.model.Reservation;
import com.oceanview.resort.model.Room;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Reservation Service - Business logic for reservation operations
 */
public class ReservationService {
    private ReservationDAO reservationDAO = new ReservationDAO();
    private RoomDAO roomDAO = new RoomDAO();
    private PricingStrategy pricingStrategy = new DiscountPricingStrategy();
    
    public String createReservation(Guest guest, String roomNumber, String checkInStr, String checkOutStr) 
            throws Exception {
        LocalDate checkIn = LocalDate.parse(checkInStr);
        LocalDate checkOut = LocalDate.parse(checkOutStr);
        
        if (checkOut.isBefore(checkIn) || checkOut.isEqual(checkIn)) {
            throw new IllegalArgumentException("Check-out must be after check-in");
        }
        if (checkIn.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Check-in cannot be in the past");
        }
        
        Room room = roomDAO.findByRoomNumber(roomNumber);
        if (room == null || !room.isAvailable()) {
            throw new IllegalArgumentException("Room not available");
        }
        
        if (hasConflict(roomNumber, checkIn, checkOut)) {
            throw new IllegalArgumentException("Room already booked for these dates");
        }
        
        Reservation reservation = ReservationFactory.createReservation(guest, room, checkIn, checkOut);
        reservation.setStatus("CONFIRMED");
        reservation.setTotalAmount(pricingStrategy.calculatePrice(reservation));
        reservationDAO.save(reservation);
        
        room.setAvailable(false);
        roomDAO.save(room);
        
        return reservation.getReservationNumber();
    }
    
    private boolean hasConflict(String roomNumber, LocalDate checkIn, LocalDate checkOut) throws Exception {
        List<Reservation> reservations = reservationDAO.findAll();
        for (Reservation r : reservations) {
            if (r.getRoom() != null && r.getRoom().getRoomNumber().equals(roomNumber) 
                && ("CONFIRMED".equals(r.getStatus()) || "PENDING".equals(r.getStatus()))) {
                LocalDate rIn = r.getCheckInDate();
                LocalDate rOut = r.getCheckOutDate();
                if (!(checkOut.isBefore(rIn) || checkIn.isAfter(rOut))) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public Reservation getReservationByNumber(String number) throws Exception {
        return reservationDAO.findByReservationNumber(number);
    }
    
    public List<Reservation> getAllReservations() throws Exception {
        return reservationDAO.findAll();
    }
    
    public List<Reservation> searchByGuestName(String name) throws Exception {
        return reservationDAO.findByGuestName(name);
    }
    
    public String calculateBill(String reservationNumber) throws Exception {
        Reservation reservation = reservationDAO.findByReservationNumber(reservationNumber);
        if (reservation == null) return "Reservation not found";
        
        double total = pricingStrategy.calculatePrice(reservation);
        reservation.setTotalAmount(total);
        reservationDAO.save(reservation);
        
        StringBuilder bill = new StringBuilder();
        bill.append("========================================\n");
        bill.append("     OCEAN VIEW RESORT - BILL          \n");
        bill.append("========================================\n");
        bill.append("Reservation: ").append(reservation.getReservationNumber()).append("\n");
        if (reservation.getGuest() != null) {
            bill.append("Guest: ").append(reservation.getGuest().getName()).append("\n");
        }
        if (reservation.getRoom() != null) {
            bill.append("Room: ").append(reservation.getRoom().getRoomNumber()).append("\n");
        }
        bill.append("Check-in: ").append(reservation.getCheckInDate()).append("\n");
        bill.append("Check-out: ").append(reservation.getCheckOutDate()).append("\n");
        bill.append("Nights: ").append(reservation.getNumberOfNights()).append("\n");
        if (reservation.getRoom() != null) {
            bill.append("Rate: Rs. ").append(String.format("%.2f", reservation.getRoom().getRate())).append("/night\n");
        }
        bill.append("TOTAL: Rs. ").append(String.format("%.2f", total)).append("\n");
        bill.append("========================================\n");
        return bill.toString();
    }
}
