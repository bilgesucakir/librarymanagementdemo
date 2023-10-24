package com.example.librarymanagementdemo.repository;

import com.example.librarymanagementdemo.entity.Book;
import com.example.librarymanagementdemo.entity.LibraryBranch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibraryBranchRepository extends JpaRepository<LibraryBranch, Integer> {

    List<LibraryBranch> findByBooksContaining(Book book);
}
