package com.oceanview.resort.service;

import com.oceanview.resort.model.Guest;
import com.oceanview.resort.model.Reservation;
import com.oceanview.resort.model.Room;
import java.time.LocalDate;

/**
 * Lightweight tests for reservation-related business behavior.
 */
public class ReservationServiceTest {

    public static void main(String[] args) {
        ReservationServiceTest test = new ReservationServiceTest();
        int passed = 0;
        int failed = 0;

        System.out.println("Running ReservationService Tests...\n");

        if (test.testDiscountAppliedForSevenNights()) {
            System.out.println("✓ Discount calculation for 7+ nights - PASSED");
            passed++;
        } else {
            System.out.println("✗ Discount calculation for 7+ nights - FAILED");
            failed++;
        }

        if (test.testNoDiscountForShortStay()) {
            System.out.println("✓ No discount for short stay - PASSED");
            passed++;
        } else {
            System.out.println("✗ No discount for short stay - FAILED");
            failed++;
        }

        if (test.testReservationCalculatesNightsCorrectly()) {
            System.out.println("✓ Reservation nights calculation - PASSED");
            passed++;
        } else {
            System.out.println("✗ Reservation nights calculation - FAILED");
            failed++;
        }

        System.out.println("\n=== Test Results ===");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("Total: " + (passed + failed));
    }

    private boolean testDiscountAppliedForSevenNights() {
        Room room = new Room("R101", Room.RoomType.SINGLE, true, 1, "AC");
        Guest guest = new Guest("Nimal Perera", "Galle", "0712345678", "nimal@example.com", "123456789V");

        Reservation reservation = new Reservation(
                "RES2001",
                guest,
                room,
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(8)
        );

        PricingStrategy strategy = new DiscountPricingStrategy();
        double expected = room.getRate() * 7 * 0.90;
        double actual = strategy.calculatePrice(reservation);

        return Math.abs(actual - expected) < 0.001;
    }

    private boolean testNoDiscountForShortStay() {
        Room room = new Room("R201", Room.RoomType.DOUBLE, true, 2, "AC");
        Guest guest = new Guest("Amali Silva", "Matara", "0771234567", "amali@example.com", "987654321V");

        Reservation reservation = new Reservation(
                "RES2002",
                guest,
                room,
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(4)
        );

        PricingStrategy strategy = new DiscountPricingStrategy();
        double expected = room.getRate() * 3;
        double actual = strategy.calculatePrice(reservation);

        return Math.abs(actual - expected) < 0.001;
    }

    private boolean testReservationCalculatesNightsCorrectly() {
        Room room = new Room("R301", Room.RoomType.DELUXE, true, 3, "Balcony");
        Guest guest = new Guest("Kasun Fernando", "Colombo", "0769876543", "kasun@example.com", "456123789V");

        Reservation reservation = new Reservation(
                "RES2003",
                guest,
                room,
                LocalDate.now().plusDays(5),
                LocalDate.now().plusDays(10)
        );

        return reservation.getNumberOfNights() == 5;
    }
}
