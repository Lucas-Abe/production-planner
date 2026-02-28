package com.lucasAbe.production.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.lucasAbe.production.entity.*;
import com.lucasAbe.production.repository.*;

public class ProductionPlannerServiceTest {

    private ProductRepository productRepository;
    private ProductCompositionRepository compositionRepository;
    private ProductionPlannerService service;

    @BeforeEach
    void setup() {
        productRepository = mock(ProductRepository.class);
        compositionRepository = mock(ProductCompositionRepository.class);
        service = new ProductionPlannerService(productRepository, compositionRepository);
    }

    @Test
    void shouldPrioritizeMoreExpensiveProduct() {

        RawMaterial chocolate = new RawMaterial();
        chocolate.setId(1L);
        chocolate.setStockQuantity(100.0);

        Product premium = new Product();
        premium.setId(1L);
        premium.setName("Premium");
        premium.setPrice(20.0);

        Product simple = new Product();
        simple.setId(2L);
        simple.setName("Simple");
        simple.setPrice(5.0);

        ProductComposition compPremium = new ProductComposition();
        compPremium.setProduct(premium);
        compPremium.setRawMaterial(chocolate);
        compPremium.setQuantityRequired(50.0);

        ProductComposition compSimple = new ProductComposition();
        compSimple.setProduct(simple);
        compSimple.setRawMaterial(chocolate);
        compSimple.setQuantityRequired(25.0);

        when(productRepository.findAll())
                .thenReturn(List.of(premium, simple));

        when(compositionRepository.findAll())
                .thenReturn(List.of(compPremium, compSimple));

        var result = service.calculatePlan();

        assertEquals(1, result.size());
        assertEquals("Premium", result.get(0).getProductName());
        assertEquals(2, result.get(0).getQuantityToProduce());
        assertEquals(40.0, result.get(0).getTotalValue());
    }

    @Test
    void shouldReturnEmptyListWhenStockIsInsufficient() {

        RawMaterial chocolate = new RawMaterial();
        chocolate.setId(1L);
        chocolate.setStockQuantity(10.0);

        Product premium = new Product();
        premium.setId(1L);
        premium.setName("Premium");
        premium.setPrice(20.0);

        ProductComposition composition = new ProductComposition();
        composition.setProduct(premium);
        composition.setRawMaterial(chocolate);
        composition.setQuantityRequired(50.0);

        when(productRepository.findAll())
                .thenReturn(List.of(premium));

        when(compositionRepository.findAll())
                .thenReturn(List.of(composition));

        var result = service.calculatePlan();

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnEmptyListWhenProductHasNoComposition() {

        Product product = new Product();
        product.setId(1L);
        product.setName("Test");
        product.setPrice(10.0);

        when(productRepository.findAll())
                .thenReturn(List.of(product));

        when(compositionRepository.findAll())
                .thenReturn(List.of());

        var result = service.calculatePlan();

        assertTrue(result.isEmpty());
    }
}