package com.oceanview.resort.service;

import com.oceanview.resort.model.Guest;
import com.oceanview.resort.model.Reservation;
import java.time.LocalDate;

/**
 * Test class for ReservationService
 * Manual testing approach (pure Java, no external test libraries)
 */
public class ReservationServiceTest {
    
    public static void main(String[] args) {
        System.out.println("=== ReservationService Test Suite ===\n");
        
        int passed = 0;
        int failed = 0;
        
        // Test 1: Create Reservation
        try {
            testCreateReservation();
            System.out.println("✓ Test 1: Create Reservation - PASSED");
            passed++;
        } catch (Exception e) {
            System.out.println("✗ Test 1: Create Reservation - FAILED: " + e.getMessage());
            failed++;
        }
        
        // Test 2: Calculate Bill
        try {
            testCalculateBill();
            System.out.println("✓ Test 2: Calculate Bill - PASSED");
            passed++;
        } catch (Exception e) {
            System.out.println("✗ Test 2: Calculate Bill - FAILED: " + e.getMessage());
            failed++;
        }
        
        // Test 3: Date Validation
        try {
            testDateValidation();
            System.out.println("✓ Test 3: Date Validation - PASSED");
            passed++;
        } catch (Exception e) {
            System.out.println("✗ Test 3: Date Validation - FAILED: " + e.getMessage());
            failed++;
        }
        
        // Test 4: Get Reservation
        try {
            testGetReservation();
            System.out.println("✓ Test 4: Get Reservation - PASSED");
            passed++;
        } catch (Exception e) {
            System.out.println("✗ Test 4: Get Reservation - FAILED: " + e.getMessage());
            failed++;
        }
        
        System.out.println("\n=== Test Results ===");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("Total: " + (passed + failed));
    }
    
    private static void testCreateReservation() throws Exception {
        ReservationService service = new ReservationService();
        Guest guest = new Guest("Test Guest", "123 Test St", "0712345678", "test@email.com", "123456789V");
        
        String checkIn = LocalDate.now().plusDays(1).toString();
        String checkOut = LocalDate.now().plusDays(3).toString();
        
        String reservationNumber = service.createReservation(guest, "R101", checkIn, checkOut);
        
        if (reservationNumber == null || reservationNumber.isEmpty()) {
            throw new Exception("Reservation number not generated");
        }
    }
    
    private static void testCalculateBill() throws Exception {
        ReservationService service = new ReservationService();
        Guest guest = new Guest("Bill Test", "123 St", "0712345678", "bill@test.com", "987654321V");
        String checkIn = LocalDate.now().plusDays(5).toString();
        String checkOut = LocalDate.now().plusDays(8).toString();
        
        String reservationNumber = service.createReservation(guest, "R102", checkIn, checkOut);
        String bill = service.calculateBill(reservationNumber);
        
        if (bill == null || bill.isEmpty() || !bill.contains("TOTAL")) {
            throw new Exception("Bill not calculated correctly");
        }
    }
    
    private static void testDateValidation() throws Exception {
        ReservationService service = new ReservationService();
        Guest guest = new Guest("Date Test", "123 St", "0712345678", "date@test.com", "111222333V");
        
        // Test invalid date range
        try {
            String checkIn = LocalDate.now().plusDays(5).toString();
            String checkOut = LocalDate.now().plusDays(3).toString();
            service.createReservation(guest, "R103", checkIn, checkOut);
            throw new Exception("Should have thrown exception for invalid date range");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
        
        // Test past date
        try {
            String checkIn = LocalDate.now().minusDays(1).toString();
            String checkOut = LocalDate.now().plusDays(2).toString();
            service.createReservation(guest, "R103", checkIn, checkOut);
            throw new Exception("Should have thrown exception for past date");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
    
    private static void testGetReservation() throws Exception {
        ReservationService service = new ReservationService();
        Guest guest = new Guest("Get Test", "123 St", "0712345678", "get@test.com", "444555666V");
        String checkIn = LocalDate.now().plusDays(10).toString();
        String checkOut = LocalDate.now().plusDays(12).toString();
        
        String reservationNumber = service.createReservation(guest, "R104", checkIn, checkOut);
        Reservation reservation = service.getReservationByNumber(reservationNumber);
        
        if (reservation == null) {
            throw new Exception("Reservation not retrieved");
        }
        
        if (!reservation.getReservationNumber().equals(reservationNumber)) {
            throw new Exception("Wrong reservation retrieved");
        }
    }
}
