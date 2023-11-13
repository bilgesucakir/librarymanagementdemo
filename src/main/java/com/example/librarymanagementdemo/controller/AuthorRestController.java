package com.example.librarymanagementdemo.controller;

import com.example.librarymanagementdemo.dto.AuthorDTO;
import com.example.librarymanagementdemo.dto.BookDTO;
import com.example.librarymanagementdemo.entity.Author;
import com.example.librarymanagementdemo.entity.Book;
import com.example.librarymanagementdemo.service.AuthorService;
import com.example.librarymanagementdemo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/authors")
public class AuthorRestController {

    private AuthorService authorService;
    private BookService bookService;

    @Autowired
    public AuthorRestController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @GetMapping
    public List<AuthorDTO> findAllWithOptionalFilter(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "birthdate", required = false) Date birthdate,
            @RequestParam(name = "nationality", required = false) String nationality
    ){
        List<Author> authors = new ArrayList<>();

        if(name == null && birthdate == null && nationality == null){
            //no filtering, calling findAll

            System.out.println("\nWill return all authors in db.");

            authors =  authorService.findAll();
        }
        else{//some filtering exists

            System.out.print("\nWill return all authors in db with filtering.");

            authors =  authorService.findByFilter(name, birthdate, nationality);
        }

        List<AuthorDTO> authorDTOs = authors.stream()
                .map(authorService::convertAuthorEntityToAuthorDTO)
                .collect(Collectors.toList());

        return authorDTOs;
    }

    @GetMapping("/{authorId}")
    public AuthorDTO getAuthor(@PathVariable int authorId){

        Author author = authorService.findById(authorId);

        if(author == null){
            throw new RuntimeException("Couldn't find author with id: " + authorId);
        }

        System.out.println("\nAuthor with id " + authorId + " is found.");

        AuthorDTO authorDTO = authorService.convertAuthorEntityToAuthorDTO(author);
        return authorDTO;
    }

    @GetMapping("/{authorId}/books")
    public List<BookDTO> getBookOfAuthor(@PathVariable int authorId){ //comes from url

        Author author = authorService.findById(authorId);

        if(author == null){
            throw new RuntimeException("Cannot return books. Couldn't find author with id: " + authorId);
        }
        else{

            System.out.println("\nWill return all books of author with id " + authorId);

            List<Book> books = bookService.findByAuthor(author);
            List<BookDTO> bookDTOs = books.stream()
                    .map(bookService::convertBookEntityToBookDTO)
                    .collect(Collectors.toList());

            return bookDTOs;
        }
    }

    @PostMapping
    public AuthorDTO addAuthor(@RequestBody AuthorDTO authorDTO) { //get entity params from body

        authorDTO.setId(0);
        Author author = authorService.convertAuthorDTOToAuthorEntity(authorDTO);

        List<Book> books = new ArrayList<>();
        if(authorDTO.getBookIds() != null) {
            books = authorDTO.getBookIds().stream().map(bookService::findById)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }

        Author authorInDB = authorService.setBooksAndSaveAuthor(author, books);

        System.out.println("Saved author: " + authorInDB);

        AuthorDTO returnAuthorDTO = authorService.convertAuthorEntityToAuthorDTO(authorInDB);
        return returnAuthorDTO;
    }

    @PutMapping
    public AuthorDTO updateAuthor(@RequestBody AuthorDTO authorDTO) {

        Author author = new Author();
        //if author exists or not
        if(authorDTO.getId() != 0){ //replace existing instance

            author = authorService.findById(authorDTO.getId());
            if(author == null){
                throw new RuntimeException("Cannot update author. No author exists with id: " + authorDTO.getId() );
            }
        }
        else{ //cretae new instance
            author.setId(0);
        }

        author = authorService.updateAuthorPartially(author, authorDTO);
        Author authorInDB = new Author();

        //book checks
        if(authorDTO.getBookIds()!= null){

            List<Book> books = new ArrayList<>();
            if(authorDTO.getBookIds() != null) {
                books = authorDTO.getBookIds().stream().map(bookService::findById)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
            }

            authorInDB = authorService.setBooksAndSaveAuthor(author, books);
        }
        else{
            authorInDB = authorService.setBooksAndSaveAuthor(author, null);
        }
        System.out.println("Saved author: " + authorInDB);

        AuthorDTO returnAuthorDTO = authorService.convertAuthorEntityToAuthorDTO(authorInDB);
        return returnAuthorDTO;
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
