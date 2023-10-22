package com.example.librarymanagementdemo.entity;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="title")
    private String title;

    @Column(name="isbn")
    private String ISBN;

    @Column(name="publication_year")
    private int publicationYear;

    @Column(name="genre")
    private String genre;

    @Column(name="availability_status")
    private boolean isAvailable;

    @Column(name="multiple_author")
    private boolean haveMultipleAuthors;


    //book-librarybranch relationship
    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="library_branch_id")
    private LibraryBranch libraryBranch;

    //book-author relationship
    @ManyToMany(fetch=FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    @JoinTable(name="book_author",
            joinColumns = @JoinColumn(name="book_id"),
            inverseJoinColumns = @JoinColumn(name="author_id"))
    private List<Author> authors;


    //book-checkout relationship

    public Book(){}

    public Book(String title, String ISBN, int publicationYear, String genre, boolean isAvailable, boolean haveMultipleAuthors) {
        this.title = title;
        this.ISBN = ISBN;
        this.publicationYear = publicationYear;
        this.genre = genre;
        this.isAvailable = isAvailable;
        this.haveMultipleAuthors = haveMultipleAuthors;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public boolean isHaveMultipleAuthors() {
        return haveMultipleAuthors;
    }

    public void setHaveMultipleAuthors(boolean haveMultipleAuthors) {
        this.haveMultipleAuthors = haveMultipleAuthors;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public void addAuthor(Author theAuthor){
        if(authors == null){
            authors = new ArrayList<>();
        }
        authors.add(theAuthor);
    }

    public LibraryBranch getLibraryBranch() {
        return libraryBranch;
    }

    public void setLibraryBranch(LibraryBranch libraryBranch) {
        this.libraryBranch = libraryBranch;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", ISBN='" + ISBN + '\'' +
                ", publicationYear=" + publicationYear +
                ", genre='" + genre + '\'' +
                ", isAvailable=" + isAvailable +
                ", haveMultipleAuthors=" + haveMultipleAuthors +
                ", libraryBranch=" + libraryBranch +
                ", authors=" + authors +
                '}';
    }
}
