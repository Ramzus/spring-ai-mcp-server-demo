-- Schema for Order Management Application
-- This script creates the database schema for managing customer orders

-- Drop tables if they exist (for development/testing purposes)
DROP TABLE IF EXISTS orders;

-- Create orders table
CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(255) NOT NULL,
    order_date DATE NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    number_of_items INTEGER NOT NULL,
    status VARCHAR(50) NOT NULL,
    CONSTRAINT chk_status CHECK (status IN ('CREATED', 'PENDING', 'SHIPPED', 'DELIVERED', 'CANCELLED', 'FINISHED')),
    CONSTRAINT chk_total_amount CHECK (total_amount >= 0),
    CONSTRAINT chk_number_of_items CHECK (number_of_items > 0)
);

-- Create indexes for better performance
CREATE INDEX idx_orders_status ON orders(status);
CREATE INDEX idx_orders_customer_name ON orders(customer_name);
CREATE INDEX idx_orders_order_date ON orders(order_date);

-- Insert sample data for development/testing
INSERT INTO orders (id, customer_name, order_date, total_amount, status, number_of_items) VALUES
    (100, 'John Doe', '2023-10-01', 100.00, 'PENDING', 3),
    (102, 'Alicia Date', '2023-10-02', 200.00, 'DELIVERED', 5),
    (103, 'Eric Dupont', '2023-10-03', 300.00, 'SHIPPED', 2),
    (104, 'Alain Provist', '2023-10-04', 300.00, 'CREATED', 4);
