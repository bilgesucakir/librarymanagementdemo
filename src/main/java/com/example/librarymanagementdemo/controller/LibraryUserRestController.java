package com.example.librarymanagementdemo.controller;

import com.example.librarymanagementdemo.dto.CheckoutDTO;
import com.example.librarymanagementdemo.dto.LibraryUserDTO;
import com.example.librarymanagementdemo.entity.LibraryUser;
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

        List<LibraryUserDTO> libraryUserDTOs = libraryUserService.findAll().stream()
                .map(libraryUserService::convertLibraryUserEntityTolibraryUserDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(libraryUserDTOs, HttpStatus.OK);
    }

    @GetMapping("/{libraryUserId}")
    public ResponseEntity<LibraryUserDTO> getLibraryUser(@PathVariable int libraryUserId){

        LibraryUser libraryUser = libraryUserService.findById(libraryUserId);

        LibraryUserDTO libraryUserDTO = libraryUserService.convertLibraryUserEntityTolibraryUserDTO(libraryUser);
        return new ResponseEntity<>(libraryUserDTO, HttpStatus.OK);
    }

    @GetMapping("/{libraryUserId}/checkouts")
    public ResponseEntity<List<CheckoutDTO>> getCheckoutOfLibraryUser(@PathVariable int libraryUserId){

        LibraryUser libraryUser = libraryUserService.findById(libraryUserId);

        List<CheckoutDTO> checkoutDTOs = checkoutService.findByLibraryUser(libraryUser).stream()
                .map(checkoutService::convertCheckoutEntityToCheckoutDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(checkoutDTOs, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<LibraryUserDTO> addLibraryUser(@RequestBody LibraryUserDTO libraryUserDTO) {

        //username and email checks
        libraryUserService.validateAddLibraryUser(libraryUserDTO);

        libraryUserDTO.setId(0);

        LibraryUser libraryUser = libraryUserService.convertLibraryUserDTOToLibraryUserEntity(libraryUserDTO);
        LibraryUser userInDB = libraryUserService.save(libraryUser); //is it empty list?

        LibraryUserDTO returnLibraryUserDTO = libraryUserService.convertLibraryUserEntityTolibraryUserDTO(userInDB);
        return new ResponseEntity<>(returnLibraryUserDTO, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<LibraryUserDTO> updateLibraryUser(@RequestBody LibraryUserDTO libraryUserDTO) {

        LibraryUser libraryUser = new LibraryUser();

        if(libraryUserDTO.getId() != 0){
            libraryUser = libraryUserService.findById(libraryUserDTO.getId());

            libraryUserService.validateUpdateLibraryUser(libraryUserDTO, libraryUser.getUsername(), libraryUser.getEmail());
        }
        else{
            libraryUser.setId(0); //add new libraryuser

            //since id is not given, this operation will be treated as new record adding to the database.
            //Thus, username and email fields should be non-redundant.
            libraryUserService.validateAddLibraryUser(libraryUserDTO);
        }

        libraryUser = libraryUserService.updateLibraryUserPartially(libraryUser, libraryUserDTO);
        LibraryUser libraryUserInDB = libraryUserService.save(libraryUser);

        LibraryUserDTO returnLibraryUserDTO = libraryUserService.convertLibraryUserEntityTolibraryUserDTO(libraryUserInDB);

        return new ResponseEntity<>(returnLibraryUserDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{libraryUserId}")
    public ResponseEntity<String> deleteLibraryUser(@PathVariable int libraryUserId) {

        libraryUserService.findById(libraryUserId);

        libraryUserService.deleteById(libraryUserId);

        return new ResponseEntity<>("Deleted library user id: " + libraryUserId, HttpStatus.OK);
    }
}
