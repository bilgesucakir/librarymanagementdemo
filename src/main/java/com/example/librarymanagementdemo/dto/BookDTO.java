package com.example.librarymanagementdemo.dto;

import com.example.librarymanagementdemo.entity.Book;

import java.util.List;

public class BookDTO {

    private Book book;
    private int libraryBranchId;
    private List<Integer> AuthorIds;
    private List<Integer> CheckoutIds;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getLibraryBranchId() {
        return libraryBranchId;
    }

    public void setLibraryBranchId(int libraryBranchId) {
        this.libraryBranchId = libraryBranchId;
    }

    public List<Integer> getAuthorIds() {
        return AuthorIds;
    }

    public void setAuthorIds(List<Integer> authorIds) {
        AuthorIds = authorIds;
    }

    public List<Integer> getCheckoutIds() {
        return CheckoutIds;
    }

    public void setCheckoutIds(List<Integer> checkoutIds) {
        CheckoutIds = checkoutIds;
    }

    public BookDTO(){}

    @Override
    public String toString() {
        return "BookDTO{" +
                "book=" + book +
                ", libraryBranchId=" + libraryBranchId +
                ", AuthorIds=" + AuthorIds +
                ", CheckoutIds=" + CheckoutIds +
                '}';
    }
}
