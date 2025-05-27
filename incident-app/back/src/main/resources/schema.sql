-- Schema for Incident Management Application
-- This script creates the database schema for managing incidents

-- Drop tables if they exist (for development/testing purposes)
DROP TABLE IF EXISTS incidents;

-- Create incidents table
CREATE TABLE incidents (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    severity VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    reporter_name VARCHAR(100) NOT NULL,
    assigned_to VARCHAR(100),
    resolution VARCHAR(1000),
    created_date DATE NOT NULL,
    updated_date DATE NOT NULL,
    CONSTRAINT chk_incident_severity CHECK (severity IN ('LOW', 'MEDIUM', 'HIGH', 'CRITICAL')),
    CONSTRAINT chk_incident_status CHECK (status IN ('OPEN', 'IN_PROGRESS', 'RESOLVED', 'CLOSED', 'CANCELLED'))
);

-- Create indexes for better performance
CREATE INDEX idx_incidents_status ON incidents(status);
CREATE INDEX idx_incidents_severity ON incidents(severity);
CREATE INDEX idx_incidents_assigned_to ON incidents(assigned_to);
CREATE INDEX idx_incidents_reporter_name ON incidents(reporter_name);
CREATE INDEX idx_incidents_created_date ON incidents(created_date);
