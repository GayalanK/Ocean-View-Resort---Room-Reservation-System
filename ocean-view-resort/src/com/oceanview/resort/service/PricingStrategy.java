package com.oceanview.resort.service;

import com.oceanview.resort.model.Reservation;

/**
 * Pricing Strategy Interface - Strategy pattern for pricing calculations
 */
public interface PricingStrategy {
    double calculatePrice(Reservation reservation);
}
