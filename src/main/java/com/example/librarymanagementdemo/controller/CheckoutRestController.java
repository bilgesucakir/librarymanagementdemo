package com.example.librarymanagementdemo.controller;

import com.example.librarymanagementdemo.entity.Book;
import com.example.librarymanagementdemo.entity.Checkout;
import com.example.librarymanagementdemo.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checkouts")
public class CheckoutRestController {

    private CheckoutService checkoutService;

    @Autowired
    public CheckoutRestController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @GetMapping
    public List<Checkout> findAll(){

        System.out.println("\nWill return all checkouts in db.");

        return checkoutService.findAll();
    }

    @GetMapping("/{checkoutId}")
    public Checkout getCheckout(@PathVariable int checkoutId){

        System.out.println("\nWill try to return checkout with id: " + checkoutId);

        Checkout checkout = checkoutService.findById(checkoutId);

        if(checkout == null){
            throw new RuntimeException("Couldn't find checkout with id: " + checkoutId);
        }

        System.out.println("\nCheckout with id " + checkout + " is found.");

        return checkout;

    }

    @PostMapping
    public Checkout addCheckout(@RequestBody Checkout checkout) {

        //for debug purposes
        System.out.println("\nWill add a checkout to the database.");

        checkout.setId(0);

        Checkout checkoutInDB = checkoutService.save(checkout);

        System.out.println("Saved checkout: " + checkoutInDB);

        return checkoutInDB;
    }

    @PutMapping
    public Checkout updateCheckout(@RequestBody Checkout checkout) {

        System.out.println("\nWill try to update a checkout from database.");

        Checkout checkoutInDB = checkoutService.save(checkout);

        System.out.println("Updated checkout: " + checkoutInDB);

        return checkoutInDB;
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
