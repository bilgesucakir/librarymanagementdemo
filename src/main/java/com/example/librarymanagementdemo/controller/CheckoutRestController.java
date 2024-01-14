package com.example.librarymanagementdemo.controller;
import com.example.librarymanagementdemo.dto.CheckoutDTO;
import com.example.librarymanagementdemo.entity.Book;
import com.example.librarymanagementdemo.entity.Checkout;
import com.example.librarymanagementdemo.entity.LibraryUser;
import com.example.librarymanagementdemo.service.BookService;
import com.example.librarymanagementdemo.service.CheckoutService;
import com.example.librarymanagementdemo.service.LibraryUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<CheckoutDTO>> findAll(){

        List<CheckoutDTO> checkoutDTOs = checkoutService.findAll().stream()
                .map(checkoutService::convertCheckoutEntityToCheckoutDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(checkoutDTOs, HttpStatus.OK);
    }

    @GetMapping("/{checkoutId}")
    public ResponseEntity<CheckoutDTO> getCheckout(@PathVariable int checkoutId){

        Checkout checkout = checkoutService.findById(checkoutId);

        CheckoutDTO checkoutDTO = checkoutService.convertCheckoutEntityToCheckoutDTO(checkout);
        return new ResponseEntity<>(checkoutDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CheckoutDTO> addCheckout(@RequestBody CheckoutDTO checkoutDTO) {
        //check if book and user id exists in request body
        checkoutService.validateAddCheckout(checkoutDTO);

        //check if given user and book id exists in database
        int userId = checkoutDTO.getUserId();
        int bookId = checkoutDTO.getBookId();

        Book book = bookService.findById(bookId);
        LibraryUser libraryUser = libraryUserService.findById(userId);

        checkoutDTO.setId(0);
        Checkout checkout = checkoutService.convertCheckoutDTOToCheckoutEntity(checkoutDTO);

        Checkout checkoutInDB = checkoutService.setFieldsAndSaveCheckout(checkout, book, libraryUser);

        CheckoutDTO returnCheckoutDTO = checkoutService.convertCheckoutEntityToCheckoutDTO(checkoutInDB);
        return new ResponseEntity<>(returnCheckoutDTO, HttpStatus.OK);

    }

    @PutMapping
    public ResponseEntity<CheckoutDTO> updateCheckout(@RequestBody CheckoutDTO checkoutDTO) {

        Checkout checkout = new Checkout();
        int checkoutId = checkoutDTO.getId();
        Checkout checkoutInDB;

        //treated as id==0 add, id!=0 update
        if(checkoutId == 0){ //book and user id cannot be null

            checkoutService.validateAddCheckout(checkoutDTO);

            checkout.setId(0);
            checkout = checkoutService.updateCheckoutPartially(checkout, checkoutDTO);

            Book book = bookService.findById(checkoutDTO.getBookId());
            LibraryUser libraryUser = libraryUserService.findById(checkoutDTO.getUserId());

            checkoutInDB = checkoutService.setFieldsAndSaveCheckout(checkout, book, libraryUser);
        }
        else{
            checkoutService.valdiateUpdateCheckout(checkoutDTO, checkout.getBook().getId(), checkout.getLibraryUser().getId());

            checkout = checkoutService.updateCheckoutPartially(checkout, checkoutDTO);
            checkoutInDB = checkoutService.setFieldsAndSaveCheckout(checkout, null, null);
        }

        CheckoutDTO returnCheckoutDTO = checkoutService.convertCheckoutEntityToCheckoutDTO(checkoutInDB);
        return new ResponseEntity<>(returnCheckoutDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{checkoutId}")
    public ResponseEntity<String> deleteCheckout(@PathVariable int checkoutId) {

        checkoutService.findById(checkoutId);

        checkoutService.deleteById(checkoutId);

        return new ResponseEntity<>("Deleted checkout id: " + checkoutId, HttpStatus.OK);
    }
}
