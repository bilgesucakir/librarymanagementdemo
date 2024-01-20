package com.example.librarymanagementdemo.controller;
import com.example.librarymanagementdemo.dto.CheckoutDTO;
import com.example.librarymanagementdemo.entity.Book;
import com.example.librarymanagementdemo.entity.Checkout;
import com.example.librarymanagementdemo.entity.LibraryUser;
import com.example.librarymanagementdemo.service.BookService;
import com.example.librarymanagementdemo.service.CheckoutService;
import com.example.librarymanagementdemo.service.LibraryUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
    public ResponseEntity<List<CheckoutDTO>> findAllWithOptionalFilter(
            @RequestParam(name = "active", required = false) Boolean active, /*true or false*/
            @RequestParam(name = "checkedOutDateBefore", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date checkedOutDateBefore,
            @RequestParam(name = "checkedOutDateAfter", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date checkedOutDateAfter,
            @RequestParam(name = "dueDateBefore", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dueDateBefore,
            @RequestParam(name = "dueDateAfter", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dueDateAfter
    ){

        List<CheckoutDTO> checkoutDTOs = checkoutService
                .findAllWithOptionalFilter(active, checkedOutDateBefore, checkedOutDateAfter, dueDateBefore, dueDateAfter)
                .stream()
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

        checkoutService.validateAddCheckout(checkoutDTO);

        Book book = bookService.findById(checkoutDTO.getBookId());
        LibraryUser libraryUser = libraryUserService.findById(checkoutDTO.getUserId());

        checkoutDTO.setId(0);
        Checkout checkout = checkoutService.convertCheckoutDTOToCheckoutEntity(checkoutDTO);

        Checkout checkoutInDB = checkoutService.setFieldsAndSaveCheckout(checkout, book, libraryUser);

        CheckoutDTO returnCheckoutDTO = checkoutService.convertCheckoutEntityToCheckoutDTO(checkoutInDB);
        return new ResponseEntity<>(returnCheckoutDTO, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CheckoutDTO> updateCheckout(@RequestBody CheckoutDTO checkoutDTO) {

        Checkout checkout = new Checkout();
        Checkout checkoutInDB;

        if(checkoutDTO.getId() == 0){
            checkoutService.validateAddCheckout(checkoutDTO);

            checkout.setId(0);
            checkout = checkoutService.updateCheckoutPartially(checkout, checkoutDTO);

            Book book = bookService.findById(checkoutDTO.getBookId());
            LibraryUser libraryUser = libraryUserService.findById(checkoutDTO.getUserId());

            checkoutInDB = checkoutService.setFieldsAndSaveCheckout(checkout, book, libraryUser);
        }
        else{
            checkoutService.validateUpdateCheckout(checkoutDTO, checkout.getBook().getId(), checkout.getLibraryUser().getId());

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
