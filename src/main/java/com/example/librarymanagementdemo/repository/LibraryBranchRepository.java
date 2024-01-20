package com.example.librarymanagementdemo.repository;

import com.example.librarymanagementdemo.entity.Book;
import com.example.librarymanagementdemo.entity.LibraryBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibraryBranchRepository extends JpaRepository<LibraryBranch, Integer> {

    @Query("SELECT lb FROM LibraryBranch lb " +
            "WHERE (:name is null or lb.name = :name) " +
            "AND (:location is null or lb.location = :location) " +
            "AND (:capacity is null or lb.capacity = :capacity)")
    List<LibraryBranch> findByNameAndLocationAndCapacity(String name, String location, Integer capacity);

}
