-- Represents the food items donated by restaurants
CREATE TABLE food_donations (
    donation_id INT AUTO_INCREMENT PRIMARY KEY,
    restaurant_id INT NOT NULL,
    food_item VARCHAR(100) NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    unit VARCHAR(20) NOT NULL,
    expiration_date DATETIME NOT NULL,
    status ENUM('AVAILABLE', 'CLAIMED', 'EXPIRED') DEFAULT 'AVAILABLE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (restaurant_id) REFERENCES users(user_id) ON DELETE CASCADE
);