DROP DATABASE IF EXISTS client_schedule;
-- 1. Create and Use the Database
CREATE DATABASE IF NOT EXISTS client_schedule;
USE client_schedule;

-- 2. Create Tables (Order matters due to Foreign Keys)
CREATE TABLE countries (
                           Country_ID INT AUTO_INCREMENT PRIMARY KEY,
                           Country VARCHAR(50)
);

CREATE TABLE first_level_divisions (
                                       Division_ID INT AUTO_INCREMENT PRIMARY KEY,
                                       Division VARCHAR(50),
                                       Country_ID INT,
                                       FOREIGN KEY (Country_ID) REFERENCES countries(Country_ID)
);

CREATE TABLE users (
                       User_ID INT AUTO_INCREMENT PRIMARY KEY,
                       User_Name VARCHAR(50),
                       Password VARCHAR(50)
);

CREATE TABLE contacts (
                          Contact_ID INT AUTO_INCREMENT PRIMARY KEY,
                          Contact_Name VARCHAR(50),
                          Email VARCHAR(50)
);

CREATE TABLE customers (
                           Customer_ID INT AUTO_INCREMENT PRIMARY KEY,
                           Customer_Name VARCHAR(50),
                           Address VARCHAR(100),
                           Postal_Code VARCHAR(50),
                           Phone VARCHAR(50),
                           Division_ID INT,
                           FOREIGN KEY (Division_ID) REFERENCES first_level_divisions(Division_ID)
);

CREATE TABLE appointments (
                              Appointment_ID INT AUTO_INCREMENT PRIMARY KEY,
                              Title VARCHAR(50),
                              Description VARCHAR(50),
                              Location VARCHAR(50),
                              Type VARCHAR(50),
                              Start DATETIME,
                              End DATETIME,
                              Customer_ID INT,
                              User_ID INT,
                              Contact_ID INT,
                              FOREIGN KEY (Customer_ID) REFERENCES customers(Customer_ID),
                              FOREIGN KEY (User_ID) REFERENCES users(User_ID),
                              FOREIGN KEY (Contact_ID) REFERENCES contacts(Contact_ID)
);

-- 3. Populate Dummy Data

-- Users (Login with: test / test)
INSERT INTO users (User_Name, Password) VALUES ('test', 'test');
INSERT INTO users (User_Name, Password) VALUES ('admin', 'admin');

-- Contacts
INSERT INTO contacts (Contact_Name, Email) VALUES ('Anika Costa', 'anika@test.com');
INSERT INTO contacts (Contact_Name, Email) VALUES ('Daniel Garcia', 'daniel@test.com');
INSERT INTO contacts (Contact_Name, Email) VALUES ('Li Lee', 'li@test.com');

-- Countries & Divisions
INSERT INTO countries (Country) VALUES ('U.S'), ('UK'), ('Canada');
INSERT INTO first_level_divisions (Division, Country_ID) VALUES ('New York', 1), ('Arizona', 1), ('London', 2), ('Quebec', 3);

-- Customers
INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES
                                                                                    ('Daddy Warbucks', '123 Rich St', '10019', '555-1234', 1),
                                                                                    ('Sherlock Holmes', '221B Baker St', 'NW1 6XE', '555-4321', 3);

-- Appointments
INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES
                                                                                                                ('Strategy Meeting', 'Discuss Q4', 'New York', 'Planning', NOW(), DATE_ADD(NOW(), INTERVAL 1 HOUR), 1, 1, 1),
                                                                                                                ('Debrief', 'Case Closed', 'London', 'De-Briefing', DATE_ADD(NOW(), INTERVAL 1 DAY), DATE_ADD(NOW(), INTERVAL 25 HOUR), 2, 1, 2);