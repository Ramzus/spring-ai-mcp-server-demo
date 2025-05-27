-- Sample data for Incident Management Application
-- This script inserts test data for development/testing

-- Insert sample incidents
INSERT INTO incidents (title, description, severity, status, reporter_name, assigned_to, resolution, created_date, updated_date) VALUES
('Order not displayed', 'Customer have create a new order but its not displayed on order UI', 'HIGH', 'OPEN', 'John Doe', 'DevOps Team', NULL, '2023-10-01', '2023-10-01'),
('System Performance Issue', 'The application is running slowly during peak hours', 'HIGH', 'IN_PROGRESS', 'Jane Smith', 'Tech Team Alpha', NULL, '2023-10-02', '2023-10-03'),
('Login Page Error', 'Users cannot log in with valid credentials', 'CRITICAL', 'OPEN', 'Bob Johnson', 'Security Team', NULL, '2023-10-03', '2023-10-03'),
('Database Connection Timeout', 'Intermittent database connection issues', 'MEDIUM', 'RESOLVED', 'Alice Brown', 'Database Team', 'Increased connection pool size', '2023-10-04', '2023-10-05'),
('UI Display Bug', 'Buttons are not aligned properly on mobile devices', 'LOW', 'CLOSED', 'Charlie Davis', 'Frontend Team', 'Fixed CSS styling issues', '2023-10-05', '2023-10-06');
