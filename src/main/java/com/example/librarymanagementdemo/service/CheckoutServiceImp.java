package com.example.librarymanagementdemo.service;

import com.example.librarymanagementdemo.dto.CheckoutDTO;
import com.example.librarymanagementdemo.entity.Book;
import com.example.librarymanagementdemo.entity.Checkout;
import com.example.librarymanagementdemo.entity.LibraryUser;
import com.example.librarymanagementdemo.repository.CheckoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CheckoutServiceImp implements CheckoutService{

    private CheckoutRepository checkoutRepository;

    @Autowired
    public CheckoutServiceImp(CheckoutRepository checkoutRepository) {
        this.checkoutRepository = checkoutRepository;
    }

    @Override
    public List<Checkout> findAll() {
        return checkoutRepository.findAll();
    }

    @Override
    public Checkout findById(int id) {
        Optional<Checkout> result = checkoutRepository.findById(id);

        Checkout checkout = null;

        if (result.isPresent()) {
            checkout = result.get();
        }

        return checkout;
    }

    @Override
    public Checkout save(Checkout checkout) {
        return checkoutRepository.save(checkout);
    }

    @Override
    public void deleteById(int id) {
        checkoutRepository.deleteById(id);
    }
    @Override
    public List<Checkout> findByBook(Book book) {
        return checkoutRepository.findByBook(book);
    }

    @Override
    public List<Checkout> findByLibraryUser(LibraryUser libraryUser) {
        return checkoutRepository.findByLibraryUser(libraryUser);
    }

    @Override
    public Checkout convertCheckoutDTOToCheckoutEntity(CheckoutDTO dto) {
        return null;
    }
}
