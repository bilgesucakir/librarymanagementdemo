package com.example.librarymanagementdemo.dto;

import com.example.librarymanagementdemo.entity.LibraryUser;

import java.net.Inet4Address;
import java.util.List;

public class LibraryUserDTO {

    private LibraryUser libraryUser;

    private List<Integer> checkoutIds;

    public LibraryUserDTO(){}

    public LibraryUser getLibraryUser() {
        return libraryUser;
    }

    public void setLibraryUser(LibraryUser libraryUser) {
        this.libraryUser = libraryUser;
    }

    public List<Integer> getCheckoutIds() {
        return checkoutIds;
    }

    public void setCheckoutIds(List<Integer> checkoutIds) {
        this.checkoutIds = checkoutIds;
    }

    @Override
    public String toString() {
        return "LibraryUserDTO{" +
                "libraryUser=" + libraryUser +
                ", checkoutIds=" + checkoutIds +
                '}';
    }
}
