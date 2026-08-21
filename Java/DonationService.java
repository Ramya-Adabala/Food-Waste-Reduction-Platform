import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class DonationService {
    private DonationDao donationDao;

    public DonationService() {
        this.donationDao = new DonationDaoImpl();
    }

    public boolean addDonation(int restaurantId, String foodItem, int quantity, String unit, int hoursUntilExpiry) {
        if (quantity <= 0) {
            System.out.println("Quantity must be greater than zero.");
            return false;
        }
        Timestamp expiration = Timestamp.valueOf(LocalDateTime.now().plusHours(hoursUntilExpiry));
        Donation donation = new Donation(restaurantId, foodItem, quantity, unit, expiration, "AVAILABLE");
        
        return donationDao.addDonation(donation);
    }

    public boolean updateDonation(int donationId, int restaurantId, String foodItem, int quantity, String unit) {
        if (quantity <= 0) return false;
        Donation donation = new Donation();
        donation.setDonationId(donationId);
        donation.setRestaurantId(restaurantId);
        donation.setFoodItem(foodItem);
        donation.setQuantity(quantity);
        donation.setUnit(unit);
        return donationDao.updateDonation(donation);
    }

    public boolean deleteDonation(int donationId, int restaurantId) {
        return donationDao.deleteDonation(donationId, restaurantId);
    }

    public List<Donation> getDonationsByRestaurant(int restaurantId) {
        return donationDao.getDonationsByRestaurant(restaurantId);
    }
}
