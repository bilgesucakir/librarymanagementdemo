package com.example.librarymanagementdemo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="checkout")
public class Checkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name="checked_out_date")
    private Date checkedOutDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name="due_date")
    private Date dueDate;

    @Column(name="active")
    private Boolean active;

    //checkout-libraryuser relationship //jsonbackreference?
    @ManyToOne(cascade= {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="user_id")
    @JsonBackReference
    private LibraryUser libraryUser;

    //checkout-book relationship //json back reference
    @ManyToOne(cascade= {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="book_id")
    @JsonBackReference
    private Book book;

    public Checkout(){}

    public Checkout(Date checkedOutDate, Date dueDate) {
        this.checkedOutDate = checkedOutDate;
        this.dueDate = dueDate;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LibraryUser getLibraryUser() {
        return libraryUser;
    }

    public void setLibraryUser(LibraryUser libraryUser) {
        this.libraryUser = libraryUser;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "Checkout{" +
                "id=" + id +
                ", checkedOutDate=" + checkedOutDate +
                ", dueDate=" + dueDate +
                ", isActive=" + active +
                '}';
    }
}
