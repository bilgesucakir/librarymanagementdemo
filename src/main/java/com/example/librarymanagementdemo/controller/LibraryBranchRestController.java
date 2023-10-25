package com.example.librarymanagementdemo.controller;
import com.example.librarymanagementdemo.entity.LibraryBranch;
import com.example.librarymanagementdemo.service.LibraryBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class LibraryBranchRestController {

    private LibraryBranchService libraryBranchService;

    @Autowired
    public LibraryBranchRestController(LibraryBranchService libraryBranchService) {
        this.libraryBranchService = libraryBranchService;
    }

    @GetMapping("/librarybranches")
    public List<LibraryBranch> findAll() {

        System.out.println("\nWill print all branches in db.");

        return libraryBranchService.findAll();
    }

    @GetMapping("/librarybranches/{libraryBranchId}")
    public LibraryBranch getLibraryBranch(@PathVariable int libraryBranchId) {

        LibraryBranch libraryBranch = libraryBranchService.findById(libraryBranchId);

        if (libraryBranch == null) {
            throw new RuntimeException("Couldn't find library branch with id: " + libraryBranchId);
        }

        return libraryBranch;
    }

    @PostMapping("/librarybranches")
    public LibraryBranch addLibraryBranch(@RequestBody LibraryBranch libraryBranch) {

        //for debug purposes
        System.out.println("\nWill add a library branch do the database.");

        // also just in case they pass an id in JSON ... set id to 0
        // this is to force a save of new item ... instead of update

        libraryBranch.setId(0);

        LibraryBranch branchInDB = libraryBranchService.save(libraryBranch);

        System.out.println("Saved library branch: " + branchInDB);

        return branchInDB;
    }

    @PutMapping("/librarybranches")
    public LibraryBranch updateLibraryBranch(@RequestBody LibraryBranch libraryBranch) {

        System.out.println("\nWill try to update a library branch from database.");

        LibraryBranch branchInDB = libraryBranchService.save(libraryBranch);

        //no existing case not added yet.

        System.out.println("Updated library branch: " + branchInDB);

        return branchInDB;
    }

    @DeleteMapping("/librarybranches/{libraryBranchId}")
    public String deleteLibraryBranch(@PathVariable int libraryBranchId) {

        LibraryBranch tempLibraryBranch = libraryBranchService.findById(libraryBranchId);

        if (tempLibraryBranch == null) {
            throw new RuntimeException("Deletion failed. could not found a library branch with id: " + libraryBranchId);
        }

        libraryBranchService.deleteById(libraryBranchId);

        System.out.println("\nLibrary branch with id " + libraryBranchId + " is deleted.");

        return "Deleted library branch id: " + libraryBranchId;
    }


}
