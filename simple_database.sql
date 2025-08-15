-- Simple Exam Seating Database Schema
CREATE DATABASE exam_seating;
USE exam_seating;

-- Students table
CREATE TABLE students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    roll_number VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    department VARCHAR(20) NOT NULL
);

-- Halls table  
CREATE TABLE halls (
    id INT PRIMARY KEY AUTO_INCREMENT,
    hall_name VARCHAR(50) NOT NULL,
    total_seats INT NOT NULL
);

-- Seating arrangements table
CREATE TABLE seating (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    hall_id INT NOT NULL,
    seat_number INT NOT NULL,
    FOREIGN KEY (student_id) REFERENCES students(id),
    FOREIGN KEY (hall_id) REFERENCES halls(id)
);

-- Sample data
INSERT INTO students (roll_number, name, department) VALUES
('2022CSE001', 'Arjun Kumar', 'CSE'),
('2022CSE002', 'Priya Sharma', 'CSE'),
('2022CSE003', 'Rohit Gupta', 'CSE'),
('2022ECE001', 'Rahul Verma', 'ECE'),
('2022ECE002', 'Sneha Patel', 'ECE'),
('2022ECE003', 'Amit Singh', 'ECE'),
('2022MECH001', 'Vikram Singh', 'MECH'),
('2022MECH002', 'Anjali Gupta', 'MECH'),
('2022CIVIL001', 'Karthik Raj', 'CIVIL'),
('2022CIVIL002', 'Deepika Nair', 'CIVIL');

INSERT INTO halls (hall_name, total_seats) VALUES
('Hall A', 15),
('Hall B', 10);