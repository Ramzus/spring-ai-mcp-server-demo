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

-- Insert sample data for development/testing
INSERT INTO payment (order_id, customer_name, payment_date, total_amount, photo_url, status) VALUES
(100, 'John Doe', '2024-01-15', 150.50, 'https://example.com/photo1.jpg', 'COMPLETED'),
(101, 'Jane Smith', '2024-01-16', 89.99, 'https://example.com/photo2.jpg', 'COMPLETED'),
(102, 'Bob Johnson', '2024-01-17', 299.99, 'https://example.com/photo3.jpg', 'COMPLETED'),
(103, 'Alice Brown', '2024-01-18', 45.25, 'https://example.com/photo4.jpg', 'PENDING'),
(104, 'Charlie Davis', '2024-01-19', 199.99, 'https://example.com/photo5.jpg', 'FAILED'),
(105, 'Eva Wilson', '2024-01-20', 75.00, 'https://example.com/photo6.jpg', 'COMPLETED');
