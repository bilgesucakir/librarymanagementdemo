package com.example.librarymanagementdemo.controller;

import com.example.librarymanagementdemo.dto.BookDTO;
import com.example.librarymanagementdemo.dto.CheckoutDTO;
import com.example.librarymanagementdemo.entity.Book;
import com.example.librarymanagementdemo.entity.Checkout;
import com.example.librarymanagementdemo.entity.LibraryUser;
import com.example.librarymanagementdemo.service.BookService;
import com.example.librarymanagementdemo.service.CheckoutService;
import com.example.librarymanagementdemo.service.LibraryUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/checkouts")
public class CheckoutRestController {

    private CheckoutService checkoutService;
    private BookService bookService;
    private LibraryUserService libraryUserService;

    @Autowired
    public CheckoutRestController(CheckoutService checkoutService, BookService bookService, LibraryUserService libraryUserService) {
        this.checkoutService = checkoutService;
        this.bookService = bookService;
        this.libraryUserService = libraryUserService;
    }

    @GetMapping
    public List<CheckoutDTO> findAll(){

        System.out.println("\nWill return all checkouts in db.");

        List<CheckoutDTO> checkoutDTOs = checkoutService.findAll().stream()
                .map(checkoutService::convertCheckoutEntityToCheckoutDTO)
                .collect(Collectors.toList());

        return checkoutDTOs;
    }

    @GetMapping("/{checkoutId}")
    public CheckoutDTO getCheckout(@PathVariable int checkoutId){

        System.out.println("\nWill try to return checkout with id: " + checkoutId);
        Checkout checkout = checkoutService.findById(checkoutId);

        if(checkout == null){
            throw new RuntimeException("Couldn't find checkout with id: " + checkoutId);
        }

        System.out.println("\nCheckout with id " + checkout + " is found.");

        CheckoutDTO checkoutDTO = checkoutService.convertCheckoutEntityToCheckoutDTO(checkout);
        return checkoutDTO;
    }

    @PostMapping
    public CheckoutDTO addCheckout(@RequestBody CheckoutDTO checkoutDTO) {
        System.out.println(checkoutDTO.getCheckedOutDate());

        //for debug purposes
        System.out.println("\nWill try to add a checkout to the database.");

        //check if book and user id exists in request body
        if(checkoutDTO.getUserId() == null){
            throw new RuntimeException("User id is not provided. Cannot add checkout.");
        }
        else if(checkoutDTO.getBookId() == null){
            throw new RuntimeException("Book id is not provided. Cannot add checkout.");
        }

        //check if given user and book id exists in database
        int userId = checkoutDTO.getUserId();
        int bookId = checkoutDTO.getBookId();
        System.out.println("\nWill try to add a checkout to the database with user id: " + userId + " and book id: " + bookId + ".");

        Book book = bookService.findById(bookId);
        LibraryUser libraryUser = libraryUserService.findById(userId);

        if(book == null){
            throw new RuntimeException("Cannot add checkout. Couldn't find book with id: " + bookId);
        }
        else if(libraryUser == null){
            throw new RuntimeException("Cannot add checkout. Couldn't find libraryuser with id: " + userId);
        }
        else{
            checkoutDTO.setId(0);
            Checkout checkout = checkoutService.convertCheckoutDTOToCheckoutEntity(checkoutDTO);

            Checkout checkoutInDB = checkoutService.setFieldsAndSaveCheckout(checkout, book, libraryUser);
            System.out.println("Saved checkout: " + checkoutInDB);

            CheckoutDTO returnCheckoutDTO = checkoutService.convertCheckoutEntityToCheckoutDTO(checkoutInDB);
            return returnCheckoutDTO;
        }
    }

    @PutMapping
    public CheckoutDTO updateCheckout(@RequestBody CheckoutDTO checkoutDTO) {

        System.out.println("\nWill try to update a checkout from database.");

        Checkout checkout = new Checkout();
        int checkoutId = checkoutDTO.getId();
        Checkout checkoutInDB = new Checkout();

        //treated as id==0 add, id!=0 update
        if(checkoutId == 0){ //book and user id cannot be null
            if(checkoutDTO.getBookId() == null || checkoutDTO.getUserId() == null){
                throw new RuntimeException("Cannot add new record. Book id or library user id cannot be null.");
            }
            checkout.setId(0);
            checkout = checkoutService.updateCheckoutPartially(checkout, checkoutDTO);

            Book book = bookService.findById(checkoutDTO.getBookId());
            LibraryUser libraryUser = libraryUserService.findById(checkoutDTO.getUserId());

            checkoutInDB = checkoutService.setFieldsAndSaveCheckout(checkout, book, libraryUser);
        }
        else{
            checkout = checkoutService.findById(checkoutId);
            if(checkout == null){
                throw new RuntimeException("Cannot update checkout. No checkout exists with id: " + checkoutId);
            }
            else{ //book and user id must match if not null with existing record
                if(checkoutDTO.getBookId() != null && checkout.getBook().getId() != checkoutDTO.getBookId()){
                    throw new RuntimeException("Cannot update checkout. You cannot alter book id of a checkout.");
                }
                if(checkoutDTO.getUserId() != null && checkout.getLibraryUser().getId() != checkoutDTO.getUserId()){
                    throw new RuntimeException("Cannot update checkout. You cannot alter libaryuser id of a checkout.");
                }
            }
            checkout = checkoutService.updateCheckoutPartially(checkout, checkoutDTO);
            checkoutInDB = checkoutService.setFieldsAndSaveCheckout(checkout, null, null);
        }

        System.out.println("Updated checkout: " + checkoutInDB);

        CheckoutDTO returnCheckoutDTO = checkoutService.convertCheckoutEntityToCheckoutDTO(checkoutInDB);
        return returnCheckoutDTO;
    }

    @DeleteMapping("/{checkoutId}")
    public String deleteCheckout(@PathVariable int checkoutId) {

        Checkout tempCheckout = checkoutService.findById(checkoutId);

        if (tempCheckout == null) {
            throw new RuntimeException("Deletion failed. could not found a checkout with id: " + checkoutId);
        }

        checkoutService.deleteById(checkoutId);
        System.out.println("\nCheckout with id " + checkoutId + " is deleted.");

        return "Deleted checkout id: " + checkoutId;
    }
}
