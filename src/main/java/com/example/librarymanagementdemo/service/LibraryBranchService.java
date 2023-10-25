package com.example.librarymanagementdemo.service;

import com.example.librarymanagementdemo.entity.LibraryBranch;

import java.util.List;

public interface LibraryBranchService {


    List<LibraryBranch> findAll();

    LibraryBranch findById(int id);

    LibraryBranch save(LibraryBranch libraryBranch);

    void deleteById(int id);

    LibraryBranch findByBookId(int bookId); //will be added

    //
}
