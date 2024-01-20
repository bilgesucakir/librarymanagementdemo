package com.example.librarymanagementdemo.repository;

import com.example.librarymanagementdemo.entity.Checkout;
import com.example.librarymanagementdemo.entity.LibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface LibraryUserRepository extends JpaRepository<LibraryUser, Integer> {

    boolean existsLibraryUserByUsernameEquals(String username);

    boolean existsLibraryUserByEmailEquals(String email);


    @Query("SELECT lu FROM LibraryUser lu " +
            "WHERE (:registrationDateBefore is null or lu.registrationDate < :registrationDateBefore) " +
            "AND (:registrationDateAfter is null or lu.registrationDate > :registrationDateAfter) ")
    List<LibraryUser> findByRegistrationDateBeforeAndRegistrationDateAfter(Date registrationDateBefore, Date registrationDateAfter);

}
