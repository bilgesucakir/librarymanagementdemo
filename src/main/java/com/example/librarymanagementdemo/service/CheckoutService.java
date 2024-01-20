package com.example.librarymanagementdemo.service;

import com.example.librarymanagementdemo.dto.CheckoutDTO;
import com.example.librarymanagementdemo.entity.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface CheckoutService {

    List<Checkout> findAllWithOptionalFilter(Boolean active, Date checkedOutDateBefore, Date checkedOutDateAfter, Date dueDateBefore, Date dueDateAfter);

    Checkout findById(int id);

    Checkout save(Checkout checkout);

    void deleteById(int id);

    List<Checkout> findByBook(Book book);

    List<Checkout> findByLibraryUser(LibraryUser libraryUser);

    Checkout convertCheckoutDTOToCheckoutEntity(CheckoutDTO dto);

    CheckoutDTO convertCheckoutEntityToCheckoutDTO(Checkout checkout);

    Checkout updateCheckoutPartially(Checkout checkout, CheckoutDTO checkoutDTO);

    Checkout setFieldsAndSaveCheckout(Checkout checkout, Book book, LibraryUser libraryUser);

    Checkout setBookOfCheckout(Checkout checkout, Book book);

    Checkout setLibraryUserOfCheckout(Checkout checkout, LibraryUser libraryUser);

    void validateAddCheckout(CheckoutDTO checkoutDTO);

    void validateUpdateCheckout(CheckoutDTO checkoutDTO, int bookId, int libraryUserId);
}
