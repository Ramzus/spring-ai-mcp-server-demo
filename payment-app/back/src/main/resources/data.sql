-- Payment data that matches the orders in business-app
INSERT INTO payment (id, order_id, customer_name, payment_date, total_amount, status) VALUES
(1, 100, 'John Doe', '2023-10-01', 100.00, 'COMPLETED'),
(2, 102, 'Alicia Date', '2023-10-02', 200.00, 'COMPLETED'),
(3, 103, 'Eric Dupont', '2023-10-03', 300.00, 'PENDING'),
(4, 104, 'Alain Provist', '2023-10-04', 300.00, 'FAILED');

