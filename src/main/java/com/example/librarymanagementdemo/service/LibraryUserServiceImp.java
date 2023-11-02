package com.example.librarymanagementdemo.service;

import com.example.librarymanagementdemo.dto.LibraryUserDTO;
import com.example.librarymanagementdemo.entity.LibraryUser;
import com.example.librarymanagementdemo.repository.LibraryUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryUserServiceImp implements LibraryUserService{

    private LibraryUserRepository libraryUserRepository;

    @Autowired
    public LibraryUserServiceImp(LibraryUserRepository libraryUserRepository) {
        this.libraryUserRepository = libraryUserRepository;
    }

    @Override
    public List<LibraryUser> findAll() {
        return libraryUserRepository.findAll();
    }

    @Override
    public LibraryUser findById(int id) {
        Optional<LibraryUser> result = libraryUserRepository.findById(id);

        LibraryUser libraryUser = null;

        if(result.isPresent()){
            libraryUser = result.get();
        }

        return libraryUser;
    }

    @Override
    public LibraryUser save(LibraryUser libraryUser) {
        return libraryUserRepository.save(libraryUser);
    }

    @Override
    public void deleteById(int id) {
        libraryUserRepository.deleteById(id);
    }

    @Override
    public LibraryUser convertLibraryUserDTOToLibraryUserEntity(LibraryUserDTO dto) {
        return null;
    }
}
