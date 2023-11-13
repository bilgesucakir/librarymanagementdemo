package com.example.librarymanagementdemo.service;

import com.example.librarymanagementdemo.dto.LibraryUserDTO;
import com.example.librarymanagementdemo.entity.LibraryUser;

import java.util.List;

public interface LibraryUserService {

    List<LibraryUser> findAll();

    LibraryUser findById(int id);

    LibraryUser save(LibraryUser libraryUser);

    void deleteById(int id);

    LibraryUser convertLibraryUserDTOToLibraryUserEntity(LibraryUserDTO dto);

    LibraryUserDTO convertLibraryUserEntityTolibraryUserDTO(LibraryUser libraryUser);

    LibraryUser updateLibraryUserPartially(LibraryUser libraryUser, LibraryUserDTO libraryUserDTO);

    boolean libraryUserExistsWithUsernameOrNot(String username);

    boolean libraryUserExistsWithEmailOrNot(String email);
}
