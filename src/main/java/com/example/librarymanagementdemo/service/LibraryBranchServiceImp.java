package com.example.librarymanagementdemo.service;

import com.example.librarymanagementdemo.dto.CheckoutDTO;
import com.example.librarymanagementdemo.dto.LibraryBranchDTO;
import com.example.librarymanagementdemo.entity.Book;
import com.example.librarymanagementdemo.entity.Checkout;
import com.example.librarymanagementdemo.entity.LibraryBranch;
import com.example.librarymanagementdemo.repository.LibraryBranchRepository;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LibraryBranchServiceImp implements LibraryBranchService{

    private LibraryBranchRepository libraryBranchRepository;

    @Autowired
    public LibraryBranchServiceImp(LibraryBranchRepository libraryBranchRepository) {
        this.libraryBranchRepository = libraryBranchRepository;
    }

    @Override
    public List<LibraryBranch> findAll() {
        return libraryBranchRepository.findAll(); //if no branches found, returns empty list
    }

    @Override
    public LibraryBranch findById(int id) {
        Optional<LibraryBranch> result = libraryBranchRepository.findById(id);

        LibraryBranch libraryBranch = null;

        if (result.isPresent()) {
            libraryBranch = result.get();
        }

        /*
        else {
            throw new RuntimeException("Couldn't find library branch with id: " + id);
        }
        */

        return libraryBranch;
    }

    @Override
    public LibraryBranch save(LibraryBranch libraryBranch) { //can be used both in update and create
        return libraryBranchRepository.save(libraryBranch);
    }

    @Override
    public void deleteById(int id) {
       libraryBranchRepository.deleteById(id);
    }

    @Override
    public LibraryBranch findByBookId(int bookId) {
        return null;

        //will be implemented after book part is started
    }

    @Override
    public LibraryBranch convertLibraryBranchDTOToLibraryBranchEntity(LibraryBranchDTO dto) {

        LibraryBranch libraryBranch = new LibraryBranch();

        libraryBranch.setId(dto.getId());
        libraryBranch.setName(dto.getName());
        libraryBranch.setLocation(dto.getLocation());
        libraryBranch.setCapacity(dto.getCapacity());

        //books will be set after checks done

        return libraryBranch;

    }

    @Override
    public LibraryBranchDTO convertLibraryBranchEntityToLibraryBranchDTO(LibraryBranch libraryBranch) {

        LibraryBranchDTO dto = new LibraryBranchDTO();

        dto.setId(libraryBranch.getId());
        dto.setName(libraryBranch.getName());
        dto.setLocation(libraryBranch.getLocation());
        dto.setCapacity(libraryBranch.getCapacity());

        if(libraryBranch.getBooks() == null){
            List<Integer> emptyList = new ArrayList<>();
            dto.setBookIds(emptyList);
        }
        else{
            List<Integer> emptyList = new ArrayList<>();
            dto.setBookIds(libraryBranch.getBooks().stream()
                    .map(Book::getId)
                    .collect(Collectors.toList()));
        }

        return dto;

    }

    @Override
    public LibraryBranch updateLibraryBranchPartially(LibraryBranch libraryBranch, LibraryBranchDTO libraryBranchDTO) {
        return null;
    }

}
