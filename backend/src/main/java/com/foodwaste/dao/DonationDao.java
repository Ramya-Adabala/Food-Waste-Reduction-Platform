package com.foodwaste.dao;

import com.foodwaste.model.FoodDonation;
import java.util.List;

public interface DonationDao {
    boolean createDonation(FoodDonation donation);
    List<FoodDonation> getAllAvailableDonations();
    boolean claimDonation(int donationId, int ngoId);
    boolean approveDonation(int donationId, String deliveryTime);
    List<FoodDonation> getDonationsByRestaurant(int restaurantId);
    List<FoodDonation> getDonationsByNgo(int ngoId);
}
