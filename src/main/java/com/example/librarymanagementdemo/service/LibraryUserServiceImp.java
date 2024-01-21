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
import java.util.Date;
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
    public List<LibraryUser> findAllWithOptionalFilter(Date registrationDateBefore, Date registrationDateAfter) {
        return libraryUserRepository.findByRegistrationDateBeforeAndRegistrationDateAfter(registrationDateBefore, registrationDateAfter);
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
    public void checkDuplicateEmailAndUsernameForAdd(LibraryUserDTO libraryUserDTO) {
        if(libraryUserExistsWithUsernameOrNot(libraryUserDTO.getUsername())){
            throw new EntityFieldValidationException("Given username already exists in database");
        }
        if(libraryUserExistsWithEmailOrNot(libraryUserDTO.getEmail())){
            throw new EntityFieldValidationException("Given email already exists in database");
        }
    }

    @Override
    public void checkDuplicateEmailAndUsernameForUpdate(LibraryUserDTO libraryUserDTO, String username, String email){
        if(!libraryUserDTO.getUsername().equals(username) && libraryUserExistsWithUsernameOrNot(libraryUserDTO.getUsername())){
            throw new EntityFieldValidationException("Given username already exists in database");
        }
        if(!libraryUserDTO.getEmail().equals(email) && libraryUserExistsWithEmailOrNot(libraryUserDTO.getEmail())){
            throw new EntityFieldValidationException("Given email already exists in database");
        }
    }
}
