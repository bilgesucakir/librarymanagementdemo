package com.example.librarymanagementdemo.service;

import com.example.librarymanagementdemo.entity.Author;
import com.example.librarymanagementdemo.entity.LibraryUser;
import com.example.librarymanagementdemo.repository.AuthorRepository;
import com.example.librarymanagementdemo.repository.LibraryUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImp implements AuthorService{


    private AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImp(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author findById(int id) {
        Optional<Author> result = authorRepository.findById(id);

        Author author = null;

        if(result.isPresent()){
            author = result.get();
        }

        return author;
    }

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public void deleteById(int id) {
        authorRepository.deleteById(id);
    }
}
