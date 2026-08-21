-- View: Active available donations with restaurant info
CREATE VIEW active_donations_view AS
SELECT d.donation_id, d.food_item, d.quantity, d.expiration_date, u.username AS restaurant_name, u.address
FROM food_donations d
JOIN users u ON d.restaurant_id = u.user_id
WHERE d.status = 'AVAILABLE' AND d.expiration_date > NOW();
-- View: Delivery tracking details
CREATE VIEW delivery_tracking_view AS
SELECT del.delivery_id, f.food_item, r.username AS restaurant_name, n.username AS ngo_name, v.username AS volunteer_name, del.status
FROM deliveries del
JOIN food_claims c ON del.claim_id = c.claim_id
JOIN food_donations f ON c.donation_id = f.donation_id
JOIN users r ON f.restaurant_id = r.user_id
JOIN users n ON c.ngo_id = n.user_id
LEFT JOIN users v ON del.volunteer_id = v.user_id;