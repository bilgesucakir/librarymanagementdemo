package com.example.librarymanagementdemo.service;

import com.example.librarymanagementdemo.dto.LibraryUserDTO;
import com.example.librarymanagementdemo.entity.LibraryUser;

import java.util.Date;
import java.util.List;

public interface LibraryUserService {

    List<LibraryUser> findAllWithOptionalFilter(Date registrationDateBefore, Date registrationDateAfter);

    LibraryUser findById(int id);

    LibraryUser save(LibraryUser libraryUser);

    void deleteById(int id);

    LibraryUser convertLibraryUserDTOToLibraryUserEntity(LibraryUserDTO dto);

    LibraryUserDTO convertLibraryUserEntityTolibraryUserDTO(LibraryUser libraryUser);

    LibraryUser updateLibraryUserPartially(LibraryUser libraryUser, LibraryUserDTO libraryUserDTO);

    boolean libraryUserExistsWithUsernameOrNot(String username);

    boolean libraryUserExistsWithEmailOrNot(String email);

    void validateAddLibraryUser(LibraryUserDTO libraryUserDTO);

    void validateUpdateLibraryUser(LibraryUserDTO libraryUserDTO, String username, String email);
}
