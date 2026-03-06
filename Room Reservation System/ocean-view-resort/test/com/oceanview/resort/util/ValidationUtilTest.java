package com.oceanview.resort.util;

import java.time.LocalDate;

/**
 * Test class for ValidationUtil
 * Manual testing approach
 */
public class ValidationUtilTest {
    
    public static void main(String[] args) {
        System.out.println("=== ValidationUtil Test Suite ===\n");
        
        int passed = 0;
        int failed = 0;
        
        // Test Email Validation
        try {
            if (!ValidationUtil.isValidEmail("test@example.com")) {
                throw new Exception("Valid email should pass");
            }
            if (ValidationUtil.isValidEmail("invalid-email")) {
                throw new Exception("Invalid email should fail");
            }
            System.out.println("✓ Email Validation - PASSED");
            passed++;
        } catch (Exception e) {
            System.out.println("✗ Email Validation - FAILED: " + e.getMessage());
            failed++;
        }
        
        // Test Phone Validation
        try {
            if (!ValidationUtil.isValidPhone("0712345678")) {
                throw new Exception("Valid phone should pass");
            }
            if (ValidationUtil.isValidPhone("123")) {
                throw new Exception("Short phone should fail");
            }
            System.out.println("✓ Phone Validation - PASSED");
            passed++;
        } catch (Exception e) {
            System.out.println("✗ Phone Validation - FAILED: " + e.getMessage());
            failed++;
        }
        
        // Test Date Validation
        try {
            if (!ValidationUtil.isValidDate("2024-12-31")) {
                throw new Exception("Valid date should pass");
            }
            if (ValidationUtil.isValidDate("invalid-date")) {
                throw new Exception("Invalid date should fail");
            }
            if (!ValidationUtil.isDateInFuture(LocalDate.now().plusDays(1))) {
                throw new Exception("Future date should pass");
            }
            if (ValidationUtil.isDateInFuture(LocalDate.now().minusDays(1))) {
                throw new Exception("Past date should fail");
            }
            System.out.println("✓ Date Validation - PASSED");
            passed++;
        } catch (Exception e) {
            System.out.println("✗ Date Validation - FAILED: " + e.getMessage());
            failed++;
        }
        
        // Test Name Validation
        try {
            if (!ValidationUtil.isValidName("John Doe")) {
                throw new Exception("Valid name should pass");
            }
            if (ValidationUtil.isValidName("A")) {
                throw new Exception("Short name should fail");
            }
            System.out.println("✓ Name Validation - PASSED");
            passed++;
        } catch (Exception e) {
            System.out.println("✗ Name Validation - FAILED: " + e.getMessage());
            failed++;
        }
        
        System.out.println("\n=== Test Results ===");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("Total: " + (passed + failed));
    }
}
