package com.example.librarymanagementdemo.controller;

import com.example.librarymanagementdemo.entity.Author;
import com.example.librarymanagementdemo.entity.LibraryUser;
import com.example.librarymanagementdemo.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorRestController {

    private AuthorService authorService;

    @Autowired
    public AuthorRestController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<Author> findAll(){

        System.out.println("\nWill return all authors in db.");

        return authorService.findAll();
    }

    @GetMapping("/{authorId}")
    public Author getAuthor(@PathVariable int authorId){

        System.out.println("\nWill try to return author with id: " + authorId);

        Author author = authorService.findById(authorId);

        if(author == null){
            throw new RuntimeException("Couldn't find author with id: " + authorId);
        }

        System.out.println("\nAuthor with id " + authorId + " is found.");

        return author;

    }

    @PostMapping
    public Author addAuthor(@RequestBody Author author) {

        //for debug purposes
        System.out.println("\nWill add an author to the database.");

        author.setId(0);

        Author authorInDB = authorService.save(author);

        System.out.println("Saved author: " + authorInDB);

        return authorInDB;
    }

    @PutMapping
    public Author updateAuthor(@RequestBody Author author) {

        System.out.println("\nWill try to update an author from database.");

        Author authorInDB = authorService.save(author);

        System.out.println("Updated author: " + authorInDB);

        return authorInDB;
    }

    @DeleteMapping("/{authorId}")
    public String deleteAuthor(@PathVariable int authorId) {

        Author tempAuthor = authorService.findById(authorId);

        if (tempAuthor == null) {
            throw new RuntimeException("Deletion failed. could not found an author with id: " + authorId);
        }

        authorService.deleteById(authorId);

        System.out.println("\nAuthor with id " + authorId + " is deleted.");

        return "Deleted author id: " + authorId;
    }
}
