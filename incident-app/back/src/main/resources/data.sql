-- Sample incident data
INSERT INTO incidents (id, title, description, severity, status, reporter_name, assigned_to, resolution, created_date, updated_date) VALUES
(1, 'Server Performance Issue', 'Application server running slowly, response times over 5 seconds', 'HIGH', 'OPEN', 'John Doe', 'DevOps Team', NULL, '2023-10-01', '2023-10-01'),
(2, 'Login Authentication Problem', 'Users unable to login to the system', 'CRITICAL', 'IN_PROGRESS', 'Alice Johnson', 'Security Team', 'Investigating root cause', '2023-10-02', '2023-10-02'),
(3, 'Database Connection Timeout', 'Intermittent database connection failures', 'MEDIUM', 'RESOLVED', 'Bob Smith', 'Database Team', 'Increased connection pool size and optimized queries', '2023-10-03', '2023-10-05'),
(4, 'Email Service Down', 'Email notifications not being sent', 'HIGH', 'OPEN', 'Mary Wilson', 'Infrastructure Team', NULL, '2023-10-04', '2023-10-04'),
(5, 'UI Display Bug', 'Buttons not displaying correctly on mobile devices', 'LOW', 'CLOSED', 'David Brown', 'Frontend Team', 'Fixed CSS media queries for mobile responsiveness', '2023-09-28', '2023-10-01');
