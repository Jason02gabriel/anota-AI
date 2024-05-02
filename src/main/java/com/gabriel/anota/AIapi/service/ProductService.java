package com.gabriel.anota.AIapi.service;


import com.gabriel.anota.AIapi.domain.category.Category;
import com.gabriel.anota.AIapi.domain.category.exceptions.CategoryNotFoundException;
import com.gabriel.anota.AIapi.domain.products.Product;
import com.gabriel.anota.AIapi.domain.products.ProductDTO;
import com.gabriel.anota.AIapi.domain.products.exceptions.ProductNotFoundException;
import com.gabriel.anota.AIapi.repository.ProductRepository;
import com.gabriel.anota.AIapi.service.aws.AwsSnsService;
import com.gabriel.anota.AIapi.service.aws.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AwsSnsService awsSnsService;


    public Product insert(ProductDTO productData){
        Category category = categoryService.findById(productData.categoryId())
                .orElseThrow(CategoryNotFoundException::new);
        Product product = new Product(productData);
        product.setCategory(category);
        repository.save(product);
        awsSnsService.publishMessage(new MessageDTO(product.getOwnerId()));
        return product;

    }
    public List<Product> findAll(){
        return repository.findAll();
    }

    public Product update(String id, ProductDTO productData){
        Product product = repository.findById(id).orElseThrow(ProductNotFoundException::new);
        if(productData.categoryId() !=null) {
            categoryService.findById(productData.categoryId())
                    .ifPresent(product::setCategory);
        }
        if (!productData.title().isEmpty())
            product.setTitle(productData.title());
        if (!productData.description().isEmpty())
            product.setDescription(productData.description());
        if (!(productData.price() == null))
            product.setPrice(productData.price());

        repository.save(product);
        awsSnsService.publishMessage(new MessageDTO(product.getOwnerId()));
        return product;


    }

    public void delete(String id){
        Optional<Product> product = repository.findById(id);
        if (product.isPresent())
            repository.delete(product.get());
        else
            throw new CategoryNotFoundException();
    }
}
