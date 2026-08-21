package com.foodwaste.model;

import java.sql.Timestamp;

public class FoodDonation {
    private int donationId;
    private int restaurantId;
    private String foodItem;
    private int quantity;
    private String unit;
    private Timestamp expirationDate;
    private String status;
    private Timestamp createdAt;
    // Additional fields for NGO connection details (for NGO Dashboard)
    private String restaurantName;
    private String restaurantPhone;
    private String restaurantAddress;
    
    // Additional fields for Restaurant connection details (for Restaurant Dashboard)
    private Integer claimedBy; // Integer to allow nulls
    private String ngoName;
    private String ngoPhone;
    private String ngoAddress;
    private String deliveryTimeEstimate;

    public FoodDonation() {}

    public int getDonationId() { return donationId; }
    public void setDonationId(int donationId) { this.donationId = donationId; }

    public int getRestaurantId() { return restaurantId; }
    public void setRestaurantId(int restaurantId) { this.restaurantId = restaurantId; }

    public String getFoodItem() { return foodItem; }
    public void setFoodItem(String foodItem) { this.foodItem = foodItem; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public Timestamp getExpirationDate() { return expirationDate; }
    public void setExpirationDate(Timestamp expirationDate) { this.expirationDate = expirationDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public String getRestaurantName() { return restaurantName; }
    public void setRestaurantName(String restaurantName) { this.restaurantName = restaurantName; }

    public String getRestaurantPhone() { return restaurantPhone; }
    public void setRestaurantPhone(String restaurantPhone) { this.restaurantPhone = restaurantPhone; }

    public String getRestaurantAddress() { return restaurantAddress; }
    public void setRestaurantAddress(String restaurantAddress) { this.restaurantAddress = restaurantAddress; }
    
    public Integer getClaimedBy() { return claimedBy; }
    public void setClaimedBy(Integer claimedBy) { this.claimedBy = claimedBy; }
    
    public String getNgoName() { return ngoName; }
    public void setNgoName(String ngoName) { this.ngoName = ngoName; }
    
    public String getNgoPhone() { return ngoPhone; }
    public void setNgoPhone(String ngoPhone) { this.ngoPhone = ngoPhone; }
    
    public String getNgoAddress() { return ngoAddress; }
    public void setNgoAddress(String ngoAddress) { this.ngoAddress = ngoAddress; }
    
    public String getDeliveryTimeEstimate() { return deliveryTimeEstimate; }
    public void setDeliveryTimeEstimate(String deliveryTimeEstimate) { this.deliveryTimeEstimate = deliveryTimeEstimate; }
}
