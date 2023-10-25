package com.example.librarymanagementdemo.service;

import com.example.librarymanagementdemo.entity.LibraryBranch;
import com.example.librarymanagementdemo.repository.LibraryBranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        else {
            throw new RuntimeException("Couldn't find library branch with id: " + id);
        }

        return libraryBranch;
    }

    @Override
    public LibraryBranch save(LibraryBranch libraryBranch) { //can be used both in update and create
        return libraryBranchRepository.save(libraryBranch);
    }

    @Override
    public void deleteById(int id) {
        if (libraryBranchRepository.existsById(id)){
            libraryBranchRepository.deleteById(id);
        }
        else{
            throw new RuntimeException("Deletion failed. Couldn't find library branch with id: " + id);
        }

    }
}
