-- Sample data for Order Management Application
-- This script inserts test data for development/testing

-- Insert sample orders
INSERT INTO orders (customer_name, order_date, total_amount, number_of_items, status) VALUES
('John Doe', '2023-10-01', 100.00, 3, 'PENDING'),
('Alicia Date', '2023-10-02', 200.00, 5, 'DELIVERED'),
('Eric Dupont', '2023-10-03', 300.00, 2, 'SHIPPED'),
('Alain Provist', '2023-10-04', 300.00, 4, 'CREATED');
