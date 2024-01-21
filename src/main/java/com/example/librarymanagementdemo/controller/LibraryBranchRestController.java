package com.example.librarymanagementdemo.controller;

import com.example.librarymanagementdemo.dto.BookDTO;
import com.example.librarymanagementdemo.dto.LibraryBranchDTO;
import com.example.librarymanagementdemo.entity.Book;
import com.example.librarymanagementdemo.entity.LibraryBranch;
import com.example.librarymanagementdemo.service.BookService;
import com.example.librarymanagementdemo.service.LibraryBranchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<LibraryBranchDTO>> findAllWithOptionalFilter(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "location", required = false) String location,
            @RequestParam(name = "capacity", required = false) Integer capacity
    ){
        List<LibraryBranch> libraryBranches = libraryBranchService.findAllWithOptionalFilter(name, location, capacity);

        List<LibraryBranchDTO> libraryBranchDTOs = libraryBranches.stream()
                .map(libraryBranchService::convertLibraryBranchEntityToLibraryBranchDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(libraryBranchDTOs, HttpStatus.OK);
    }

    @GetMapping("/{libraryBranchId}")
    public ResponseEntity<LibraryBranchDTO> getLibraryBranch(@PathVariable int libraryBranchId) {

        LibraryBranch libraryBranch = libraryBranchService.findById(libraryBranchId);

        LibraryBranchDTO libraryBranchDTO =libraryBranchService.convertLibraryBranchEntityToLibraryBranchDTO(libraryBranch);

        return new ResponseEntity<>(libraryBranchDTO, HttpStatus.OK);
    }

    @GetMapping("/{libraryBranchId}/books")
    public ResponseEntity<List<BookDTO>> getBookOfLibraryBranch(@PathVariable int libraryBranchId){

        LibraryBranch libraryBranch = libraryBranchService.findById(libraryBranchId);

        List<BookDTO> bookDTOs = bookService.findByLibraryBranch(libraryBranch).stream()
                .map(bookService::convertBookEntityToBookDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(bookDTOs, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LibraryBranchDTO> addLibraryBranch(@Valid @RequestBody LibraryBranchDTO libraryBranchDTO) {

        libraryBranchDTO.setId(0);
        List<Book> books = new ArrayList<>();

        if(libraryBranchDTO.getBookIds() != null) {
            books = libraryBranchDTO.getBookIds().stream()
                    .map(bookService::findById)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }
        LibraryBranch libraryBranch = libraryBranchService.convertLibraryBranchDTOToLibraryBranchEntity(libraryBranchDTO);

        LibraryBranch branchInDB = libraryBranchService.setBooksAndSaveLibraryBranch(libraryBranch, books);

        LibraryBranchDTO returnLibraryBranchDTO = libraryBranchService.convertLibraryBranchEntityToLibraryBranchDTO(branchInDB);

        return new ResponseEntity<>(returnLibraryBranchDTO, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<LibraryBranchDTO> updateLibraryBranch(@Valid @RequestBody LibraryBranchDTO libraryBranchDTO) {

        LibraryBranch libraryBranch = libraryBranchService.findById(libraryBranchDTO.getId());

        libraryBranch = libraryBranchService.updateLibraryBranchPartially(libraryBranch, libraryBranchDTO);

        LibraryBranch libraryBranchInDB;
        if(libraryBranchDTO.getBookIds() != null){
            List<Book> books = libraryBranchDTO.getBookIds().stream()
                    .map(bookService::findById)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            libraryBranchInDB = libraryBranchService.setBooksAndSaveLibraryBranch(libraryBranch, books);
        }
        else{
            libraryBranchInDB = libraryBranchService.setBooksAndSaveLibraryBranch(libraryBranch, null);
        }

        LibraryBranchDTO returnLibraryBranchDTO = libraryBranchService.convertLibraryBranchEntityToLibraryBranchDTO(libraryBranchInDB);
        return new ResponseEntity<>(returnLibraryBranchDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{libraryBranchId}")
    public ResponseEntity<String> deleteLibraryBranch(@PathVariable int libraryBranchId) {

        libraryBranchService.findById(libraryBranchId);

        libraryBranchService.deleteById(libraryBranchId);

        return new ResponseEntity<>("Deleted library branch id: " + libraryBranchId, HttpStatus.OK);
    }
}
