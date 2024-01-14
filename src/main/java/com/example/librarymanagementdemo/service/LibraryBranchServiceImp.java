package com.example.librarymanagementdemo.service;

import com.example.librarymanagementdemo.dto.LibraryBranchDTO;
import com.example.librarymanagementdemo.entity.Book;
import com.example.librarymanagementdemo.entity.LibraryBranch;
import com.example.librarymanagementdemo.exception.EntityFieldValidationException;
import com.example.librarymanagementdemo.exception.EntityNotFoundException;
import com.example.librarymanagementdemo.repository.LibraryBranchRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LibraryBranchServiceImp implements LibraryBranchService{

    private LibraryBranchRepository libraryBranchRepository;
    private EntityManager entityManager;

    @Autowired
    public LibraryBranchServiceImp(LibraryBranchRepository libraryBranchRepository, EntityManager entityManager) {
        this.libraryBranchRepository = libraryBranchRepository;
        this.entityManager = entityManager;
    }

    @Override
    public List<LibraryBranch> findAll() {
        return libraryBranchRepository.findAll(); //if no branches found, returns empty list
    }

    @Override
    public LibraryBranch findById(int id) {

        Optional<LibraryBranch> result = libraryBranchRepository.findById(id);

        LibraryBranch libraryBranch;

        if (result.isPresent()) {
            libraryBranch = result.get();
        }
        else{
            throw new EntityNotFoundException("Couldn't find library branch with id: " + id);
        }

        return libraryBranch;
    }

    @Override
    public List<LibraryBranch> findByFilter(String name, String location, Integer capacity) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<LibraryBranch> query = builder.createQuery(LibraryBranch.class);
        Root<LibraryBranch> root = query.from(LibraryBranch.class);

        List<Predicate> predicates = new ArrayList<>();

        if(name != null){
            predicates.add(builder.equal(root.get("name"), name));
        }
        if(location != null){
            predicates.add(builder.equal(root.get("location"), location));
        }
        if(capacity != null){
            predicates.add(builder.equal(root.get("capacity"), capacity));
        }

        if(!predicates.isEmpty()){
            query.where(builder.and(predicates.toArray(new Predicate[0])));
        }

        TypedQuery<LibraryBranch> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }

    @Override
    public LibraryBranch save(LibraryBranch libraryBranch) { //can be used both in update and create
        return libraryBranchRepository.save(libraryBranch);
    }


    @Override
    public LibraryBranch setBooksOfLibraryBranch(LibraryBranch libraryBranch, List<Book> books) {
        libraryBranch.setBooks(books);

        return libraryBranch;
    }
    @Override
    public LibraryBranch setBooksAndSaveLibraryBranch(LibraryBranch libraryBranch, List<Book> books) {

        LibraryBranch tempLibraryBranch = libraryBranch;

        if (books != null) {
            tempLibraryBranch = setBooksOfLibraryBranch(tempLibraryBranch, books);
        }
        return save(tempLibraryBranch);
    }

    @Override
    public void deleteById(int id) {
       libraryBranchRepository.deleteById(id);
    }

    @Override
    public LibraryBranch convertLibraryBranchDTOToLibraryBranchEntity(LibraryBranchDTO dto) {

        LibraryBranch libraryBranch = new LibraryBranch();

        libraryBranch.setId(dto.getId());
        libraryBranch.setName(dto.getName());
        libraryBranch.setLocation(dto.getLocation());
        libraryBranch.setCapacity(dto.getCapacity());

        //books will be set after checks done

        return libraryBranch;

    }

    @Override
    public LibraryBranchDTO convertLibraryBranchEntityToLibraryBranchDTO(LibraryBranch libraryBranch) {

        LibraryBranchDTO dto = new LibraryBranchDTO();

        dto.setId(libraryBranch.getId());
        dto.setName(libraryBranch.getName());
        dto.setLocation(libraryBranch.getLocation());
        dto.setCapacity(libraryBranch.getCapacity());

        if(libraryBranch.getBooks() == null){
            List<Integer> emptyList = new ArrayList<>();
            dto.setBookIds(emptyList);
        }
        else{
            dto.setBookIds(libraryBranch.getBooks().stream()
                    .map(Book::getId)
                    .collect(Collectors.toList()));
        }

        return dto;

    }

    @Override
    public LibraryBranch updateLibraryBranchPartially(LibraryBranch libraryBranch, LibraryBranchDTO libraryBranchDTO) {

        //id already exists
        if(libraryBranchDTO.getName() != null){
            libraryBranch.setName(libraryBranchDTO.getName());
        }
        if(libraryBranchDTO.getCapacity() != null){
            libraryBranch.setCapacity(libraryBranchDTO.getCapacity());
        }
        if(libraryBranchDTO.getLocation() != null){
            libraryBranch.setLocation(libraryBranchDTO.getLocation());
        }

        return libraryBranch;
    }

    @Override
    public void validateLibraryBranch(LibraryBranchDTO libraryBranchDTO){
        if(libraryBranchDTO.getName() != null){
            if(libraryBranchDTO.getName().trim().isEmpty()){
                throw new EntityFieldValidationException("Wrong library branch name format. Library branch name only contains white spaces.");
            }
        }
    }

}
