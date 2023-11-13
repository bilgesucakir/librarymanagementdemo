package com.example.librarymanagementdemo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class CheckoutDTO {

    private int id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date checkedOutDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dueDate;
    private Integer userId;
    private Integer bookId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCheckedOutDate() {
        return checkedOutDate;
    }

    public void setCheckedOutDate(Date checkedOutDate) {
        this.checkedOutDate = checkedOutDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public CheckoutDTO(){}

    @Override
    public String toString() {
        return "CheckoutDTO{" +
                "id=" + id +
                ", checkedOutDate=" + checkedOutDate +
                ", dueDate=" + dueDate +
                ", userId=" + userId +
                ", bookId=" + bookId +
                '}';
    }
}
