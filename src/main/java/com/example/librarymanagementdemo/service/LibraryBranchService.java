package com.example.librarymanagementdemo.service;

import com.example.librarymanagementdemo.dto.LibraryBranchDTO;
import com.example.librarymanagementdemo.entity.Book;
import com.example.librarymanagementdemo.entity.LibraryBranch;

import java.util.List;

public interface LibraryBranchService {


    List<LibraryBranch> findAllWithOptionalFilter(String name, String location, Integer capacity);

    LibraryBranch findById(int id);

    LibraryBranch save(LibraryBranch libraryBranch);

    LibraryBranch setBooksOfLibraryBranch(LibraryBranch libraryBranch, List<Book> books);

    LibraryBranch setBooksAndSaveLibraryBranch(LibraryBranch libraryBranch, List<Book> books);

    void deleteById(int id);

    LibraryBranch convertLibraryBranchDTOToLibraryBranchEntity(LibraryBranchDTO dto);

    LibraryBranchDTO convertLibraryBranchEntityToLibraryBranchDTO(LibraryBranch libraryBranch);

    LibraryBranch updateLibraryBranchPartially(LibraryBranch libraryBranch, LibraryBranchDTO libraryBranchDTO);
}
