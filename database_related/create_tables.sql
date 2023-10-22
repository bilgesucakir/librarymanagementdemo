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
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

-- Create the 'Book' table
CREATE TABLE `book` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `title` VARCHAR(255) NOT NULL,
    `isbn` VARCHAR(13) NOT NULL,
    `publication_year` INT,
    `genre` VARCHAR(50),
    `availability_status` BOOLEAN NOT NULL,
    `multiple_author` BOOLEAN,
    `library_branch_id` INT NOT NULL,
    CONSTRAINT `constraint5` FOREIGN KEY (`library_branch_id`) REFERENCES `librarybranch` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

-- Create the 'LibraryBranch' table
CREATE TABLE `librarybranch` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    `location` VARCHAR(255),
    `capacity` INT
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

-- Create a join table for the many-to-many relationship between Book and LibraryBranch
/*CREATE TABLE `book_location` (
    `book_id` INT PRIMARY KEY,
    `branch_id` INT,
    CONSTRAINT `constraint3` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `constraint4` FOREIGN KEY (`branch_id`) REFERENCES `librarybranch` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);*/

CREATE TABLE `book_author` (
    `book_id` INT,
    `author_id` INT,
    PRIMARY KEY (`book_id`, `author_id`),
    CONSTRAINT 	`constraint1` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `constraint2` FOREIGN KEY (`author_id`) REFERENCES `author` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- Create the 'Checkout' table
CREATE TABLE `checkout` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `checked_out_date` DATE,
    `due_date` DATE,
    `book_id` INT,
    `user_id` INT,
    CONSTRAINT `constraint3` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `constraint4` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

-- Create the 'User' table
CREATE TABLE `user` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `username` VARCHAR(50) NOT NULL,
    `email` VARCHAR(100),
    `password` VARCHAR(60) -- (for bcrypt)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;
