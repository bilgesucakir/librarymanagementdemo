package com.example.librarymanagementdemo.service;

import com.example.librarymanagementdemo.dto.BookDTO;
import com.example.librarymanagementdemo.entity.Author;
import com.example.librarymanagementdemo.entity.Book;
import com.example.librarymanagementdemo.entity.Checkout;
import com.example.librarymanagementdemo.entity.LibraryBranch;
import com.example.librarymanagementdemo.exception.EntityFieldValidationException;
import com.example.librarymanagementdemo.exception.EntityIdNullException;
import com.example.librarymanagementdemo.exception.EntityNotFoundException;
import com.example.librarymanagementdemo.repository.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImp implements  BookService{

    private BookRepository bookRepository;
    private EntityManager entityManager;

    @Autowired
    public BookServiceImp(BookRepository bookRepository, EntityManager entityManager) {
        this.bookRepository = bookRepository;
        this.entityManager = entityManager;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(int id) {
        Optional<Book> result = bookRepository.findById(id);

        Book book;

        if (result.isPresent()) {
            book = result.get();
        }
        else{
            throw new EntityNotFoundException("Couldn't find book with id: " + id);
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
    public List<Book> findByFilter(String title, String isbn, Integer publicationYear, String genre, String available, String multipleAuthors) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> query = builder.createQuery(Book.class);
        Root<Book> root = query.from(Book.class);

        List<Predicate> predicates = new ArrayList<>();

        if(title != null){
            predicates.add(builder.equal(root.get("title"), title));
        }
        if(isbn != null){
            predicates.add(builder.equal(root.get("ISBN"), isbn));
        }
        if(publicationYear != null){
            predicates.add(builder.equal(root.get("publicationYear"), publicationYear));
        }
        if(genre != null){
            predicates.add(builder.equal(root.get("genre"), genre));
        }
        if(available != null){
            if(available.equals("true")){
                predicates.add(builder.equal(root.get("available"), true));
            }
            else if(available.equals("false")){
                predicates.add(builder.equal(root.get("available"), false));
            }
            else{
                throw new RuntimeException("Filter parameter in wrong format.");
            }
        }
        if(multipleAuthors != null){
            if(multipleAuthors.equals("true")){
                predicates.add(builder.equal(root.get("multipleAuthors"), true));
            }
            else if(multipleAuthors.equals("false")){
                predicates.add(builder.equal(root.get("multipleAuthors"), false));
            }
            else{
                throw new RuntimeException("Filter parameter in wrong format.");
            }

        }

        if(!predicates.isEmpty()){
            query.where(builder.and(predicates.toArray(new Predicate[0])));
        }

        TypedQuery<Book> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }

    @Override
    public Book setLibraryBranchOfBook(Book book, LibraryBranch libraryBranch) {
        book.setLibraryBranch(libraryBranch);

        return book;
    }

    @Override
    public Book setAuthorsOfBook(Book book, List<Author> authors) {
        book.setAuthors(authors);

        return book;
    }

    @Override
    public Book setFieldsAndSaveBook(Book book, LibraryBranch libraryBranch, List<Author> authors) {

        Book tempBook = book;

        if(libraryBranch != null){
            tempBook = setLibraryBranchOfBook(book, libraryBranch);
        }


        if(authors != null){
            tempBook = setAuthorsOfBook(tempBook, authors);
        }

        //call save method
        return save(tempBook);
    }

    @Override
    public Book convertBookDTOToBookEntity(BookDTO dto) {
        Book book = new Book();

        book.setId(dto.getId());
        book.setTitle(dto.getTitle());
        book.setAvailable(dto.isAvailable());
        book.setGenre(dto.getGenre());
        book.setISBN(dto.getISBN());
        book.setPublicationYear(dto.getPublicationYear());
        book.setMultipleAuthors(dto.isMultipleAuthors());

        return book;
    }

    @Override
    public BookDTO convertBookEntityToBookDTO(Book book) {
        BookDTO dto = new BookDTO();

        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAvailable(book.isAvailable());
        dto.setGenre(book.getGenre());
        dto.setISBN(book.getISBN());
        dto.setPublicationYear(book.getPublicationYear());
        dto.setMultipleAuthors(book.isMultipleAuthors());

        if(book.getAuthors() == null){
            List<Integer> emptyList = new ArrayList<>();
            dto.setAuthorIds(emptyList);
        }
        else{
            dto.setAuthorIds(book.getAuthors().stream()
                    .map(Author::getId)
                    .collect(Collectors.toList()));
        }

        dto.setLibraryBranchId(book.getLibraryBranch().getId());

        if(book.getCheckouts() == null){
            List<Integer> emptyList = new ArrayList<>();
            dto.setCheckoutIds(emptyList);
        }
        else{
            dto.setCheckoutIds(book.getCheckouts().stream()
                    .map(Checkout::getId)
                    .collect(Collectors.toList()));

        }
        return dto;
    }

    @Override
    public Book updateBookPartially(Book book, BookDTO bookDTO) {

        //id already exists
        if(bookDTO.getISBN() != null){
            book.setISBN(bookDTO.getISBN());
        }
        if(bookDTO.isAvailable() != book.isAvailable()){
            book.setAvailable(bookDTO.isAvailable());
        }
        if(bookDTO.isMultipleAuthors() != book.isMultipleAuthors()){
            book.setMultipleAuthors(bookDTO.isMultipleAuthors());
        }
        if(bookDTO.getTitle() != null){
            book.setTitle(bookDTO.getTitle());
        }
        if(bookDTO.getPublicationYear() != null){
            book.setPublicationYear(bookDTO.getPublicationYear());
        }
        if(bookDTO.getGenre() != null){
            book.setGenre(bookDTO.getGenre());
        }

        return book;
    }

    @Override
    public void validateAddBook(BookDTO bookDTO) {

        if(bookDTO.getLibraryBranchId() == null){
            throw new EntityIdNullException("Library branch id is not provided.");
        }
        if(bookDTO.getTitle() != null) {
            if (bookDTO.getTitle().trim().isEmpty()) {
                throw new EntityFieldValidationException("Wrong book title format. Book title only contains whitespaces");
            }
        }
        if(bookDTO.getISBN() != null){
            if(!bookDTO.getISBN().matches("\\d{13}")){  //not being a string of length 13 and only numeric chars
                throw new EntityFieldValidationException("Wrong ISBN format given.");
            }
        }
    }

    @Override
    public void validateUpdateBook(BookDTO bookDTO) {
        if(bookDTO.getTitle() != null) {
            if (bookDTO.getTitle().trim().isEmpty()) {
                throw new EntityFieldValidationException("Wrong book title format. Book title only contains whitespaces");
            }
        }
        if(bookDTO.getISBN() != null){
            if(!bookDTO.getISBN().matches("\\d{13}")){  //not being a string of length 13 and only numeric chars
                throw new EntityFieldValidationException("Wrong ISBN format given.");
            }
        }
    }

}
