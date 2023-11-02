package com.example.librarymanagementdemo.service;

import com.example.librarymanagementdemo.dto.BookDTO;
import com.example.librarymanagementdemo.dto.LibraryBranchDTO;
import com.example.librarymanagementdemo.entity.Book;
import com.example.librarymanagementdemo.entity.LibraryBranch;

import java.util.List;

public interface LibraryBranchService {


    List<LibraryBranch> findAll();

    LibraryBranch findById(int id);

    LibraryBranch save(LibraryBranch libraryBranch);

    void deleteById(int id);

    LibraryBranch findByBookId(int bookId); //will be added

    //

    LibraryBranch convertLibraryBranchDTOToLibraryBranchEntity(LibraryBranchDTO dto);

    LibraryBranchDTO convertLibraryBranchEntityToLibraryBranchDTO(LibraryBranch libraryBranch);

    LibraryBranch updateLibraryBranchPartially(LibraryBranch libraryBranch, LibraryBranchDTO libraryBranchDTO);

}
