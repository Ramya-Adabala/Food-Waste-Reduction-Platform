import java.util.List;

public interface DonationDao {
    boolean addDonation(Donation donation);
    boolean updateDonation(Donation donation);
    boolean deleteDonation(int donationId, int restaurantId);
    List<Donation> getDonationsByRestaurant(int restaurantId);
    Donation getDonationById(int donationId);
}
