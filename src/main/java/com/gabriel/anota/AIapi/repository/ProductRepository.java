package com.gabriel.anota.AIapi.repository;

import com.gabriel.anota.AIapi.domain.products.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String>{
}
