DELIMITER //
-- Trigger: Automatically log user deletions
CREATE TRIGGER after_user_delete
AFTER DELETE ON users
FOR EACH ROW
BEGIN
    INSERT INTO audit_log (action, table_name, record_id, performed_by)
    VALUES ('USER_DELETED', 'users', OLD.user_id, NULL);
END //
-- Trigger: Notify NGO when a volunteer accepts the delivery
CREATE TRIGGER after_delivery_accept
AFTER UPDATE ON deliveries
FOR EACH ROW
BEGIN
    DECLARE ngo_to_notify INT;
    
    IF NEW.status = 'ACCEPTED' AND OLD.status = 'PENDING' THEN
        -- Find NGO from claim
        SELECT ngo_id INTO ngo_to_notify FROM food_claims WHERE claim_id = NEW.claim_id;
        
        INSERT INTO notifications (user_id, message)
        VALUES (ngo_to_notify, CONCAT('A volunteer has accepted delivery ID: ', NEW.delivery_id));
    END IF;
END //
DELIMITER ;