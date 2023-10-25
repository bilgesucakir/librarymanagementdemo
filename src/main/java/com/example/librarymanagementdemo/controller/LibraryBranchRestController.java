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


    @PostMapping("/librarybranches")
    public LibraryBranch addLibraryBranch(@RequestBody LibraryBranch libraryBranch) {

        //for debug purposes
        System.out.println("\n Will add a library branch do the database.");

        // also just in case they pass an id in JSON ... set id to 0
        // this is to force a save of new item ... instead of update

        libraryBranch.setId(0);

        LibraryBranch branchInDB = libraryBranchService.save(libraryBranch);

        System.out.println("Saved library branch: " + branchInDB);

        return branchInDB;
    }

}
