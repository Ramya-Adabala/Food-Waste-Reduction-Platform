-- Represents the delivery assignments for volunteers
CREATE TABLE deliveries (
    delivery_id INT AUTO_INCREMENT PRIMARY KEY,
    claim_id INT NOT NULL UNIQUE, -- 1-to-1 relationship with an approved claim
    volunteer_id INT, -- Can be NULL until a volunteer accepts it
    pickup_time DATETIME,
    delivery_time DATETIME,
    status ENUM('PENDING', 'ACCEPTED', 'PICKED_UP', 'DELIVERED', 'CANCELLED') DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (claim_id) REFERENCES food_claims(claim_id) ON DELETE CASCADE,
    FOREIGN KEY (volunteer_id) REFERENCES users(user_id) ON DELETE SET NULL
);