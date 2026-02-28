package com.lucasAbe.production.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lucasAbe.production.entity.Product;
import com.lucasAbe.production.repository.ProductRepository;
import com.lucasAbe.production.repository.ProductCompositionRepository;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final ProductCompositionRepository compositionRepository;

    public ProductService(ProductRepository repository,
                          ProductCompositionRepository compositionRepository){
        this.repository = repository;
        this.compositionRepository = compositionRepository;
    }

    public Product create(Product product){
        return repository.save(product);
    }

    public Product update(Long id, Product product){

        Product existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existing.setName(product.getName());
        existing.setPrice(product.getPrice());

        return repository.save(existing);
    }

    public void delete(Long id){

        if(!repository.existsById(id)){
            throw new RuntimeException("Product not found");
        }

        compositionRepository.deleteByProductId(id);

        repository.deleteById(id);
    }

    public List<Product> list(){
        return repository.findAll();
    }
}