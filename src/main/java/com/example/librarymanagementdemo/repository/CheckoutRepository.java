package com.example.librarymanagementdemo.repository;

import com.example.librarymanagementdemo.entity.Book;
import com.example.librarymanagementdemo.entity.Checkout;
import com.example.librarymanagementdemo.entity.LibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface CheckoutRepository extends JpaRepository<Checkout, Integer> {

    List<Checkout> findByBook(Book book);

    List<Checkout> findByLibraryUser(LibraryUser libraryUser);

    @Query("SELECT c FROM Checkout c " +
            "WHERE (:active is null or c.active = :active) " +
            "AND (:checkedOutDateBefore is null or c.checkedOutDate < :checkedOutDateBefore) " +
            "AND (:checkedOutDateAfter is null or c.checkedOutDate > :checkedOutDateAfter) " +
            "AND (:dueDateBefore is null or c.dueDate < :dueDateBefore) " +
            "AND (:dueDateAfter is null or c.dueDate > :dueDateAfter)")
    List<Checkout> findByActiveEqualsAndCheckedOutDateBeforeAndCheckedOutDateAfterAndDueDateBeforeAndDueDateAfter(Boolean active,
                                                                                                                  Date checkedOutDateBefore,
                                                                                                                  Date checkedOutDateAfter,
                                                                                                                  Date dueDateBefore,
                                                                                                                  Date dueDateAfter);

}
