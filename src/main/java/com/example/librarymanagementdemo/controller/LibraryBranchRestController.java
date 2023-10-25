package com.example.librarymanagementdemo.controller;
import com.example.librarymanagementdemo.entity.LibraryBranch;
import com.example.librarymanagementdemo.service.LibraryBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/librarybranches") //updated from /api
public class LibraryBranchRestController {

    private LibraryBranchService libraryBranchService;

    @Autowired
    public LibraryBranchRestController(LibraryBranchService libraryBranchService) {
        this.libraryBranchService = libraryBranchService;
    }

    @GetMapping
    public List<LibraryBranch> findAll() {

        System.out.println("\nWill return all branches in db.");

        return libraryBranchService.findAll();
    }

    @GetMapping("/{libraryBranchId}")
    public LibraryBranch getLibraryBranch(@PathVariable int libraryBranchId) {

        System.out.println("\nWill try to return library branch with id: " + libraryBranchId);

        LibraryBranch libraryBranch = libraryBranchService.findById(libraryBranchId);

        if (libraryBranch == null) {
            throw new RuntimeException("Couldn't find library branch with id: " + libraryBranchId);
        }

        System.out.println("\nLibrary branch with id " + libraryBranchId + " is found.");

        return libraryBranch;
    }

    @PostMapping
    public LibraryBranch addLibraryBranch(@RequestBody LibraryBranch libraryBranch) {

        //for debug purposes
        System.out.println("\nWill add a library branch to the database.");

        libraryBranch.setId(0); //to force a save of new item

        LibraryBranch branchInDB = libraryBranchService.save(libraryBranch);

        System.out.println("Saved library branch: " + branchInDB);

        return branchInDB;
    }

    //addLibraryBranches might be added

    @PutMapping
    public LibraryBranch updateLibraryBranch(@RequestBody LibraryBranch libraryBranch) {

        System.out.println("\nWill try to update a library branch from database.");

        LibraryBranch branchInDB = libraryBranchService.save(libraryBranch);

        System.out.println("Updated library branch: " + branchInDB);

        return branchInDB;
    }

    @DeleteMapping("/{libraryBranchId}")
    public String deleteLibraryBranch(@PathVariable int libraryBranchId) {

        LibraryBranch tempLibraryBranch = libraryBranchService.findById(libraryBranchId);

        if (tempLibraryBranch == null) {
            throw new RuntimeException("Deletion failed. could not found a library branch with id: " + libraryBranchId);
        }

        libraryBranchService.deleteById(libraryBranchId);

        System.out.println("\nLibrary branch with id " + libraryBranchId + " is deleted.");

        return "Deleted library branch id: " + libraryBranchId;
    }

    //will add /books, /books/id, get queriy handling example /librarybranches, etc

}
