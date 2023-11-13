package com.example.librarymanagementdemo.repository;

import com.example.librarymanagementdemo.entity.Book;
import com.example.librarymanagementdemo.entity.Checkout;
import com.example.librarymanagementdemo.entity.LibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface CheckoutRepository extends JpaRepository<Checkout, Integer> {

    List<Checkout> findByBook(Book book);

    List<Checkout> findByLibraryUser(LibraryUser libraryUser);

    List<Checkout> findByCheckedOutDateAfter(Date checkedOutDate);

    List<Checkout> findByCheckedOutDateBefore(Date checkedOutDate);

    List<Checkout> findByDueDateAfter(Date dueDate);

    List<Checkout> findByDueDateBefore(Date dueDate);

}
