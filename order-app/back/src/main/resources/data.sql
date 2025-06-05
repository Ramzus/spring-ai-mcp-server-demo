-- Sample data for Order Management Application
-- This script inserts test data for development/testing

-- Insert sample orders
INSERT INTO orders (customer_name, order_date, total_amount, number_of_items, status) VALUES
('John Doe', '2024-10-01', 100.00, 3, 'PENDING'),
('Alicia Date', '2025-03-04', 200.00, 5, 'DELIVERED'),
('Eric Dupont', '2025-02-03', 300.00, 2, 'SHIPPED'),
('Alain Provist', '2025-01-04', 300.00, 4, 'CREATED'),
('Jean Bonbeur', '2025-05-04', 950.00, 10, 'CREATED');
