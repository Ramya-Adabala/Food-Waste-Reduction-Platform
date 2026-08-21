package com.foodwaste.controller;

import com.foodwaste.model.FoodDonation;
import com.foodwaste.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/donations")
@CrossOrigin(origins = "*")
public class DonationController {

    @Autowired
    private DonationService donationService;

    @PostMapping
    public ResponseEntity<?> createDonation(@RequestBody Map<String, Object> payload) {
        try {
            FoodDonation donation = new FoodDonation();
            donation.setRestaurantId(Integer.parseInt(payload.get("restaurantId").toString()));
            donation.setFoodItem(payload.get("foodItem").toString());
            donation.setQuantity(Integer.parseInt(payload.get("quantity").toString()));
            donation.setUnit(payload.get("unit").toString());
            
            // Parse HTML datetime-local string (e.g. "2026-07-15T12:00")
            String expirationStr = payload.get("expirationDate").toString();
            if(expirationStr.length() == 16) {
                expirationStr += ":00"; // Add seconds if missing
            }
            LocalDateTime localDateTime = LocalDateTime.parse(expirationStr);
            donation.setExpirationDate(Timestamp.valueOf(localDateTime));

            boolean success = donationService.createDonation(donation);
            if (success) {
                return ResponseEntity.ok().body(Map.of("message", "Donation created successfully"));
            } else {
                return ResponseEntity.badRequest().body(Map.of("error", "Failed to create donation"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid data format"));
        }
    }

    @GetMapping
    public ResponseEntity<List<FoodDonation>> getDonations() {
        return ResponseEntity.ok(donationService.getAvailableDonations());
    }

    @PutMapping("/{id}/claim")
    public ResponseEntity<?> claimDonation(@PathVariable("id") int id, @RequestBody Map<String, Object> payload) {
        try {
            int ngoId = Integer.parseInt(payload.get("ngoId").toString());
            boolean success = donationService.claimDonation(id, ngoId);
            if (success) {
                return ResponseEntity.ok().body(Map.of("message", "Donation claimed successfully"));
            } else {
                return ResponseEntity.badRequest().body(Map.of("error", "Failed to claim donation"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid data format"));
        }
    }
    
    @PutMapping("/{id}/approve")
    public ResponseEntity<?> approveDonation(@PathVariable("id") int id, @RequestBody Map<String, Object> payload) {
        try {
            String deliveryTime = payload.getOrDefault("deliveryTime", "").toString();
            boolean success = donationService.approveDonation(id, deliveryTime);
            if (success) {
                return ResponseEntity.ok().body(Map.of("message", "Donation approved successfully"));
            } else {
                return ResponseEntity.badRequest().body(Map.of("error", "Failed to approve donation"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid data format"));
        }
    }
    
    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<FoodDonation>> getDonationsByRestaurant(@PathVariable("id") int id) {
        return ResponseEntity.ok(donationService.getDonationsByRestaurant(id));
    }
    
    @GetMapping("/ngo/{id}")
    public ResponseEntity<List<FoodDonation>> getDonationsByNgo(@PathVariable("id") int id) {
        return ResponseEntity.ok(donationService.getDonationsByNgo(id));
    }
}
