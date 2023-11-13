package com.example.librarymanagementdemo.controller;


import com.example.librarymanagementdemo.dto.CheckoutDTO;
import com.example.librarymanagementdemo.dto.LibraryUserDTO;
import com.example.librarymanagementdemo.entity.Checkout;
import com.example.librarymanagementdemo.entity.LibraryUser;
import com.example.librarymanagementdemo.service.CheckoutService;
import com.example.librarymanagementdemo.service.LibraryUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        System.out.println("\nWill return all users in db.");

        List<LibraryUserDTO> libraryUserDTOs = libraryUserService.findAll().stream()
                .map(libraryUserService::convertLibraryUserEntityTolibraryUserDTO)
                .collect(Collectors.toList());

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

            List<CheckoutDTO> checkoutDTOs = checkoutService.findByLibraryUser(libraryUser).stream()
                    .map(checkoutService::convertCheckoutEntityToCheckoutDTO)
                    .collect(Collectors.toList());

            return checkoutDTOs;
        }
    }

    @PostMapping
    public LibraryUserDTO addLibraryUser(@RequestBody LibraryUserDTO libraryUserDTO) {

        System.out.println("\nWill add a library user to the database.");

        //username and email checks
        if(libraryUserDTO.getUsername() == null){
            throw new RuntimeException("Username field of libraryuser cannot be empty.");
        }
        else{
            String usernameInput = libraryUserDTO.getUsername();

            if(libraryUserService.libraryUserExistsWithUsernameOrNot(usernameInput)){
                throw new RuntimeException("Cannot add libaryuser. Given username \""
                        + usernameInput + "\" already exists in database.");
            }
            else{
                if(usernameInput.matches(".*\\s.*")){
                    throw new RuntimeException("Cannot add libraryuser. Given username \""
                            + usernameInput + "\" contains at least one whitespace character.");
                }
            }
        }

        if(libraryUserDTO.getEmail() == null){
            throw new RuntimeException("Email field of libraryuser cannot be empty.");
        }
        else{
            String emailInput = libraryUserDTO.getEmail();

            if(libraryUserService.libraryUserExistsWithEmailOrNot(emailInput)){
                throw new RuntimeException("Cannot add libaryuser. Given email \""
                        + emailInput + "\" already exists in database.");
            }
            else{
                if(!emailInput.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")){
                    throw new RuntimeException("Cannot add libraryuser. Given email \""
                            + emailInput + "\" is not in correct format.");
                }
            }
        }

        libraryUserDTO.setId(0);

        LibraryUser libraryUser = libraryUserService.convertLibraryUserDTOToLibraryUserEntity(libraryUserDTO);
        LibraryUser userInDB = libraryUserService.save(libraryUser); //is it empty list?

        System.out.println("Saved library user: " + userInDB);

        LibraryUserDTO returnLibraryUserDTO = libraryUserService.convertLibraryUserEntityTolibraryUserDTO(userInDB);
        return returnLibraryUserDTO;
    }

    @PutMapping
    public LibraryUserDTO updateLibraryUser(@RequestBody LibraryUserDTO libraryUserDTO) {

        System.out.println("\nWill try to update a library user from database.");

        LibraryUser libraryUser = new LibraryUser();

        if(libraryUserDTO.getId() != 0){
            libraryUser = libraryUserService.findById(libraryUserDTO.getId());

            if(libraryUser == null){
                throw new RuntimeException("Cannot update libraryuser. No libraryuser exists with given id: " + libraryUserDTO.getId());
            }

            if(libraryUserDTO.getUsername() != null && !libraryUserDTO.getUsername().equals(libraryUser.getUsername()) ){ //username change
                String usernameInput = libraryUserDTO.getUsername();

                System.out.println("should not be here");


                if(libraryUserService.libraryUserExistsWithUsernameOrNot(usernameInput)){
                    throw new RuntimeException("Cannot add libaryuser. Given username \""
                            + usernameInput + "\" already exists in database.");
                }
                else{
                    if(usernameInput.matches(".*\\s.*")){
                        throw new RuntimeException("Cannot add libraryuser. Given username \""
                                + usernameInput + "\" contains at least one whitespace character.");
                    }
                }
            }

            if(libraryUserDTO.getEmail() != null && !libraryUserDTO.getEmail().equals(libraryUser.getEmail())){ //email change
                String emailInput = libraryUserDTO.getEmail();

                if(libraryUserService.libraryUserExistsWithEmailOrNot(emailInput)){
                    throw new RuntimeException("Cannot add libaryuser. Given email \""
                            + emailInput + "\" already exists in database.");
                }
                else{
                    System.out.println("\""+ emailInput + "\"");
                    if(!emailInput.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")){
                        throw new RuntimeException("Cannot add libraryuser. Given email \""
                                + emailInput + "\" is not in correct format.");
                    }
                }
            }
        }
        else{
            libraryUser.setId(0); //add new libraryuser

            //since id is not given, this operation will be treated as new record adding to the database.
            //Thus, username and email fields should be non-redundant.

            if(libraryUserDTO.getUsername() == null){
                throw new RuntimeException("Username field of libraryuser cannot be empty.");
            }
            else{
                String usernameInput = libraryUserDTO.getUsername();

                if(libraryUserService.libraryUserExistsWithUsernameOrNot(usernameInput)){
                    throw new RuntimeException("Cannot add libaryuser. Given username \""
                            + usernameInput + "\" already exists in database.");
                }
                else{
                    if(usernameInput.matches(".*\\s.*")){
                        throw new RuntimeException("Cannot add libraryuser. Given username \""
                                + usernameInput + "\" contains at least one whitespace character.");
                    }
                }
            }

            if(libraryUserDTO.getEmail() == null){
                throw new RuntimeException("Email field of libraryuser cannot be empty.");
            }
            else{
                String emailInput = libraryUserDTO.getEmail();

                if(libraryUserService.libraryUserExistsWithEmailOrNot(emailInput)){
                    throw new RuntimeException("Cannot add libaryuser. Given email \""
                            + emailInput + "\" already exists in database.");
                }
                else{
                    System.out.println("\""+ emailInput + "\"");
                    if(!emailInput.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")){
                        throw new RuntimeException("Cannot add libraryuser. Given email \""
                                + emailInput + "\" is not in correct format.");
                    }
                }
            }
        }

        libraryUser = libraryUserService.updateLibraryUserPartially(libraryUser, libraryUserDTO);
        LibraryUser libraryUserInDB = libraryUserService.save(libraryUser);

        System.out.println("Saved/updated book: " + libraryUserInDB);
        LibraryUserDTO returnLibraryUserDTO = libraryUserService.convertLibraryUserEntityTolibraryUserDTO(libraryUserInDB);

        return returnLibraryUserDTO;
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
