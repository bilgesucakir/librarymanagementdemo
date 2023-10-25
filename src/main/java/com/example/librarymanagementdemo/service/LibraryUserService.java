package com.example.librarymanagementdemo.service;

import com.example.librarymanagementdemo.entity.LibraryBranch;
import com.example.librarymanagementdemo.entity.LibraryUser;

import java.util.List;

public interface LibraryUserService {

    List<LibraryUser> findAll();

    LibraryUser findById(int id);

    LibraryUser save(LibraryUser libraryUser);

    void deleteById(int id);
}
