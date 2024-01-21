package com.example.librarymanagementdemo.controller;

import com.example.librarymanagementdemo.dto.AuthorDTO;
import com.example.librarymanagementdemo.dto.BookDTO;
import com.example.librarymanagementdemo.entity.Author;
import com.example.librarymanagementdemo.entity.Book;
import com.example.librarymanagementdemo.service.AuthorService;
import com.example.librarymanagementdemo.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<AuthorDTO>> findAllWithOptionalFilter(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "birthdate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthdate,
            @RequestParam(name = "nationality", required = false) String nationality
    ){
        List<Author> authors =  authorService.findAllWithOptionalfilter(name, birthdate, nationality);

        List<AuthorDTO> authorDTOs = authors.stream()
                .map(authorService::convertAuthorEntityToAuthorDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(authorDTOs, HttpStatus.OK);
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<AuthorDTO> getAuthor(@PathVariable int authorId){

        Author author = authorService.findById(authorId);

        AuthorDTO authorDTO = authorService.convertAuthorEntityToAuthorDTO(author);
        return new ResponseEntity<>(authorDTO, HttpStatus.OK);
    }

    @GetMapping("/{authorId}/books")
    public ResponseEntity<List<BookDTO>> getBookOfAuthor(@PathVariable int authorId){ //comes from url

        Author author = authorService.findById(authorId);

        List<Book> books = bookService.findByAuthor(author);
        List<BookDTO> bookDTOs = books.stream()
                    .map(bookService::convertBookEntityToBookDTO)
                    .collect(Collectors.toList());

        return new ResponseEntity<>(bookDTOs, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> addAuthor(@Valid @RequestBody AuthorDTO authorDTO) { //get entity params from body

        //authorService.validateAuthor(authorDTO);

        authorDTO.setId(0);
        Author author = authorService.convertAuthorDTOToAuthorEntity(authorDTO);

        List<Book> books = new ArrayList<>();
        if(authorDTO.getBookIds() != null) {
            books = authorDTO.getBookIds().stream().map(bookService::findById)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }

        Author authorInDB = authorService.setBooksAndSaveAuthor(author, books);

        AuthorDTO returnAuthorDTO = authorService.convertAuthorEntityToAuthorDTO(authorInDB);
        return new ResponseEntity<>(returnAuthorDTO, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<AuthorDTO> updateAuthor(@Valid @RequestBody AuthorDTO authorDTO) {

        Author author = authorService.findById(authorDTO.getId());

        author = authorService.updateAuthorPartially(author, authorDTO);

        Author authorInDB;
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

        AuthorDTO returnAuthorDTO = authorService.convertAuthorEntityToAuthorDTO(authorInDB);
        return new ResponseEntity<>(returnAuthorDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{authorId}")
    public ResponseEntity<String> deleteAuthor(@PathVariable int authorId) {

        authorService.findById(authorId);

        authorService.deleteById(authorId);

        return new ResponseEntity<>("Deleted author id: " + authorId, HttpStatus.OK);
    }
}
