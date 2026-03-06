package com.oceanview.resort.service;

import com.oceanview.resort.model.Reservation;

public class DiscountPricingStrategy implements PricingStrategy {
    private static final int THRESHOLD = 7;
    private static final double DISCOUNT = 0.10;
    
    @Override
    public double calculatePrice(Reservation reservation) {
        if (reservation.getRoom() == null || reservation.getNumberOfNights() <= 0) return 0.0;
        double base = reservation.getRoom().getRate() * reservation.getNumberOfNights();
        return reservation.getNumberOfNights() >= THRESHOLD ? base * (1 - DISCOUNT) : base;
    }
}
