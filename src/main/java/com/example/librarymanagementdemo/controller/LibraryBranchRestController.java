package com.example.librarymanagementdemo.controller;
import com.example.librarymanagementdemo.dto.BookDTO;
import com.example.librarymanagementdemo.dto.LibraryBranchDTO;
import com.example.librarymanagementdemo.entity.Author;
import com.example.librarymanagementdemo.entity.Book;
import com.example.librarymanagementdemo.entity.LibraryBranch;
import com.example.librarymanagementdemo.service.BookService;
import com.example.librarymanagementdemo.service.LibraryBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/librarybranches")
public class LibraryBranchRestController {

    private LibraryBranchService libraryBranchService;
    private BookService bookService;

    @Autowired
    public LibraryBranchRestController(LibraryBranchService libraryBranchService, BookService bookService) {
        this.libraryBranchService = libraryBranchService;
        this.bookService = bookService;
    }

    @GetMapping
    public List<LibraryBranchDTO> findAll() {

        System.out.println("\nWill return all branches in db.");

        List<LibraryBranchDTO> libraryBranchDTOs = libraryBranchService.findAll().stream()
                .map(libraryBranchService::convertLibraryBranchEntityToLibraryBranchDTO)
                .collect(Collectors.toList());

        return libraryBranchDTOs;
    }

    @GetMapping("/{libraryBranchId}")
    public LibraryBranchDTO getLibraryBranch(@PathVariable int libraryBranchId) {

        System.out.println("\nWill try to return library branch with id: " + libraryBranchId);

        LibraryBranch libraryBranch = libraryBranchService.findById(libraryBranchId);

        if (libraryBranch == null) {
            throw new RuntimeException("Couldn't find library branch with id: " + libraryBranchId);
        }

        System.out.println("\nLibrary branch with id " + libraryBranchId + " is found.");
        LibraryBranchDTO libraryBranchDTO =libraryBranchService.convertLibraryBranchEntityToLibraryBranchDTO(libraryBranch);

        return libraryBranchDTO;
    }

    @GetMapping("/{libraryBranchId}/books")
    public List<BookDTO> getBookOfLibraryBranch(@PathVariable int libraryBranchId){
        System.out.println("\nWill try to find library branch with id " + libraryBranchId + " to return its books.");

        LibraryBranch libraryBranch = libraryBranchService.findById(libraryBranchId);

        if(libraryBranch == null){
            throw new RuntimeException("Cannot return books. Couldn't find library branch with id: " + libraryBranchId);
        }
        else{

            System.out.println("\nWill return all books of library branch with id " + libraryBranchId);

            List<BookDTO> bookDTOs = bookService.findByLibraryBranch(libraryBranch).stream()
                    .map(bookService::convertBookEntityToBookDTO)
                    .collect(Collectors.toList());

            return bookDTOs;
        }
    }

    @PostMapping
    public LibraryBranchDTO addLibraryBranch(@RequestBody LibraryBranchDTO libraryBranchDTO) {

        //for debug purposes
        System.out.println("\nWill add a library branch to the database.");

        libraryBranchDTO.setId(0); //to force a save of new item
        List<Book> books = new ArrayList<>();

        if(libraryBranchDTO.getBookIds() != null) {
            books = libraryBranchDTO.getBookIds().stream()
                    .map(bookService::findById)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }
        LibraryBranch libraryBranch = libraryBranchService.convertLibraryBranchDTOToLibraryBranchEntity(libraryBranchDTO);

        LibraryBranch branchInDB = libraryBranchService.setBooksAndSaveLibraryBranch(libraryBranch, books);

        System.out.println("Saved library branch: " + branchInDB);
        LibraryBranchDTO returnLibraryBranchDTO = libraryBranchService.convertLibraryBranchEntityToLibraryBranchDTO(branchInDB);

        return returnLibraryBranchDTO;
    }

    @PutMapping
    public LibraryBranchDTO updateLibraryBranch(@RequestBody LibraryBranchDTO libraryBranchDTO) {

        System.out.println("\nWill try to update a library branch from database.");

        LibraryBranch libraryBranch = new LibraryBranch();
        if(libraryBranchDTO.getId() != 0){
            libraryBranch = libraryBranchService.findById(libraryBranchDTO.getId());

            if(libraryBranch == null){
                throw new RuntimeException("Cannot update librarybranch. No librarybranch exists with id: " + libraryBranchDTO.getId());
            }
        }
        else{
            libraryBranch.setId(0);
        }

        libraryBranch = libraryBranchService.updateLibraryBranchPartially(libraryBranch, libraryBranchDTO);
        LibraryBranch libraryBranchInDB = new LibraryBranch();

        if(libraryBranchDTO.getBookIds() != null){
            List<Book> books = libraryBranchDTO.getBookIds().stream()
                    .map(bookService::findById)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            try {
                libraryBranchInDB = libraryBranchService.setBooksAndSaveLibraryBranch(libraryBranch, books);
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }

        }
        else{
            libraryBranchInDB = libraryBranchService.setBooksAndSaveLibraryBranch(libraryBranch, null);
        }

        System.out.println("Updated library branch: " + libraryBranchInDB);
        LibraryBranchDTO returnLibraryBranchDTO = libraryBranchService.convertLibraryBranchEntityToLibraryBranchDTO(libraryBranchInDB);

        return returnLibraryBranchDTO;
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
}
