package com.example.librarymanagementdemo.service;

import com.example.librarymanagementdemo.dto.AuthorDTO;
import com.example.librarymanagementdemo.dto.CheckoutDTO;
import com.example.librarymanagementdemo.entity.Author;
import com.example.librarymanagementdemo.entity.Book;
import com.example.librarymanagementdemo.entity.Checkout;
import com.example.librarymanagementdemo.entity.LibraryUser;
import com.example.librarymanagementdemo.repository.CheckoutRepository;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        Checkout checkout = new Checkout();

        checkout.setId(dto.getId());
        checkout.setCheckedOutDate(dto.getCheckedOutDate());
        checkout.setDueDate(dto.getDueDate());

        //book and user will be set after checks done

        return checkout;

    }

    @Override
    public CheckoutDTO convertCheckoutEntityToCheckoutDTO(Checkout checkout) {

        CheckoutDTO dto = new CheckoutDTO();

        dto.setId(checkout.getId());
        dto.setCheckedOutDate(checkout.getCheckedOutDate());
        dto.setDueDate(checkout.getDueDate());

        dto.setBookId(checkout.getBook().getId());
        dto.setUserId(checkout.getLibraryUser().getId());

        return dto;


    }

    @Override
    public Author updateCheckoutPartially(Checkout checkout, CheckoutDTO checkoutDTO) {
        return null;
    }
}
