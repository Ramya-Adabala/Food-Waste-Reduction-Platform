-- Represents a history of critical actions for auditing purposes
CREATE TABLE audit_log (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    action VARCHAR(255) NOT NULL,
    table_name VARCHAR(50) NOT NULL,
    record_id INT NOT NULL,
    performed_by INT,
    action_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);