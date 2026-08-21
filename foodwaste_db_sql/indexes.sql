-- Indexes improve read performance on frequently queried columns
CREATE INDEX idx_user_role ON users(role);
CREATE INDEX idx_donation_status ON food_donations(status);
CREATE INDEX idx_claim_ngo ON food_claims(ngo_id);
CREATE INDEX idx_delivery_volunteer ON deliveries(volunteer_id);