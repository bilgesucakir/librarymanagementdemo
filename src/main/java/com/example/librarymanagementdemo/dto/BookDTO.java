package com.example.librarymanagementdemo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public class BookDTO {


    private int id;

    @Pattern(regexp = "^(?:(?!\\s*$).+)?$", message = "Book title must contain at least one non-whitespace character")
    @Size(min=2, message="Book title must be at least two characters long")
    private String title;

    @Pattern(regexp = "^\\d{13}$", message = "ISBN must be a string of length 13 and contain only numeric characters")
    private String ISBN;

    @Min(value=0, message="Invalid publication year format")
    private Integer publicationYear;

    private String genre;

    private Boolean available;

    private Boolean multipleAuthors;

    @NotNull(message="Library branch id is not provided")
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

    public Boolean isAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Boolean isMultipleAuthors() {
        return multipleAuthors;
    }

    public void setMultipleAuthors(Boolean multipleAuthors) {
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
