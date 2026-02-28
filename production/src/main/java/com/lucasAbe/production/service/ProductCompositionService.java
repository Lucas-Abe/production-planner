package com.lucasAbe.production.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lucasAbe.production.entity.ProductComposition;
import com.lucasAbe.production.repository.ProductCompositionRepository;

@Service
public class ProductCompositionService {

    private final ProductCompositionRepository repository;

    public ProductCompositionService(ProductCompositionRepository repository){
        this.repository = repository;
    }

    public ProductComposition create(ProductComposition product){
        return repository.save(product);
    }

    public List<ProductComposition> list(){
        return repository.findAll();
    }

}
