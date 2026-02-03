package com.oceanview.resort.util;

/**
 * Test class for ValidationUtil
 */
public class ValidationUtilTest {
    
    public static void main(String[] args) {
        ValidationUtilTest test = new ValidationUtilTest();
        int passed = 0;
        int failed = 0;
        
        System.out.println("Running ValidationUtil Tests...\n");
        
        if (test.testEmailValidation()) {
            System.out.println("✓ Email validation - PASSED");
            passed++;
        } else {
            System.out.println("✗ Email validation - FAILED");
            failed++;
        }
        
        if (test.testPhoneValidation()) {
            System.out.println("✓ Phone validation - PASSED");
            passed++;
        } else {
            System.out.println("✗ Phone validation - FAILED");
            failed++;
        }
        
        if (test.testDateValidation()) {
            System.out.println("✓ Date validation - PASSED");
            passed++;
        } else {
            System.out.println("✗ Date validation - FAILED");
            failed++;
        }
        
        System.out.println("\n=== Test Results ===");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("Total: " + (passed + failed));
    }
    
    private boolean testEmailValidation() {
        boolean valid1 = ValidationUtil.isValidEmail("test@example.com");
        boolean valid2 = ValidationUtil.isValidEmail("user.name@domain.co.uk");
        boolean invalid1 = ValidationUtil.isValidEmail("invalid-email");
        boolean invalid2 = ValidationUtil.isValidEmail("");
        
        return valid1 && valid2 && !invalid1 && !invalid2;
    }
    
    private boolean testPhoneValidation() {
        boolean valid1 = ValidationUtil.isValidPhone("0712345678");
        boolean invalid1 = ValidationUtil.isValidPhone("123");
        boolean invalid2 = ValidationUtil.isValidPhone("");
        
        return valid1 && !invalid1 && !invalid2;
    }
    
    private boolean testDateValidation() {
        boolean valid1 = ValidationUtil.isValidDate("2024-12-25");
        boolean invalid1 = ValidationUtil.isValidDate("25-12-2024");
        boolean invalid2 = ValidationUtil.isValidDate("");
        
        return valid1 && !invalid1 && !invalid2;
    }
}
