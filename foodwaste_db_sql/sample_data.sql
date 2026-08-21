-- Insert Dummy Users
INSERT INTO users (username, password_hash, email, phone, role, address) VALUES
('Admin_John', 'hash123', 'admin@fw.com', '1234567890', 'ADMIN', 'HQ'),
('City Grille', 'hash123', 'contact@citygrille.com', '9876543210', 'RESTAURANT', '123 Main St'),
('Hope Shelter', 'hash123', 'help@hopeshelter.org', '1122334455', 'NGO', '456 Charity Ave'),
('Tom Volunteer', 'hash123', 'tom@gmail.com', '5566778899', 'VOLUNTEER', '789 Fast Rd');
-- Insert Dummy Donation
INSERT INTO food_donations (restaurant_id, food_item, quantity, unit, expiration_date) VALUES
(2, 'Lasagna', 20, 'Portions', DATE_ADD(NOW(), INTERVAL 1 DAY));