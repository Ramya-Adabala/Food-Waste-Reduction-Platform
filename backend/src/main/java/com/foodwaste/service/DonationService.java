package com.foodwaste.service;

import com.foodwaste.dao.DonationDao;
import com.foodwaste.model.FoodDonation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonationService {

    @Autowired
    private DonationDao donationDao;

    public boolean createDonation(FoodDonation donation) {
        return donationDao.createDonation(donation);
    }

    public List<FoodDonation> getAvailableDonations() {
        return donationDao.getAllAvailableDonations();
    }

    public boolean claimDonation(int donationId, int ngoId) {
        return donationDao.claimDonation(donationId, ngoId);
    }
    
    public boolean approveDonation(int donationId, String deliveryTime) {
        return donationDao.approveDonation(donationId, deliveryTime);
    }
    
    public List<FoodDonation> getDonationsByRestaurant(int restaurantId) {
        return donationDao.getDonationsByRestaurant(restaurantId);
    }
    
    public List<FoodDonation> getDonationsByNgo(int ngoId) {
        return donationDao.getDonationsByNgo(ngoId);
    }
}
