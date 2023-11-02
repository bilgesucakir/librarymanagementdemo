package com.example.librarymanagementdemo.service;

import com.example.librarymanagementdemo.dto.AuthorDTO;
import com.example.librarymanagementdemo.entity.Author;
import com.example.librarymanagementdemo.entity.Book;
import com.example.librarymanagementdemo.entity.LibraryUser;
import com.example.librarymanagementdemo.repository.AuthorRepository;
import com.example.librarymanagementdemo.repository.LibraryUserRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImp implements AuthorService{


    private AuthorRepository authorRepository;

    private EntityManager entityManager;

    @Autowired //for auto dependency injection --> constructor injeciton
    public AuthorServiceImp(AuthorRepository authorRepository, EntityManager entityManager) {
        this.authorRepository = authorRepository;
        this.entityManager = entityManager;
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

    @Override
    public List<Author> findByBook(Book book) {
        return authorRepository.findByBooksContaining(book);
    }

    @Override
    public List<Author> findByFilter(String name, Date birthdate, String nationality) {


        //entity manager part

        //check if params are null, if not null, filter accordingly

        return null;
    }

    @Override
    public Author convertAuthorDTOToAuthorEntity(AuthorDTO dto) {
        return null;
    }
}
