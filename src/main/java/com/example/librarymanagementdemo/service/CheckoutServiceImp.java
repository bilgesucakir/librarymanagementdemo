package com.example.librarymanagementdemo.service;

import com.example.librarymanagementdemo.dto.CheckoutDTO;
import com.example.librarymanagementdemo.entity.Book;
import com.example.librarymanagementdemo.entity.Checkout;
import com.example.librarymanagementdemo.entity.LibraryUser;
import com.example.librarymanagementdemo.exception.*;
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

        Checkout checkout;

        if (result.isPresent()) {
            checkout = result.get();
        }
        else{
            throw new EntityNotFoundException("Couldn't find checkout with id: " + id);
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
    public Checkout updateCheckoutPartially(Checkout checkout, CheckoutDTO checkoutDTO) {

        if(checkoutDTO.getDueDate() != null){
            checkout.setDueDate(checkoutDTO.getDueDate());
        }
        if(checkoutDTO.getCheckedOutDate() != null){
            checkout.setCheckedOutDate(checkoutDTO.getCheckedOutDate());
        }

        return checkout;
        //user id book id cannot be altered by checkout itself, this entity cannot exist without both of them
    }

    @Override
    public Checkout setFieldsAndSaveCheckout(Checkout checkout, Book book, LibraryUser libraryUser){

        if(book != null){
            checkout = setBookOfCheckout(checkout, book);
        }
        if(libraryUser != null){
            checkout = setLibraryUserOfCheckout(checkout, libraryUser);
        }
        return save(checkout);
    }

    @Override
    public Checkout setBookOfCheckout(Checkout checkout, Book book) {
        checkout.setBook(book);

        return checkout;
    }

    @Override
    public Checkout setLibraryUserOfCheckout(Checkout checkout, LibraryUser libraryUser) {
        checkout.setLibraryUser(libraryUser);

        return checkout;
    }

    @Override
    public void validateAddCheckout(CheckoutDTO checkoutDTO) {
        if(checkoutDTO.getBookId() == null){
            throw new EntityIdNullException("Book id field cannot be null.");
        }
        else if(checkoutDTO.getUserId() == null){
            throw new EntityIdNullException("Library user id field cannot be null.");
        }
        if(!checkoutDTO.getCheckedOutDate().before(checkoutDTO.getDueDate())){
            throw new EntityFieldValidationException("Checked out date cannot be bigger than or equal to due date.");
        }
    }

    @Override
    public void valdiateUpdateCheckout(CheckoutDTO checkoutDTO, int bookId, int libraryUserId){

        //book and user id must match if not null with existing record
        if(checkoutDTO.getBookId() != null && bookId != checkoutDTO.getBookId()){
            throw new EntityIdChangeNotAllowedException("You cannot alter book id of a checkout during update.");
        }
        if(checkoutDTO.getUserId() != null && libraryUserId != checkoutDTO.getUserId()){
            throw new EntityIdChangeNotAllowedException("You cannot alter libaryuser id of a checkout.");
        }
        if(!checkoutDTO.getCheckedOutDate().before(checkoutDTO.getDueDate())){
            throw new EntityFieldValidationException("Checked out date cannot be bigger than or equal to due date.");
        }
    }
}
