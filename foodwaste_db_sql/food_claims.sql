-- Represents claims made by NGOs for available food donations
CREATE TABLE food_claims (
    claim_id INT AUTO_INCREMENT PRIMARY KEY,
    donation_id INT NOT NULL,
    ngo_id INT NOT NULL,
    status ENUM('PENDING', 'APPROVED', 'REJECTED', 'COMPLETED') DEFAULT 'PENDING',
    claimed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (donation_id) REFERENCES food_donations(donation_id) ON DELETE CASCADE,
    FOREIGN KEY (ngo_id) REFERENCES users(user_id) ON DELETE CASCADE
);