import java.sql.Timestamp;

public class Donation {
    private int donationId;
    private int restaurantId;
    private String foodItem;
    private int quantity;
    private String unit;
    private Timestamp expirationDate;
    private String status;
    private Timestamp createdAt;

    public Donation() {}

    public Donation(int restaurantId, String foodItem, int quantity, String unit, Timestamp expirationDate, String status) {
        this.restaurantId = restaurantId;
        this.foodItem = foodItem;
        this.quantity = quantity;
        this.unit = unit;
        this.expirationDate = expirationDate;
        this.status = status;
    }

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
}
