-- Schema for Payment Management Application
-- This script creates the database schema for managing payments

-- Drop tables if they exist (for development/testing purposes)
DROP TABLE IF EXISTS payment;

-- Create payment table
CREATE TABLE payment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    customer_name VARCHAR(255) NOT NULL,
    payment_date DATE NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    photo_url VARCHAR(500),
    status VARCHAR(50) NOT NULL,
    CONSTRAINT chk_payment_status CHECK (status IN ('PENDING', 'COMPLETED', 'FAILED', 'CANCELLED')),
    CONSTRAINT chk_payment_total_amount CHECK (total_amount >= 0)
);

-- Create indexes for better performance
CREATE INDEX idx_payment_order_id ON payment(order_id);
CREATE INDEX idx_payment_status ON payment(status);
CREATE INDEX idx_payment_customer_name ON payment(customer_name);
CREATE INDEX idx_payment_date ON payment(payment_date);
