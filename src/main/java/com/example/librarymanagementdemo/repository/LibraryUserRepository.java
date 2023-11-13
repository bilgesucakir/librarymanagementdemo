package com.example.librarymanagementdemo.repository;

import com.example.librarymanagementdemo.entity.Checkout;
import com.example.librarymanagementdemo.entity.LibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibraryUserRepository extends JpaRepository<LibraryUser, Integer> {

    List<LibraryUser> findByUsername(String username);

    List<LibraryUser> findByEmail(String email);

    List<LibraryUser> findByCheckoutsContaining(Checkout checkout);

    boolean existsLibraryUserByUsernameEquals(String username);

    boolean existsLibraryUserByEmailEquals(String email);

}
