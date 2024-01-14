package com.example.librarymanagementdemo.service;

import com.example.librarymanagementdemo.dto.LibraryUserDTO;
import com.example.librarymanagementdemo.entity.Checkout;
import com.example.librarymanagementdemo.entity.LibraryUser;
import com.example.librarymanagementdemo.exception.EntityFieldValidationException;
import com.example.librarymanagementdemo.exception.EntityNotFoundException;
import com.example.librarymanagementdemo.repository.LibraryUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LibraryUserServiceImp implements LibraryUserService{

    private LibraryUserRepository libraryUserRepository;

    @Autowired
    public LibraryUserServiceImp(LibraryUserRepository libraryUserRepository) {
        this.libraryUserRepository = libraryUserRepository;
    }

    @Override
    public List<LibraryUser> findAll() {
        return libraryUserRepository.findAll();
    }

    @Override
    public LibraryUser findById(int id) {
        Optional<LibraryUser> result = libraryUserRepository.findById(id);

        LibraryUser libraryUser;

        if(result.isPresent()){
            libraryUser = result.get();
        }
        else{
            throw new EntityNotFoundException("Couldn't find libraryuser with id: " + id);
        }

        return libraryUser;
    }

    @Override
    public LibraryUser save(LibraryUser libraryUser) {
        return libraryUserRepository.save(libraryUser);
    }

    @Override
    public void deleteById(int id) {
        libraryUserRepository.deleteById(id);
    }

    @Override
    public LibraryUser convertLibraryUserDTOToLibraryUserEntity(LibraryUserDTO dto) {

        LibraryUser libraryUser = new LibraryUser();

        libraryUser.setId(dto.getId());
        libraryUser.setUsername(dto.getUsername());
        libraryUser.setEmail(dto.getEmail());
        libraryUser.setRegistrationDate(dto.getRegistrationDate());

        //checkouts will be set after checks done

        return libraryUser;
    }

    @Override
    public LibraryUserDTO convertLibraryUserEntityTolibraryUserDTO(LibraryUser libraryUser) {

        LibraryUserDTO dto = new LibraryUserDTO();

        dto.setId(libraryUser.getId());
        dto.setEmail(libraryUser.getEmail());
        dto.setUsername(libraryUser.getUsername());
        dto.setRegistrationDate(libraryUser.getRegistrationDate());

        if(libraryUser.getCheckouts() == null){
            List<Integer> emptyList = new ArrayList<>();
            dto.setCheckoutIds(emptyList);
        }
        else{
            dto.setCheckoutIds(libraryUser.getCheckouts().stream()
                    .map(Checkout::getId)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    @Override
    public LibraryUser updateLibraryUserPartially(LibraryUser libraryUser, LibraryUserDTO libraryUserDTO) {

        if(libraryUserDTO.getEmail() != null){
            libraryUser.setEmail(libraryUserDTO.getEmail());
        }
        if(libraryUserDTO.getUsername() != null) {
            libraryUser.setUsername(libraryUserDTO.getUsername());
        }
        if(libraryUserDTO.getRegistrationDate() != null){
            libraryUser.setRegistrationDate(libraryUserDTO.getRegistrationDate());
        }

        return libraryUser;
    }

    @Override
    public boolean libraryUserExistsWithUsernameOrNot(String username) {
        return libraryUserRepository.existsLibraryUserByUsernameEquals(username);
    }

    @Override
    public boolean libraryUserExistsWithEmailOrNot(String email) {
        return libraryUserRepository.existsLibraryUserByEmailEquals(email);
    }

    @Override
    public void validateAddLibraryUser(LibraryUserDTO libraryUserDTO) {
        if(libraryUserDTO.getUsername() == null){
            throw new EntityFieldValidationException("Username field of libraryuser cannot be empty.");
        }
        else{
            String usernameInput = libraryUserDTO.getUsername();

            if(libraryUserExistsWithUsernameOrNot(usernameInput)){
                throw new EntityFieldValidationException("Given username \""
                        + usernameInput + "\" already exists in database.");
            }
            else{
                if(usernameInput.matches(".*\\s.*")){
                    throw new EntityFieldValidationException("Given username \""
                            + usernameInput + "\" contains at least one whitespace character.");
                }
            }
        }

        if(libraryUserDTO.getEmail() == null){
            throw new EntityFieldValidationException("Email field of libraryuser cannot be empty.");
        }
        else{
            String emailInput = libraryUserDTO.getEmail();

            if(libraryUserExistsWithEmailOrNot(emailInput)){
                throw new EntityFieldValidationException("CGiven email \""
                        + emailInput + "\" already exists in database.");
            }
            else{
                if(!emailInput.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")){
                    throw new EntityFieldValidationException("Given email \""
                            + emailInput + "\" is not in correct format.");
                }
            }
        }
    }

    @Override
    public void validateUpdateLibraryUser(LibraryUserDTO libraryUserDTO, String username, String email){
        if(libraryUserDTO.getUsername() != null && !libraryUserDTO.getUsername().equals(username) ){ //username change
            String usernameInput = libraryUserDTO.getUsername();

            if(libraryUserExistsWithUsernameOrNot(usernameInput)){
                throw new EntityFieldValidationException("Given username \""
                        + usernameInput + "\" already exists in database.");
            }
            else{
                if(usernameInput.matches(".*\\s.*")){
                    throw new EntityFieldValidationException("Given username \""
                            + usernameInput + "\" contains at least one whitespace character.");
                }
            }
        }
        if(libraryUserDTO.getEmail() != null && !libraryUserDTO.getEmail().equals(email)){ //email change
            String emailInput = libraryUserDTO.getEmail();

            if(libraryUserExistsWithEmailOrNot(emailInput)){
                throw new EntityFieldValidationException("Given email \""
                        + emailInput + "\" already exists in database.");
            }
            else{
                System.out.println("\""+ emailInput + "\"");
                if(!emailInput.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")){
                    throw new EntityFieldValidationException("CGiven email \""
                            + emailInput + "\" is not in correct format.");
                }
            }
        }
    }
}
