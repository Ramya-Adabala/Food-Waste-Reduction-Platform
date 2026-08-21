DELIMITER //
-- Stored Procedure to claim food safely
CREATE PROCEDURE ClaimFood(IN p_donation_id INT, IN p_ngo_id INT)
BEGIN
    DECLARE current_status VARCHAR(20);
    
    -- Check if the donation is still available
    SELECT status INTO current_status FROM food_donations WHERE donation_id = p_donation_id;
    
    IF current_status = 'AVAILABLE' THEN
        -- Update donation status
        UPDATE food_donations SET status = 'CLAIMED' WHERE donation_id = p_donation_id;
        
        -- Insert claim record
        INSERT INTO food_claims (donation_id, ngo_id, status) VALUES (p_donation_id, p_ngo_id, 'APPROVED');
        
        -- Auto-generate a pending delivery task
        INSERT INTO deliveries (claim_id, status) VALUES (LAST_INSERT_ID(), 'PENDING');
        
        SELECT 'Claim successful' AS message;
    ELSE
        SELECT 'Food is no longer available' AS message;
    END IF;
END //
DELIMITER ;
