package com.example.librarymanagementdemo.dto;

import com.example.librarymanagementdemo.validation.annotation.DateRange;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@DateRange(first = "checkedOutDate", second = "dueDate", message = "Checked out date cannot be later than due date")
public class CheckoutDTO {

    private int id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date checkedOutDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dueDate;

    private boolean isActive;

    @NotNull(message="User id field cannot be null")
    private Integer userId;

    @NotNull(message="Book id field cannot be null")
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

    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
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
                ", isActive=" + isActive +
                ", userId=" + userId +
                ", bookId=" + bookId +
                '}';
    }
}
