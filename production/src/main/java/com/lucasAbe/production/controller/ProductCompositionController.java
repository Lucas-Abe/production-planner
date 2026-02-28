package com.lucasAbe.production.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import com.lucasAbe.production.entity.ProductComposition;
import com.lucasAbe.production.service.ProductCompositionService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/products-composition")
public class ProductCompositionController {

    private final ProductCompositionService service;

    public ProductCompositionController(ProductCompositionService service){
        this.service = service;
    }

    @PostMapping
    public ProductComposition create(@Valid @RequestBody ProductComposition productComposition){
        return service.create(productComposition);
    }

    @GetMapping
    public List<ProductComposition> list(){
        return service.list();
    }

}
