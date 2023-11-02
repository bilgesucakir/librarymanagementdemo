package com.example.librarymanagementdemo.dto;

import com.example.librarymanagementdemo.entity.Checkout;

public class CheckoutDTO {

    private Checkout checkout;
    private int userId;
    private int bookId;

    public Checkout getCheckout() {
        return checkout;
    }

    public void setCheckout(Checkout checkout) {
        this.checkout = checkout;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public CheckoutDTO(){}

    @Override
    public String toString() {
        return "CheckoutDTO{" +
                "checkout=" + checkout +
                ", userId=" + userId +
                ", bookId=" + bookId +
                '}';
    }
}
