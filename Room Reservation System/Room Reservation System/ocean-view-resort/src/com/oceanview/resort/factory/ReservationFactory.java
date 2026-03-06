package com.oceanview.resort.factory;

import com.oceanview.resort.model.Guest;
import com.oceanview.resort.model.Reservation;
import com.oceanview.resort.model.Room;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReservationFactory {
    private static int counter = 1000;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static Reservation createReservation(Guest guest, Room room, LocalDate checkIn, LocalDate checkOut) {
        String number = "RES-" + LocalDate.now().format(DATE_FORMAT) + "-" + (counter++);
        return new Reservation(number, guest, room, checkIn, checkOut);
    }
}
