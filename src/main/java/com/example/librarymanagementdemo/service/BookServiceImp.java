package com.example.librarymanagementdemo.service;

import com.example.librarymanagementdemo.dto.BookDTO;
import com.example.librarymanagementdemo.entity.Author;
import com.example.librarymanagementdemo.entity.Book;
import com.example.librarymanagementdemo.entity.Checkout;
import com.example.librarymanagementdemo.entity.LibraryBranch;
import com.example.librarymanagementdemo.repository.BookRepository;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImp implements  BookService{

    private BookRepository bookRepository;

    @Autowired
    public BookServiceImp(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(int id) {
        Optional<Book> result = bookRepository.findById(id);

        Book book = null;

        if (result.isPresent()) {
            book = result.get();
        }

        return book;
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteById(int id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<Book> findByAuthor(Author author) {
        return bookRepository.findByAuthorsContaining(author);
    }

    @Override
    public List<Book> findByLibraryBranch(LibraryBranch libraryBranch) {
        return bookRepository.findByLibraryBranch(libraryBranch);
    }

    @Override
    public Book setLibraryBranchOfBook(Book book, LibraryBranch libraryBranch) {
        book.setLibraryBranch(libraryBranch);

        return book;
    }

    @Override
    public Book setCheckoutsOfBook(Book book, List<Checkout> checkouts) {
        book.setCheckouts(checkouts);

        return book;
    }

    @Override
    public Book setAuthorsOfBook(Book book, List<Author> authors) {
        book.setAuthors(authors);

        return book;
    }

    @Override
    public Book setFieldsAndSaveBook(Book book, LibraryBranch libraryBranch, List<Author> authors) {

        Book tempBook = book;

        if(libraryBranch != null){
            tempBook = setLibraryBranchOfBook(book, libraryBranch);
        }


        if(authors != null){
            tempBook = setAuthorsOfBook(tempBook, authors);
        }

        //call save method
        return save(tempBook);
    }

    @Override
    public Book convertBookDTOToBookEntity(BookDTO dto) {
        Book book = new Book();

        book.setId(dto.getId());
        book.setTitle(dto.getTitle());
        book.setAvailable(dto.isAvailable());
        book.setGenre(dto.getGenre());
        book.setISBN(dto.getISBN());
        book.setPublicationYear(dto.getPublicationYear());
        book.setMultipleAuthors(dto.isMultipleAuthors());

        return book;
    }

    @Override
    public BookDTO convertBookEntityToBookDTO(Book book) {
        BookDTO dto = new BookDTO();

        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAvailable(book.isAvailable());
        dto.setGenre(book.getGenre());
        dto.setISBN(book.getISBN());
        dto.setPublicationYear(book.getPublicationYear());
        dto.setMultipleAuthors(book.isMultipleAuthors());

        if(book.getAuthors() == null){
            List<Integer> emptyList = new ArrayList<>();
            dto.setAuthorIds(emptyList);
        }
        else{
            dto.setAuthorIds(book.getAuthors().stream()
                    .map(Author::getId)
                    .collect(Collectors.toList()));
        }

        dto.setLibraryBranchId(book.getLibraryBranch().getId());

        if(book.getCheckouts() == null){
            List<Integer> emptyList = new ArrayList<>();
            dto.setCheckoutIds(emptyList);
        }
        else{
            dto.setCheckoutIds(book.getCheckouts().stream()
                    .map(Checkout::getId)
                    .collect(Collectors.toList()));

        }
        return dto;

    }

    @Override
    public Book updateBookPartially(Book book, BookDTO bookDTO) {

        //id already exists
        if(bookDTO.getISBN() != null){
            book.setISBN(bookDTO.getISBN());
        }
        if(bookDTO.isAvailable() != book.isAvailable()){
            book.setAvailable(bookDTO.isAvailable());
        }
        if(bookDTO.isMultipleAuthors() != book.isMultipleAuthors()){
            book.setMultipleAuthors(bookDTO.isMultipleAuthors());
        }
        if(bookDTO.getTitle() != null){
            book.setTitle(bookDTO.getTitle());
        }
        if(bookDTO.getPublicationYear() != null){
            book.setPublicationYear(bookDTO.getPublicationYear());
        }
        if(bookDTO.getGenre() != null){
            book.setGenre(bookDTO.getGenre());
        }

        return book;

    }


}
