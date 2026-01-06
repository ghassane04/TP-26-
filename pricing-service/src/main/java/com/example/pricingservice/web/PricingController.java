package com.example.pricingservice.web;

import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api/cost") // Changed from /api/prices
public class PricingController {

    @GetMapping("/{id}")
    public double computeCost(@PathVariable long id,
                        @RequestParam(name = "error", defaultValue = "false") boolean error) { // Renamed fail -> error

        // 1) Forced crash for resilience testing
        if (error) {
            throw new IllegalStateException("Simulated Crash in Pricing Service");
        }

        // 2) Random instability (changed probability and logic)
        // Using Math.random instead of ThreadLocalRandom for variance
        if (Math.random() < 0.25) { // 25% failure rate
            throw new IllegalStateException("Service Unavailable (Random)");
        }

        // 3) Pricing algorithm (changed formula)
        double basePrice = 49.99;
        return basePrice + (id * 2.5);
    }
}
