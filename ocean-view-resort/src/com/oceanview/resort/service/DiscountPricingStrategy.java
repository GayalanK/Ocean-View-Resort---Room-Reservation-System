package com.oceanview.resort.service;

import com.oceanview.resort.model.Reservation;

/**
 * Discount Pricing Strategy - Applies 10% discount for stays of 7+ nights
 */
public class DiscountPricingStrategy implements PricingStrategy {
    private static final int DISCOUNT_THRESHOLD = 7;
    private static final double DISCOUNT_RATE = 0.10; // 10% discount
    
    @Override
    public double calculatePrice(Reservation reservation) {
        if (reservation == null || reservation.getRoom() == null) {
            return 0.0;
        }
        
        double basePrice = reservation.getRoom().getRate() * reservation.getNumberOfNights();
        
        // Apply discount for long stays
        if (reservation.getNumberOfNights() >= DISCOUNT_THRESHOLD) {
            double discount = basePrice * DISCOUNT_RATE;
            return basePrice - discount;
        }
        
        return basePrice;
    }
}
