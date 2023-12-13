package com.example.librarymanagementdemo.controller;

import com.example.librarymanagementdemo.dto.CheckoutDTO;
import com.example.librarymanagementdemo.dto.LibraryUserDTO;
import com.example.librarymanagementdemo.entity.LibraryUser;
import com.example.librarymanagementdemo.exception.EmailValidationException;
import com.example.librarymanagementdemo.exception.LibraryUserNotFoundException;
import com.example.librarymanagementdemo.exception.UsernameValidatonException;
import com.example.librarymanagementdemo.service.CheckoutService;
import com.example.librarymanagementdemo.service.LibraryUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<LibraryUserDTO>> findAll(){

        System.out.println("\nWill return all users in db.");

        List<LibraryUserDTO> libraryUserDTOs = libraryUserService.findAll().stream()
                .map(libraryUserService::convertLibraryUserEntityTolibraryUserDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(libraryUserDTOs, HttpStatus.OK);
    }

    @GetMapping("/{libraryUserId}")
    public ResponseEntity<LibraryUserDTO> getLibraryUser(@PathVariable int libraryUserId){

        LibraryUser libraryUser = libraryUserService.findById(libraryUserId);

        if(libraryUser == null){
            throw new LibraryUserNotFoundException("Couldn't find library user with id: " + libraryUserId);
        }
        System.out.println("\nLibrary user with id " + libraryUserId + " is found.");

        LibraryUserDTO libraryUserDTO = libraryUserService.convertLibraryUserEntityTolibraryUserDTO(libraryUser);
        return new ResponseEntity<>(libraryUserDTO, HttpStatus.OK);
    }

    @GetMapping("/{libraryUserId}/checkouts")
    public ResponseEntity<List<CheckoutDTO>> getCheckoutOfLibraryUser(@PathVariable int libraryUserId){

        LibraryUser libraryUser = libraryUserService.findById(libraryUserId);

        if(libraryUser == null){
                throw new LibraryUserNotFoundException("Cannot return checkouts. Couldn't find library user with id: " + libraryUserId);
        }
        else{
            System.out.println("\nWill return all checkouts of library user with id " + libraryUserId);

            List<CheckoutDTO> checkoutDTOs = checkoutService.findByLibraryUser(libraryUser).stream()
                    .map(checkoutService::convertCheckoutEntityToCheckoutDTO)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(checkoutDTOs, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<LibraryUserDTO> addLibraryUser(@RequestBody LibraryUserDTO libraryUserDTO) {

        //username and email checks
        if(libraryUserDTO.getUsername() == null){
            throw new UsernameValidatonException("Username field of libraryuser cannot be empty.");
        }
        else{
            String usernameInput = libraryUserDTO.getUsername();

            if(libraryUserService.libraryUserExistsWithUsernameOrNot(usernameInput)){
                throw new UsernameValidatonException("Cannot add libaryuser. Given username \""
                        + usernameInput + "\" already exists in database.");
            }
            else{
                if(usernameInput.matches(".*\\s.*")){
                    throw new UsernameValidatonException("Cannot add libraryuser. Given username \""
                            + usernameInput + "\" contains at least one whitespace character.");
                }
            }
        }

        if(libraryUserDTO.getEmail() == null){
            throw new EmailValidationException("Email field of libraryuser cannot be empty.");
        }
        else{
            String emailInput = libraryUserDTO.getEmail();

            if(libraryUserService.libraryUserExistsWithEmailOrNot(emailInput)){
                throw new EmailValidationException("Cannot add libaryuser. Given email \""
                        + emailInput + "\" already exists in database.");
            }
            else{
                if(!emailInput.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")){
                    throw new EmailValidationException("Cannot add libraryuser. Given email \""
                            + emailInput + "\" is not in correct format.");
                }
            }
        }

        libraryUserDTO.setId(0);

        LibraryUser libraryUser = libraryUserService.convertLibraryUserDTOToLibraryUserEntity(libraryUserDTO);
        LibraryUser userInDB = libraryUserService.save(libraryUser); //is it empty list?

        System.out.println("Saved library user: " + userInDB);

        LibraryUserDTO returnLibraryUserDTO = libraryUserService.convertLibraryUserEntityTolibraryUserDTO(userInDB);
        return new ResponseEntity<>(returnLibraryUserDTO, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<LibraryUserDTO> updateLibraryUser(@RequestBody LibraryUserDTO libraryUserDTO) {

        LibraryUser libraryUser = new LibraryUser();

        if(libraryUserDTO.getId() != 0){
            libraryUser = libraryUserService.findById(libraryUserDTO.getId());

            if(libraryUser == null){
                throw new LibraryUserNotFoundException("Cannot update libraryuser. No libraryuser exists with given id: " + libraryUserDTO.getId());
            }

            if(libraryUserDTO.getUsername() != null && !libraryUserDTO.getUsername().equals(libraryUser.getUsername()) ){ //username change
                String usernameInput = libraryUserDTO.getUsername();

                if(libraryUserService.libraryUserExistsWithUsernameOrNot(usernameInput)){
                    throw new UsernameValidatonException("Cannot add libaryuser. Given username \""
                            + usernameInput + "\" already exists in database.");
                }
                else{
                    if(usernameInput.matches(".*\\s.*")){
                        throw new UsernameValidatonException("Cannot add libraryuser. Given username \""
                                + usernameInput + "\" contains at least one whitespace character.");
                    }
                }
            }
            if(libraryUserDTO.getEmail() != null && !libraryUserDTO.getEmail().equals(libraryUser.getEmail())){ //email change
                String emailInput = libraryUserDTO.getEmail();

                if(libraryUserService.libraryUserExistsWithEmailOrNot(emailInput)){
                    throw new EmailValidationException("Cannot add libaryuser. Given email \""
                            + emailInput + "\" already exists in database.");
                }
                else{
                    System.out.println("\""+ emailInput + "\"");
                    if(!emailInput.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")){
                        throw new EmailValidationException("Cannot add libraryuser. Given email \""
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
                throw new UsernameValidatonException("Username field of libraryuser cannot be empty.");
            }
            else{
                String usernameInput = libraryUserDTO.getUsername();

                if(libraryUserService.libraryUserExistsWithUsernameOrNot(usernameInput)){
                    throw new UsernameValidatonException("Cannot add libaryuser. Given username \""
                            + usernameInput + "\" already exists in database.");
                }
                else{
                    if(usernameInput.matches(".*\\s.*")){
                        throw new UsernameValidatonException("Cannot add libraryuser. Given username \""
                                + usernameInput + "\" contains at least one whitespace character.");
                    }
                }
            }

            if(libraryUserDTO.getEmail() == null){
                throw new EmailValidationException("Email field of libraryuser cannot be empty.");
            }
            else{
                String emailInput = libraryUserDTO.getEmail();

                if(libraryUserService.libraryUserExistsWithEmailOrNot(emailInput)){
                    throw new EmailValidationException("Cannot add libaryuser. Given email \""
                            + emailInput + "\" already exists in database.");
                }
                else{
                    System.out.println("\""+ emailInput + "\"");
                    if(!emailInput.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")){
                        throw new EmailValidationException("Cannot add libraryuser. Given email \""
                                + emailInput + "\" is not in correct format.");
                    }
                }
            }
        }

        libraryUser = libraryUserService.updateLibraryUserPartially(libraryUser, libraryUserDTO);
        LibraryUser libraryUserInDB = libraryUserService.save(libraryUser);

        System.out.println("Saved/updated book: " + libraryUserInDB);
        LibraryUserDTO returnLibraryUserDTO = libraryUserService.convertLibraryUserEntityTolibraryUserDTO(libraryUserInDB);

        return new ResponseEntity<>(returnLibraryUserDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{libraryUserId}")
    public ResponseEntity<String> deleteLibraryUser(@PathVariable int libraryUserId) {

        LibraryUser tempLibraryUser = libraryUserService.findById(libraryUserId);

        if (tempLibraryUser == null) {
            throw new LibraryUserNotFoundException("Deletion failed. could not found a library user with id: " + libraryUserId);
        }

        libraryUserService.deleteById(libraryUserId);
        System.out.println("\nLibrary user with id " + libraryUserId + " is deleted.");

        return new ResponseEntity<>("Deleted library user id: " + libraryUserId, HttpStatus.OK);
    }
}
