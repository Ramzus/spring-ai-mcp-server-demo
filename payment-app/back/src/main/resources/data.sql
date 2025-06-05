-- Sample data for Payment Management Application
-- This script inserts test data for development/testing

-- Insert sample payments
INSERT INTO payment (order_id, customer_name, payment_date, total_amount, status)
VALUES (1, 'John Doe', '2024-10-01', 100.00, 'COMPLETED'),
       (2, 'Alicia Date', '2025-03-04', 200.00, 'COMPLETED'),
       (3, 'Eric Dupont', '2025-02-03', 300.00, 'COMPLETED'),
       (4, 'Alain Provist', '2025-01-04', 300.00, 'COMPLETED'),
       (5, 'Jean Bonbeur', '2025-05-04', 950.00, 'FAILED');
