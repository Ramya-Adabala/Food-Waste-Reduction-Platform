import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DonationDaoImpl implements DonationDao {

    @Override
    public boolean addDonation(Donation donation) {
        String sql = "INSERT INTO food_donations (restaurant_id, food_item, quantity, unit, expiration_date, status) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donation.getRestaurantId());
            stmt.setString(2, donation.getFoodItem());
            stmt.setInt(3, donation.getQuantity());
            stmt.setString(4, donation.getUnit());
            stmt.setTimestamp(5, donation.getExpirationDate());
            stmt.setString(6, donation.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateDonation(Donation donation) {
        String sql = "UPDATE food_donations SET food_item=?, quantity=?, unit=? WHERE donation_id=? AND restaurant_id=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, donation.getFoodItem());
            stmt.setInt(2, donation.getQuantity());
            stmt.setString(3, donation.getUnit());
            stmt.setInt(4, donation.getDonationId());
            stmt.setInt(5, donation.getRestaurantId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteDonation(int donationId, int restaurantId) {
        String sql = "DELETE FROM food_donations WHERE donation_id=? AND restaurant_id=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donationId);
            stmt.setInt(2, restaurantId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Donation> getDonationsByRestaurant(int restaurantId) {
        List<Donation> list = new ArrayList<>();
        String sql = "SELECT * FROM food_donations WHERE restaurant_id = ?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, restaurantId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Donation d = new Donation();
                    d.setDonationId(rs.getInt("donation_id"));
                    d.setRestaurantId(rs.getInt("restaurant_id"));
                    d.setFoodItem(rs.getString("food_item"));
                    d.setQuantity(rs.getInt("quantity"));
                    d.setUnit(rs.getString("unit"));
                    d.setExpirationDate(rs.getTimestamp("expiration_date"));
                    d.setStatus(rs.getString("status"));
                    d.setCreatedAt(rs.getTimestamp("created_at"));
                    list.add(d);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Donation getDonationById(int donationId) {
        // Implementation similar to above if needed for validation
        return null; 
    }
}
