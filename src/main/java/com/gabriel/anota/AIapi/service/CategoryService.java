package com.gabriel.anota.AIapi.service;


import com.gabriel.anota.AIapi.domain.category.Category;
import com.gabriel.anota.AIapi.domain.category.CategoryDTO;
import com.gabriel.anota.AIapi.domain.category.exceptions.CategoryNotFoundException;
import com.gabriel.anota.AIapi.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repository;


    public Category insert(CategoryDTO categoryData){
        Category category = new Category(categoryData);
        return repository.save(category);
    }
    public List<Category> findAll(){
        return repository.findAll();
    }
    public Category update(String id, CategoryDTO categoryData){
        Category category = repository.findById(id).orElseThrow(CategoryNotFoundException::new);
        if (!categoryData.title().isEmpty())
            category.setTitle(categoryData.title());
        if (!categoryData.description().isEmpty())
            category.setDescription(categoryData.description());
        return repository.save(category);
    }

    public void delete(String id){
        Optional<Category> category = repository.findById(id);
        if (category.isPresent())
            repository.delete(category.get());
        else
            throw new CategoryNotFoundException();
    }

    public Optional<Category> findById(String id) {
        return repository.findById(id);
    }

}
