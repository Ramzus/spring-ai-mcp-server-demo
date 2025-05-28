-- Sample data for Payment Management Application
-- This script inserts test data for development/testing

-- Insert sample payments
INSERT INTO payment (order_id, customer_name, payment_date, total_amount, photo_url, status) VALUES
(1, 'John Doe', '2023-10-01', 100.00, 'https://example.com/photo1.jpg', 'COMPLETED'),
(2, 'Alicia Date', '2023-10-02', 200.00, 'https://example.com/photo2.jpg', 'COMPLETED'),
(3, 'Eric Dupont', '2023-10-03', 300.00, 'https://example.com/photo3.jpg', 'PENDING'),
(4, 'Alain Provist', '2023-10-04', 300.00, 'https://example.com/photo4.jpg', 'FAILED');
