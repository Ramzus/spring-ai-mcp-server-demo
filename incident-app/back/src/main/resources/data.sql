-- Sample incident data
INSERT INTO incidents (id, title, description, severity, status, reporter_name, assigned_to, resolution, created_date, updated_date) VALUES
(1, 'Order not displayed', 'Customer have create a new order but its not displayed on order UI', 'HIGH', 'OPEN', 'John Doe', 'DevOps Team', 'Order is on CREATED status as payment not existing for this order. Payment was created and validated in order move forward it', '2023-10-01', '2023-10-01');
