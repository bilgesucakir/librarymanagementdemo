DROP SCHEMA IF EXISTS `library_management`;

CREATE SCHEMA `library_management`;

USE `library_management`;

SET FOREIGN_KEY_CHECKS = 0;

-- Create the 'Author' table
CREATE TABLE `author` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    `biography` TEXT,
    `birthdate` DATE,
    `nationality` VARCHAR(50)
);

-- Create the 'Book' table
CREATE TABLE `book` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `title` VARCHAR(255) NOT NULL,
    `isbn` VARCHAR(13) NOT NULL,
    `publicationYear` INT,
    `genre` VARCHAR(50),
    `availabilityStatus` BOOLEAN,
    `author_name` VARCHAR(255),
    `multiple_author` BOOLEAN
);

-- Create the 'LibraryBranch' table
CREATE TABLE `librarybranch` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    `location` VARCHAR(255),
    `capacity` INT
);

-- Create a join table for the many-to-many relationship between Book and LibraryBranch
CREATE TABLE `book_location` (
    `book_id` INT,
    `branch_id` INT,
    PRIMARY KEY (`book_id`, `branch_id`),
    FOREIGN KEY (`book_id`) REFERENCES `book` (`id`),
    FOREIGN KEY (`branch_id`) REFERENCES `librarybranch` (`id`)
);

CREATE TABLE `book_author` (
    `book_id` INT,
    `author_id` INT,
    PRIMARY KEY (`book_id`, `author_id`),
    FOREIGN KEY (`book_id`) REFERENCES `book` (`id`),
    FOREIGN KEY (`author_id`) REFERENCES `author` (`id`)
);

-- Create the 'Checkout' table
CREATE TABLE `checkout` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `checked_out_date` DATE,
    `due_date` DATE,
    `book_id` INT,
    `user_id` INT,
    FOREIGN KEY (`book_id`) REFERENCES `book` (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

-- Create the 'User' table
CREATE TABLE `user` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `username` VARCHAR(50) NOT NULL,
    `email` VARCHAR(100),
    `password` VARCHAR(60) -- (for bcrypt)
);

SET FOREIGN_KEY_CHECKS = 1;
