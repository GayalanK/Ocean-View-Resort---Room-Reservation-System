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

    public Reservation cancelReservation(String reservationNumber) throws Exception {
        Reservation reservation = reservationDAO.findByReservationNumber(reservationNumber);
        if (reservation == null) {
            return null;
        }

        if ("CANCELLED".equals(reservation.getStatus())) {
            throw new IllegalArgumentException("Reservation is already cancelled");
        }

        reservation.setStatus("CANCELLED");
        reservationDAO.save(reservation);

        // Make the room available again
        if (reservation.getRoom() != null) {
            reservation.getRoom().setAvailable(true);
            roomDAO.save(reservation.getRoom());
        }

        return reservation;
    }

    public List<Reservation> getReservationsByDateRange(String fromDateStr, String toDateStr) throws Exception {
        LocalDate fromDate = LocalDate.parse(fromDateStr);
        LocalDate toDate = LocalDate.parse(toDateStr);
        return reservationDAO.findByDateRange(fromDate, toDate);
    }

    public String calculateBill(String reservationNumber) throws Exception {
        Reservation reservation = reservationDAO.findByReservationNumber(reservationNumber);
        if (reservation == null)
            return "Reservation not found";

        double total = pricingStrategy.calculatePrice(reservation);
        reservation.setTotalAmount(total);
        reservationDAO.save(reservation);

        double rate = reservation.getRoom().getRate();
        int nights = reservation.getNumberOfNights();
        double subtotal = rate * nights;
        boolean hasDiscount = nights >= 7;
        double discountAmount = hasDiscount ? subtotal * 0.10 : 0;

        StringBuilder bill = new StringBuilder();
        bill.append("========================================\n");
        bill.append("     OCEAN VIEW RESORT - BILL          \n");
        bill.append("========================================\n");
        bill.append("Reservation: ").append(reservation.getReservationNumber()).append("\n");
        bill.append("Guest: ").append(reservation.getGuest().getName()).append("\n");
        bill.append("Room: ").append(reservation.getRoom().getRoomNumber()).append("\n");
        bill.append("Check-in: ").append(reservation.getCheckInDate()).append("\n");
        bill.append("Check-out: ").append(reservation.getCheckOutDate()).append("\n");
        bill.append("Nights: ").append(nights).append("\n");
        bill.append("Rate: Rs. ").append(String.format("%.2f", rate)).append("/night\n");
        bill.append("Subtotal: Rs. ").append(String.format("%.2f", subtotal)).append("\n");
        if (hasDiscount) {
            bill.append("Discount: 10% (7+ nights)\n");
            bill.append("Discount Amount: Rs. ").append(String.format("%.2f", discountAmount)).append("\n");
        } else {
            bill.append("Discount: No discount\n");
        }
        bill.append("TOTAL: Rs. ").append(String.format("%.2f", total)).append("\n");
        bill.append("========================================\n");
        return bill.toString();
    }
}
