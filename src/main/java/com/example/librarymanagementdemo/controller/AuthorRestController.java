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
    public List<AuthorDTO> findAllOrFilter(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "birthdate", required = false) Date birthdate,
            @RequestParam(name = "nationality", required = false) String nationality

    ){ //gets param from request body with name

        List<Author> authors = new ArrayList<>();

        if(name == null && birthdate == null && nationality == null){
            //no filtering, calling findAll

            System.out.println("\nWill return all authors in db.");

            authors =  authorService.findAll();
        }
        else{
            //some filtering exists

            System.out.print("\nWill return all authors in db with following filters: ");
            if(name!= null){
                System.out.print("name=" + name+ " ");
            }
            if(birthdate!= null){
                System.out.print("birthdate=" + birthdate+ " ");
            }
            if(nationality!= null){
                System.out.print("nationality=" + nationality);
            }

            authors =  authorService.findByFilter(name, birthdate, nationality);
        }

        List<AuthorDTO> authorDTOs = new ArrayList<>();

        for(Author author : authors){
            AuthorDTO authorDTO = authorService.convertAuthorEntityToAuthorDTO(author);

            authorDTOs.add(authorDTO);
        }

        return authorDTOs;

    }

    @GetMapping("/{authorId}")
    public AuthorDTO getAuthor(@PathVariable int authorId){

        System.out.println("\nWill try to return author with id: " + authorId);

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
        System.out.println("\nWill try to find author with id " + authorId + " to return its books.");

        Author author = authorService.findById(authorId);

        if(author == null){
            throw new RuntimeException("Cannot return books. Couldn't find author with id: " + authorId);
        }
        else{

            System.out.println("\nWill return all books of author with id " + authorId);


            List<Book> books = bookService.findByAuthor(author);
            List<BookDTO> bookDTOs = new ArrayList<>();

            for(Book book : books){
                BookDTO bookDTO = bookService.convertBookEntityToBookDTO(book);

                bookDTOs.add(bookDTO);
            }

            return bookDTOs;
        }

    }

    @PostMapping
    public AuthorDTO addAuthor(@RequestBody AuthorDTO authorDTO) { //get entity params from body

        //for debug purposes
        System.out.println("\nWill add an author to the database.");

        authorDTO.setId(0);

        Author author = authorService.convertAuthorDTOToAuthorEntity(authorDTO);


        List<Integer> bookIds = authorDTO.getBookIds();
        List<Book> books = new ArrayList<>();

        //book list check
        for(int id: bookIds){
            Book book = bookService.findById(id);

            if (author == null) {
                System.out.println("No book found with id: " + id + ". Will not be added to books list.");
            } else{
                System.out.println("Book find with id: " + id + ", as " + book + ".\nWill be added to books list.");
                books.add(book);
            }
        }

        Author authorInDB = authorService.setBooksAndSaveAuthor(author, books);

        System.out.println("Saved author: " + authorInDB);

        AuthorDTO returnAuthorDTO = authorService.convertAuthorEntityToAuthorDTO(authorInDB);

        return returnAuthorDTO;

    }

    @PutMapping
    public AuthorDTO updateAuthor(@RequestBody AuthorDTO authorDTO) {

        System.out.println("\nWill try to update an author from database.");

        /*Author authorInDB = authorService.save(author);

        System.out.println("Updated author: " + authorInDB);

        return authorInDB;*/

        Author author = new Author();
        //if author exists or not
        if(authorDTO.getId() != null){ //replace existing instance

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
            List<Integer> bookIds = authorDTO.getBookIds();
            List<Book> books = new ArrayList<>();

            //book list check
            for(int id: bookIds){
                Book book = bookService.findById(id);

                if(book == null){
                    System.out.println("No book found with id: " + id + ". Will not be added to books list.");

                }
                else{
                    System.out.println("Book find with id: " + id + ", as " + book + ".\nWill be added to books list.");
                    books.add(book);
                }
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
