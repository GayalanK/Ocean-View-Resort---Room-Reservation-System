package com.oceanview.resort.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

/**
 * Validation Utility - Input validation for the application
 */
public class ValidationUtil {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );
    
    private static final Pattern PHONE_PATTERN = Pattern.compile(
        "^[0-9]{10}$"
    );
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    
    /**
     * Validate email format
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }
    
    /**
     * Validate phone number (10 digits)
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        // Remove spaces and dashes
        String cleaned = phone.replaceAll("[\\s-]", "");
        return PHONE_PATTERN.matcher(cleaned).matches();
    }
    
    /**
     * Validate date format (YYYY-MM-DD)
     */
    public static boolean isValidDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return false;
        }
        try {
            LocalDate.parse(dateStr.trim(), DATE_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    
    /**
     * Check if date is in the future
     */
    public static boolean isDateInFuture(LocalDate date) {
        return date != null && date.isAfter(LocalDate.now());
    }
    
    /**
     * Validate date range (check-out after check-in)
     */
    public static boolean isValidDateRange(LocalDate checkIn, LocalDate checkOut) {
        if (checkIn == null || checkOut == null) {
            return false;
        }
        return checkOut.isAfter(checkIn);
    }
    
    /**
     * Validate name (non-empty, contains only letters and spaces)
     */
    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return name.trim().length() >= 2 && name.matches("^[a-zA-Z\\s]+$");
    }
    
    /**
     * Validate room number format
     */
    public static boolean isValidRoomNumber(String roomNumber) {
        if (roomNumber == null || roomNumber.trim().isEmpty()) {
            return false;
        }
        // Room number should be like R101, R201, etc.
        return roomNumber.matches("^R[0-9]{3}$");
    }
    
    /**
     * Sanitize input string (remove dangerous characters)
     */
    public static String sanitizeInput(String input) {
        if (input == null) {
            return "";
        }
        return input.trim()
                   .replaceAll("[<>\"']", "")
                   .replaceAll("[\r\n]", "");
    }
}
