package com.example.librarymanagementdemo.controller;

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

import java.util.List;

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
    public List<Book> findAll(){

        System.out.println("\nWill return all books in db.");

        return bookService.findAll();
    }

    @GetMapping("/{bookId}")
    public Book getBook(@PathVariable int bookId){

        System.out.println("\nWill try to return book with id: " + bookId);

        Book book = bookService.findById(bookId);

        if(book == null){
            throw new RuntimeException("Couldn't find book with id: " + bookId);
        }

        System.out.println("\nBook with id " + bookId + " is found.");

        return book;

    }

    //get mapping /books/{bookId}/checkouts will be added

    @GetMapping("/{bookId}/authors")
    public List<Author> getAuthorOfBook(@PathVariable int bookId){
        System.out.println("\nWill try to find book with id " + bookId + " to return its authors.");

        Book book = bookService.findById(bookId);

        if(book == null){
            throw new RuntimeException("Cannot return authors. Couldn't find book with id: " + bookId);
        }
        else{

            System.out.println("\nWill return all authors of book with id " + bookId);

            return authorService.findByBook(book);
        }

    }

    @GetMapping("/{bookId}/checkouts")
    public List<Checkout> getCheckoutOfBook(@PathVariable int bookId){
        System.out.println("\nWill try to find book with id " + bookId + " to return its checkouts.");

        Book book = bookService.findById(bookId);

        if(book == null){
            throw new RuntimeException("Cannot return checkouts. Couldn't find book with id: " + bookId);
        }
        else{

            System.out.println("\nWill return all checkouts of book with id " + bookId);

            return checkoutService.findByBook(book);
        }

    }

    @PostMapping
    public Book addBook(@RequestBody Book book, @RequestParam int libraryBranchId)
                         { //add this under /librarybranches/{lbid}/books?

        //for debug purposes
        System.out.println("\nWill add a book to the database under library branch with id: " + libraryBranchId + ".");

        LibraryBranch libraryBranch = libraryBranchService.findById(libraryBranchId);

        if(libraryBranch == null){
            throw new RuntimeException("Cannot add book. Couldn't find library branch with id: " + libraryBranchId);
        }
        else{

            System.out.println("\nWill add the book to library branch with id " + libraryBranchId);

            book.setId(0);



            Book bookInDB = bookService.setFieldsAndSaveBook(book, libraryBranch, null, null);

            System.out.println("Saved book: " + bookInDB);

            return bookInDB;
        }

    }

    @PutMapping
    public Book updateBook(@RequestBody Book book) {

        System.out.println("\nWill try to update a book from database.");

        Book bookInDB = bookService.save(book);

        System.out.println("Updated book: " + bookInDB);

        return bookInDB;
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
