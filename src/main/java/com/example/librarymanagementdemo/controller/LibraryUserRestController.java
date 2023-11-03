package com.example.librarymanagementdemo.controller;


import com.example.librarymanagementdemo.dto.CheckoutDTO;
import com.example.librarymanagementdemo.dto.LibraryUserDTO;
import com.example.librarymanagementdemo.entity.Book;
import com.example.librarymanagementdemo.entity.Checkout;
import com.example.librarymanagementdemo.entity.LibraryBranch;
import com.example.librarymanagementdemo.entity.LibraryUser;
import com.example.librarymanagementdemo.service.CheckoutService;
import com.example.librarymanagementdemo.service.LibraryBranchService;
import com.example.librarymanagementdemo.service.LibraryUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/libraryusers")
public class LibraryUserRestController {

    private LibraryUserService libraryUserService;

    private CheckoutService checkoutService;

    @Autowired
    public LibraryUserRestController(LibraryUserService libraryUserService, CheckoutService checkoutService) {
        this.libraryUserService = libraryUserService;
        this.checkoutService = checkoutService;
    }

    @GetMapping
    public List<LibraryUserDTO> findAll(){

        List<LibraryUser> libraryUsers = new ArrayList<>();

        System.out.println("\nWill return all users in db.");

        libraryUsers =  libraryUserService.findAll();

        List<LibraryUserDTO> libraryUserDTOs = new ArrayList<>();

        for(LibraryUser libraryUser : libraryUsers){
            LibraryUserDTO libraryUserDTO  = libraryUserService.convertLibraryUserEntityTolibraryUserDTO(libraryUser);

            libraryUserDTOs.add(libraryUserDTO);
        }

        return libraryUserDTOs;
    }

    @GetMapping("/{libraryUserId}")
    public LibraryUserDTO getLibraryUser(@PathVariable int libraryUserId){

        System.out.println("\nWill try to return library branch with id: " + libraryUserId);

        LibraryUser libraryUser = libraryUserService.findById(libraryUserId);

        if(libraryUser == null){
            throw new RuntimeException("Couldn't find library user with id: " + libraryUserId);
        }

        System.out.println("\nLibrary user with id " + libraryUserId + " is found.");


        LibraryUserDTO libraryUserDTO = libraryUserService.convertLibraryUserEntityTolibraryUserDTO(libraryUser);
        return libraryUserDTO;

    }

    @GetMapping("/{libraryUserId}/checkouts")
    public List<CheckoutDTO> getCheckoutOfLibraryUser(@PathVariable int libraryUserId){
        System.out.println("\nWill try to find library user with id " + libraryUserId + " to return its checkouts.");

        LibraryUser libraryUser = libraryUserService.findById(libraryUserId);

        if(libraryUser == null){
                throw new RuntimeException("Cannot return checkouts. Couldn't find library user with id: " + libraryUserId);
        }
        else{

            System.out.println("\nWill return all checkouts of library user with id " + libraryUserId);

            List<CheckoutDTO> checkoutDTOs = new ArrayList<>();
            List<Checkout> checkouts = checkoutService.findByLibraryUser(libraryUser);
            for(Checkout checkout : checkouts){
                CheckoutDTO checkoutDTO = checkoutService.convertCheckoutEntityToCheckoutDTO(checkout);
                checkoutDTOs.add(checkoutDTO);
            }

            return checkoutDTOs;
        }

    }

    @PostMapping
    public LibraryUserDTO addLibraryUser(@RequestBody LibraryUserDTO libraryUserDTO) {

        //for debug purposes
        System.out.println("\nWill add a library user to the database.");

        libraryUserDTO.setId(0);

        LibraryUser libraryUser = libraryUserService.convertLibraryUserDTOToLibraryUserEntity(libraryUserDTO);

        LibraryUser userInDB = libraryUserService.save(libraryUser);

        System.out.println("Saved library user: " + userInDB);

        LibraryUserDTO returnLibraryUserDTO = libraryUserService.convertLibraryUserEntityTolibraryUserDTO(userInDB);

        return returnLibraryUserDTO;
    }

    @PutMapping //not finished, will be updated according to dto
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
