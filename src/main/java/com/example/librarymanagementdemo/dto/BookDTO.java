package com.example.librarymanagementdemo.dto;

import com.example.librarymanagementdemo.entity.Book;
import com.example.librarymanagementdemo.entity.Checkout;
import jakarta.persistence.Column;

import java.util.List;

public class BookDTO {


    private int id;

    private String title;

    private String ISBN;

    private Integer publicationYear;

    private String genre;

    private boolean available;

    private boolean multipleAuthors;

    private Integer libraryBranchId;
    private List<Integer> authorIds;
    private List<Integer> checkoutIds;

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

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isMultipleAuthors() {
        return multipleAuthors;
    }

    public void setMultipleAuthors(boolean multipleAuthors) {
        this.multipleAuthors = multipleAuthors;
    }

    public Integer getLibraryBranchId() {
        return libraryBranchId;
    }

    public void setLibraryBranchId(Integer libraryBranchId) {
        this.libraryBranchId = libraryBranchId;
    }

    public List<Integer> getAuthorIds() {
        return authorIds;
    }

    public void setAuthorIds(List<Integer> authorIds) {
        this.authorIds = authorIds;
    }

    public List<Integer> getCheckoutIds() {
        return checkoutIds;
    }

    public void setCheckoutIds(List<Integer> checkoutIds) {
        this.checkoutIds = checkoutIds;
    }

    public BookDTO(){}


    @Override
    public String toString() {
        return "BookDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", ISBN='" + ISBN + '\'' +
                ", publicationYear=" + publicationYear +
                ", genre='" + genre + '\'' +
                ", available=" + available +
                ", multipleAuthors=" + multipleAuthors +
                ", libraryBranchId=" + libraryBranchId +
                ", authorIds=" + authorIds +
                ", checkoutIds=" + checkoutIds +
                '}';
    }
}
