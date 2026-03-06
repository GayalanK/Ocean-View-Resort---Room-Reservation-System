package com.oceanview.resort.service;

import com.oceanview.resort.model.Reservation;

public interface PricingStrategy {
    double calculatePrice(Reservation reservation);
}
