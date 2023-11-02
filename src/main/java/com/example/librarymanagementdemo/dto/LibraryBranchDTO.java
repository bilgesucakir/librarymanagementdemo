package com.example.librarymanagementdemo.dto;

import com.example.librarymanagementdemo.entity.LibraryBranch;

import java.util.List;

public class LibraryBranchDTO {

    private LibraryBranch libraryBranch;
    private List<Integer> bookIds;

    public LibraryBranchDTO(){}

    public LibraryBranch getLibraryBranch() {
        return libraryBranch;
    }

    public void setLibraryBranch(LibraryBranch libraryBranch) {
        this.libraryBranch = libraryBranch;
    }

    public List<Integer> getBookIds() {
        return bookIds;
    }

    public void setBookIds(List<Integer> bookIds) {
        this.bookIds = bookIds;
    }

    @Override
    public String toString() {
        return "LibraryBranchDTO{" +
                "libraryBranch=" + libraryBranch +
                ", bookIds=" + bookIds +
                '}';
    }
}
