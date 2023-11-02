package com.example.librarymanagementdemo.service;

import com.example.librarymanagementdemo.dto.BookDTO;
import com.example.librarymanagementdemo.dto.CheckoutDTO;
import com.example.librarymanagementdemo.entity.*;

import java.util.List;

public interface CheckoutService {


    List<Checkout> findAll();

    Checkout findById(int id);

    Checkout save(Checkout checkout);

    void deleteById(int id);

    List<Checkout> findByBook(Book book);

    List<Checkout> findByLibraryUser(LibraryUser libraryUser);

    Checkout convertCheckoutDTOToCheckoutEntity(CheckoutDTO dto);

}
