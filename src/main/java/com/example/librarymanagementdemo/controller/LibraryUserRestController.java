package com.example.librarymanagementdemo.controller;


import com.example.librarymanagementdemo.entity.LibraryBranch;
import com.example.librarymanagementdemo.entity.LibraryUser;
import com.example.librarymanagementdemo.service.LibraryBranchService;
import com.example.librarymanagementdemo.service.LibraryUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libraryusers")
public class LibraryUserRestController {

    private LibraryUserService libraryUserService;

    @Autowired
    public LibraryUserRestController(LibraryUserService libraryUserService) {
        this.libraryUserService = libraryUserService;
    }

    @GetMapping
    public List<LibraryUser> findAll(){

        System.out.println("\nWill return all users in db.");

        return libraryUserService.findAll();
    }

    @GetMapping("/{libraryUserId}")
    public LibraryUser getLibraryUser(@PathVariable int libraryUserId){

        System.out.println("\nWill try to return library branch with id: " + libraryUserId);

        LibraryUser libraryUser = libraryUserService.findById(libraryUserId);

        if(libraryUser == null){
            throw new RuntimeException("Couldn't find library user with id: " + libraryUserId);
        }

        System.out.println("\nLibrary user with id " + libraryUserId + " is found.");

        return libraryUser;

    }

    @PostMapping
    public LibraryUser addLibraryUser(@RequestBody LibraryUser libraryUser) {

        //for debug purposes
        System.out.println("\nWill add a library user to the database.");

        libraryUser.setId(0);

        LibraryUser userInDB = libraryUserService.save(libraryUser);

        System.out.println("Saved library user: " + userInDB);

        return userInDB;
    }

    @PutMapping
    public LibraryUser updateLibraryUser(@RequestBody LibraryUser libraryUser) {

        System.out.println("\nWill try to update a library user from database.");

        LibraryUser userInDB = libraryUserService.save(libraryUser);

        System.out.println("Updated library user: " + userInDB);

        //uniqueness of username and email might need to be considered here

        return userInDB;
    }

    @DeleteMapping("/{libraryUserId}")
    public String deleteLibraryUser(@PathVariable int libraryUserId) {

        LibraryUser tempLibraryUser = libraryUserService.findById(libraryUserId);

        if (tempLibraryUser == null) {
            throw new RuntimeException("Deletion failed. could not found a library user with id: " + libraryUserId);
        }

        libraryUserService.deleteById(libraryUserId);

        System.out.println("\nLibrary user with id " + libraryUserId + " is deleted.");

        return "Deleted library user id: " + libraryUserId;
    }
}
