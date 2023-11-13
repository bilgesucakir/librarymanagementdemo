package com.example.librarymanagementdemo.controller;

import com.example.librarymanagementdemo.dto.AuthorDTO;
import com.example.librarymanagementdemo.dto.BookDTO;
import com.example.librarymanagementdemo.dto.CheckoutDTO;
import com.example.librarymanagementdemo.entity.Author;
import com.example.librarymanagementdemo.entity.Book;
import com.example.librarymanagementdemo.entity.Checkout;
import com.example.librarymanagementdemo.entity.LibraryBranch;
import com.example.librarymanagementdemo.service.AuthorService;
import com.example.librarymanagementdemo.service.BookService;
import com.example.librarymanagementdemo.service.CheckoutService;
import com.example.librarymanagementdemo.service.LibraryBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private BookService bookService;
    private AuthorService authorService;
    private LibraryBranchService libraryBranchService;
    private CheckoutService checkoutService;

    @Autowired
    public BookRestController(BookService bookService, AuthorService authorService, CheckoutService checkoutService, LibraryBranchService libraryBranchService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.checkoutService = checkoutService;
        this.libraryBranchService = libraryBranchService;
    }

    @GetMapping
    public List<BookDTO> findAll(){

        System.out.println("\nWill return all books in db.");

        List<BookDTO> bookDTOs = bookService.findAll().stream()
                .map(bookService::convertBookEntityToBookDTO)
                .collect(Collectors.toList());

        return bookDTOs;
    }

    @GetMapping("/{bookId}")
    public BookDTO getBook(@PathVariable int bookId){

        System.out.println("\nWill try to return book with id: " + bookId);
        Book book = bookService.findById(bookId);

        if(book == null){
            throw new RuntimeException("Couldn't find book with id: " + bookId);
        }

        System.out.println("\nBook with id " + bookId + " is found.");

        BookDTO bookDTO = bookService.convertBookEntityToBookDTO(book);
        return bookDTO;
    }

    @GetMapping("/{bookId}/authors")
    public List<AuthorDTO> getAuthorOfBook(@PathVariable int bookId){

        System.out.println("\nWill try to find book with id " + bookId + " to return its authors.");
        Book book = bookService.findById(bookId);

        if(book == null){
            throw new RuntimeException("Cannot return authors. Couldn't find book with id: " + bookId);
        }
        else{
            System.out.println("\nWill return all authors of book with id " + bookId);

            List<Author> authors = authorService.findByBook(book);
            List<AuthorDTO> authorDTOs = authors.stream()
                    .map(authorService::convertAuthorEntityToAuthorDTO)
                    .collect(Collectors.toList());

            return authorDTOs;
        }
    }

    @GetMapping("/{bookId}/checkouts")
    public List<CheckoutDTO> getCheckoutOfBook(@PathVariable int bookId){

        System.out.println("\nWill try to find book with id " + bookId + " to return its checkouts.");
        Book book = bookService.findById(bookId);

        if(book == null){
            throw new RuntimeException("Cannot return checkouts. Couldn't find book with id: " + bookId);
        }
        else{
            System.out.println("\nWill return all checkouts of book with id " + bookId);

            List<Checkout> checkouts = checkoutService.findByBook(book);
            List<CheckoutDTO> checkoutDTOs = checkouts.stream()
                    .map(checkoutService::convertCheckoutEntityToCheckoutDTO)
                    .collect(Collectors.toList());

            return checkoutDTOs;
        }
    }

    @PostMapping
    public BookDTO addBook(@RequestBody BookDTO bookDTO)
    {
        System.out.println("\nWill try to add a book to the database");

        if(bookDTO.getLibraryBranchId() == null){
            throw new RuntimeException("Library branch id is not provided. Cannot add book.");
        }
        int libraryBranchId = bookDTO.getLibraryBranchId();

        System.out.println("\nWill try to add a book to the database under library branch with id: " + libraryBranchId + ".");
        LibraryBranch libraryBranch = libraryBranchService.findById(libraryBranchId);

        if(libraryBranch == null){
            throw new RuntimeException("Cannot add book. Couldn't find library branch with id: " + libraryBranchId);
        }
        else{
            System.out.println("\nWill add the book to library branch with id " + libraryBranchId);

            bookDTO.setId(0);
            Book book = bookService.convertBookDTOToBookEntity(bookDTO);

            List<Author> authors = new ArrayList<>();

            if(bookDTO.getAuthorIds() != null) {
                authors = bookDTO.getAuthorIds().stream().map(authorService::findById)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
            }

            Book bookInDB = bookService.setFieldsAndSaveBook(book, libraryBranch, authors);
            System.out.println("Saved book: " + bookInDB);

            BookDTO returnBookDTO = bookService.convertBookEntityToBookDTO(bookInDB);
            return returnBookDTO;
        }

    }

    @PutMapping
    public BookDTO updateBook(@RequestBody BookDTO bookDTO) {

        System.out.println("\nWill try to update a book from database.");

        Integer libraryBranchId = bookDTO.getLibraryBranchId();
        LibraryBranch libraryBranch = null;

        if(libraryBranchId != null){
            libraryBranch = libraryBranchService.findById(libraryBranchId);

            if(libraryBranch == null){
                throw new RuntimeException("Cannot update/add book. Couldn't find library branch with id: " + libraryBranchId);
            }
        }

        Book book = new Book();

        if(bookDTO.getId() != 0){
            book = bookService.findById(bookDTO.getId());

            if(book == null){
                throw new RuntimeException("Cannot update book. No book exists with id: " + bookDTO.getId() );
            }
        }
        else{
            book.setId(0);
        }

        book = bookService.updateBookPartially(book, bookDTO);
        Book bookInDB = new Book();

        if(bookDTO.getAuthorIds() != null){
            List<Author> authors = bookDTO.getAuthorIds().stream()
                    .map(authorService::findById)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            bookInDB = bookService.setFieldsAndSaveBook(book, libraryBranch, authors);
        }
        else{
            bookInDB = bookService.setFieldsAndSaveBook(book, libraryBranch, null);
        }

        System.out.println("Saved book: " + bookInDB);

        BookDTO returnBookDTO = bookService.convertBookEntityToBookDTO(bookInDB);
        return returnBookDTO;
    }

    @DeleteMapping("/{bookId}")
    public String deleteBook(@PathVariable int bookId) {

        Book tempBook = bookService.findById(bookId);

        if (tempBook == null) {
            throw new RuntimeException("Deletion failed. could not found a book with id: " + bookId);
        }

        bookService.deleteById(bookId);
        System.out.println("\nBook with id " + bookId + " is deleted.");

        return "Deleted book id: " + bookId;
    }
}
