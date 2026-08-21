package com.foodwaste.dao;

import com.foodwaste.model.FoodDonation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import jakarta.annotation.PostConstruct;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DonationDaoImpl implements DonationDao {

    @Autowired
    private DataSource dataSource;
    
    @PostConstruct
    public void init() {
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SHOW COLUMNS FROM food_donations LIKE 'claimed_by'");
            if (!rs.next()) {
                stmt.executeUpdate("ALTER TABLE food_donations ADD COLUMN claimed_by INT");
                stmt.executeUpdate("ALTER TABLE food_donations ADD FOREIGN KEY (claimed_by) REFERENCES users(user_id) ON DELETE SET NULL");
            }
            ResultSet rs2 = stmt.executeQuery("SHOW COLUMNS FROM food_donations LIKE 'delivery_time_estimate'");
            if (!rs2.next()) {
                stmt.executeUpdate("ALTER TABLE food_donations ADD COLUMN delivery_time_estimate VARCHAR(255)");
            }
            // Add APPROVED to the ENUM if it doesn't exist
            stmt.executeUpdate("ALTER TABLE food_donations MODIFY COLUMN status ENUM('AVAILABLE', 'CLAIMED', 'APPROVED', 'EXPIRED') DEFAULT 'AVAILABLE'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean createDonation(FoodDonation donation) {
        String sql = "INSERT INTO food_donations (restaurant_id, food_item, quantity, unit, expiration_date, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donation.getRestaurantId());
            stmt.setString(2, donation.getFoodItem());
            stmt.setInt(3, donation.getQuantity());
            stmt.setString(4, donation.getUnit());
            stmt.setTimestamp(5, donation.getExpirationDate());
            stmt.setString(6, "AVAILABLE");
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<FoodDonation> getAllAvailableDonations() {
        List<FoodDonation> donations = new ArrayList<>();
        String sql = "SELECT d.*, u.username as r_name, u.phone as r_phone, u.address as r_address " +
                     "FROM food_donations d " +
                     "JOIN users u ON d.restaurant_id = u.user_id " +
                     "WHERE d.status = 'AVAILABLE'";
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                FoodDonation d = new FoodDonation();
                d.setDonationId(rs.getInt("donation_id"));
                d.setRestaurantId(rs.getInt("restaurant_id"));
                d.setFoodItem(rs.getString("food_item"));
                d.setQuantity(rs.getInt("quantity"));
                d.setUnit(rs.getString("unit"));
                d.setExpirationDate(rs.getTimestamp("expiration_date"));
                d.setStatus(rs.getString("status"));
                d.setCreatedAt(rs.getTimestamp("created_at"));
                
                // Set the joined restaurant connection details
                d.setRestaurantName(rs.getString("r_name"));
                d.setRestaurantPhone(rs.getString("r_phone"));
                d.setRestaurantAddress(rs.getString("r_address"));
                
                donations.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return donations;
    }

    @Override
    public boolean claimDonation(int donationId, int ngoId) {
        String sql = "UPDATE food_donations SET status = 'CLAIMED', claimed_by = ? WHERE donation_id = ?";
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ngoId);
            stmt.setInt(2, donationId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public List<FoodDonation> getDonationsByRestaurant(int restaurantId) {
        List<FoodDonation> donations = new ArrayList<>();
        String sql = "SELECT d.*, u.username as ngo_name, u.phone as ngo_phone, u.address as ngo_address " +
                     "FROM food_donations d " +
                     "LEFT JOIN users u ON d.claimed_by = u.user_id " +
                     "WHERE d.restaurant_id = ? " +
                     "ORDER BY d.created_at DESC";
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, restaurantId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                FoodDonation d = new FoodDonation();
                d.setDonationId(rs.getInt("donation_id"));
                d.setRestaurantId(rs.getInt("restaurant_id"));
                d.setFoodItem(rs.getString("food_item"));
                d.setQuantity(rs.getInt("quantity"));
                d.setUnit(rs.getString("unit"));
                d.setExpirationDate(rs.getTimestamp("expiration_date"));
                d.setStatus(rs.getString("status"));
                d.setCreatedAt(rs.getTimestamp("created_at"));
                
                int claimedBy = rs.getInt("claimed_by");
                if (!rs.wasNull()) {
                    d.setClaimedBy(claimedBy);
                    d.setNgoName(rs.getString("ngo_name"));
                    d.setNgoPhone(rs.getString("ngo_phone"));
                    d.setNgoAddress(rs.getString("ngo_address"));
                }
                d.setDeliveryTimeEstimate(rs.getString("delivery_time_estimate"));
                donations.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return donations;
    }
    
    @Override
    public boolean approveDonation(int donationId, String deliveryTime) {
        String sql = "UPDATE food_donations SET status = 'APPROVED', delivery_time_estimate = ? WHERE donation_id = ?";
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, deliveryTime);
            stmt.setInt(2, donationId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public List<FoodDonation> getDonationsByNgo(int ngoId) {
        List<FoodDonation> donations = new ArrayList<>();
        String sql = "SELECT d.*, u.username as restaurant_name, u.phone as restaurant_phone, u.address as restaurant_address " +
                     "FROM food_donations d " +
                     "JOIN users u ON d.restaurant_id = u.user_id " +
                     "WHERE d.claimed_by = ? " +
                     "ORDER BY d.created_at DESC";
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ngoId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                FoodDonation d = new FoodDonation();
                d.setDonationId(rs.getInt("donation_id"));
                d.setRestaurantId(rs.getInt("restaurant_id"));
                d.setFoodItem(rs.getString("food_item"));
                d.setQuantity(rs.getInt("quantity"));
                d.setUnit(rs.getString("unit"));
                d.setExpirationDate(rs.getTimestamp("expiration_date"));
                d.setStatus(rs.getString("status"));
                d.setCreatedAt(rs.getTimestamp("created_at"));
                
                // Restaurant info
                d.setRestaurantName(rs.getString("restaurant_name"));
                d.setRestaurantPhone(rs.getString("restaurant_phone"));
                d.setRestaurantAddress(rs.getString("restaurant_address"));
                
                d.setClaimedBy(rs.getInt("claimed_by"));
                d.setDeliveryTimeEstimate(rs.getString("delivery_time_estimate"));
                donations.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return donations;
    }
}
