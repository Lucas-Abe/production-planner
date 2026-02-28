package com.lucasAbe.production.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lucasAbe.production.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
