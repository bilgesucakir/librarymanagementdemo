package com.example.librarymanagementdemo.controller;
import com.example.librarymanagementdemo.entity.Author;
import com.example.librarymanagementdemo.entity.Book;
import com.example.librarymanagementdemo.entity.LibraryBranch;
import com.example.librarymanagementdemo.service.BookService;
import com.example.librarymanagementdemo.service.LibraryBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/librarybranches") //updated from /api
public class LibraryBranchRestController {

    private LibraryBranchService libraryBranchService;
    private BookService bookService;

    @Autowired
    public LibraryBranchRestController(LibraryBranchService libraryBranchService, BookService bookService) {
        this.libraryBranchService = libraryBranchService;
        this.bookService = bookService;
    }

    @GetMapping
    public List<LibraryBranch> findAll() {

        System.out.println("\nWill return all branches in db.");

        return libraryBranchService.findAll();
    }

    @GetMapping("/{libraryBranchId}")
    public LibraryBranch getLibraryBranch(@PathVariable int libraryBranchId) {

        System.out.println("\nWill try to return library branch with id: " + libraryBranchId);

        LibraryBranch libraryBranch = libraryBranchService.findById(libraryBranchId);

        if (libraryBranch == null) {
            throw new RuntimeException("Couldn't find library branch with id: " + libraryBranchId);
        }

        System.out.println("\nLibrary branch with id " + libraryBranchId + " is found.");

        return libraryBranch;
    }

    @GetMapping("/{libraryBranchId}/books")
    public List<Book> getBookOfLibraryBranch(@PathVariable int libraryBranchId){
        System.out.println("\nWill try to find library branch with id " + libraryBranchId + " to return its books.");

        LibraryBranch libraryBranch = libraryBranchService.findById(libraryBranchId);

        if(libraryBranch == null){
            throw new RuntimeException("Cannot return books. Couldn't find library branch with id: " + libraryBranchId);
        }
        else{

            System.out.println("\nWill return all books of library branch with id " + libraryBranchId);

            return bookService.findByLibraryBranch(libraryBranch);
        }

    }

    @PostMapping
    public LibraryBranch addLibraryBranch(@RequestBody LibraryBranch libraryBranch) {

        //for debug purposes
        System.out.println("\nWill add a library branch to the database.");

        libraryBranch.setId(0); //to force a save of new item

        LibraryBranch branchInDB = libraryBranchService.save(libraryBranch);

        System.out.println("Saved library branch: " + branchInDB);

        return branchInDB;
    }

    /*@PostMapping("/{libraryBranchId}/books")
    public Book addBookToLibraryBranch(@RequestBody Book book, @PathVariable int libraryBranchId){

        LibraryBranch libraryBranch = libraryBranchService.findById(libraryBranchId);

        if(libraryBranch == null){
            throw new RuntimeException("Cannot add book. Couldn't find library branch with id: " + libraryBranchId);
        }
        else{

            System.out.println("\nWill add the book to library branch with id " + libraryBranchId);

            return bookService.saveBookWithLibraryBranch(book, libraryBranch);
        }

    }*/


    @PutMapping
    public LibraryBranch updateLibraryBranch(@RequestBody LibraryBranch libraryBranch) {

        System.out.println("\nWill try to update a library branch from database.");

        LibraryBranch branchInDB = libraryBranchService.save(libraryBranch);

        System.out.println("Updated library branch: " + branchInDB);

        return branchInDB;
    }

    @DeleteMapping("/{libraryBranchId}")
    public String deleteLibraryBranch(@PathVariable int libraryBranchId) {

        LibraryBranch tempLibraryBranch = libraryBranchService.findById(libraryBranchId);

        if (tempLibraryBranch == null) {
            throw new RuntimeException("Deletion failed. could not found a library branch with id: " + libraryBranchId);
        }

        libraryBranchService.deleteById(libraryBranchId);

        System.out.println("\nLibrary branch with id " + libraryBranchId + " is deleted.");

        return "Deleted library branch id: " + libraryBranchId;
    }

    //will add /books, /books/id, get queriy handling example /librarybranches, etc

}
