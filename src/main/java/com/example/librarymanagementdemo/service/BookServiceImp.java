package com.example.librarymanagementdemo.service;

import com.example.librarymanagementdemo.dto.BookDTO;
import com.example.librarymanagementdemo.entity.Author;
import com.example.librarymanagementdemo.entity.Book;
import com.example.librarymanagementdemo.entity.Checkout;
import com.example.librarymanagementdemo.entity.LibraryBranch;
import com.example.librarymanagementdemo.repository.BookRepository;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImp implements  BookService{

    private BookRepository bookRepository;

    @Autowired
    public BookServiceImp(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(int id) {
        Optional<Book> result = bookRepository.findById(id);

        Book book = null;

        if (result.isPresent()) {
            book = result.get();
        }

        return book;
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteById(int id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<Book> findByAuthor(Author author) {
        return bookRepository.findByAuthorsContaining(author);
    }

    @Override
    public List<Book> findByLibraryBranch(LibraryBranch libraryBranch) {
        return bookRepository.findByLibraryBranch(libraryBranch);
    }

    @Override
    public Book setLibraryBranchOfBook(Book book, LibraryBranch libraryBranch) {
        book.setLibraryBranch(libraryBranch);

        return book;
    }

    @Override
    public Book setCheckoutsOfBook(Book book, List<Checkout> checkouts) {
        book.setCheckouts(checkouts);

        return book;
    }

    @Override
    public Book setAuthorsOfBook(Book book, List<Author> authors) {
        book.setAuthors(authors);

        return book;
    }

    @Override
    public Book setFieldsAndSaveBook(Book book, LibraryBranch libraryBranch, List<Checkout> checkouts, List<Author> authors) {

        //libraryBranch cannot be null
        Book tempBook = setLibraryBranchOfBook(book, libraryBranch);

        if(checkouts != null){
            tempBook = setCheckoutsOfBook(tempBook, checkouts);
        }
        if(authors != null){
            tempBook = setAuthorsOfBook(tempBook, authors);
        }

        //call save method
        return save(tempBook);
    }

    @Override
    public Book convertBookDTOToBookEntity(BookDTO dto) {
        Book book = dto.getBook();

        return book;
    }


}
