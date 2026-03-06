package com.oceanview.resort.factory;

import com.oceanview.resort.model.Guest;
import com.oceanview.resort.model.Reservation;
import com.oceanview.resort.model.Room;
import java.time.LocalDate;

public class ReservationFactory {
    private static int counter = 1000;
    
    public static Reservation createReservation(Guest guest, Room room, LocalDate checkIn, LocalDate checkOut) {
        String number = "RES-" + System.currentTimeMillis() + "-" + (counter++);
        return new Reservation(number, guest, room, checkIn, checkOut);
    }
}
