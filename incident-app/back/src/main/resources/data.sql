-- Sample data for Incident Management Application
-- This script inserts test data for development/testing

-- Insert sample incidents
INSERT INTO incidents (title, description, severity, status, reporter_name, assigned_to, resolution, created_date, updated_date) VALUES
('Order not displayed', 'Customer have create a new order but its not displayed on order UI', 'HIGH', 'RESOLVED', 'John Doe', 'N3 OMS', 'Order at CREATED status are not displayed in frontend. Payment linked to order need to be completed in order to shift order to PENDING status', '2023-10-01', '2023-10-01'),
('System Performance Issue', 'The application is running slowly during peak hours', 'HIGH', 'IN_PROGRESS', 'Jane Smith', 'N3 OMS', NULL, '2023-10-02', '2023-10-03'),
('Cannot move forward an order', 'When I click on move forward button, order is not move forward and I don''t know why', 'MEDIUM', 'RESOLVED', 'Alice Brown', 'N3 Payment', 'Payment status was not updated as we never received payment ackowledge from customer bank; After verification, we force payment status to COMPLETED in order to unblock the move forward function on this order', '2023-10-04', '2023-10-05'),
('Database Connection Timeout', 'Intermittent database connection issues', 'MEDIUM', 'RESOLVED', 'Alice Brown', 'Database Team', 'Increased connection pool size', '2023-10-04', '2023-10-05'),
('UI Display Bug', 'Buttons are not aligned properly on mobile devices', 'LOW', 'CLOSED', 'Charlie Davis', 'N3 OMS', 'Fixed CSS styling issues', '2023-10-05', '2023-10-06');
