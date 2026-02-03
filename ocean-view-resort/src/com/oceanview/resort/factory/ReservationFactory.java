package com.oceanview.resort.factory;

import com.oceanview.resort.model.Guest;
import com.oceanview.resort.model.Reservation;
import com.oceanview.resort.model.Room;
import java.time.LocalDate;

/**
 * Reservation Factory - Factory pattern for creating reservations
 */
public class ReservationFactory {
    private static int reservationCounter = 1000;
    
    /**
     * Create a new reservation
     */
    public static Reservation createReservation(Guest guest, Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        String reservationNumber = generateReservationNumber();
        return new Reservation(reservationNumber, guest, room, checkInDate, checkOutDate);
    }
    
    /**
     * Generate unique reservation number
     */
    private static String generateReservationNumber() {
        reservationCounter++;
        return "RES" + reservationCounter;
    }
    
    /**
     * Reset counter (useful for testing)
     */
    public static void resetCounter() {
        reservationCounter = 1000;
    }
    
    /**
     * Get current counter value
     */
    public static int getCounter() {
        return reservationCounter;
    }
}
