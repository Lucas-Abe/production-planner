package com.lucasAbe.production.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.lucasAbe.production.entity.ProductComposition;

import jakarta.transaction.Transactional;

public interface ProductCompositionRepository extends JpaRepository<ProductComposition, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM ProductComposition pc WHERE pc.product.id = :productId")
    void deleteByProductId(Long productId);

    @Transactional
    @Modifying
    @Query("DELETE FROM ProductComposition pc WHERE pc.rawMaterial.id = :rawMaterialId")
    void deleteByRawMaterialId(Long rawMaterialId);

}
