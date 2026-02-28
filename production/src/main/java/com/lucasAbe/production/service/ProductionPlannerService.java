package com.lucasAbe.production.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lucasAbe.production.dto.ProductionPlanItem;
import com.lucasAbe.production.entity.Product;
import com.lucasAbe.production.entity.ProductComposition;
import com.lucasAbe.production.entity.RawMaterial;
import com.lucasAbe.production.repository.ProductCompositionRepository;
import com.lucasAbe.production.repository.ProductRepository;

@Service
public class ProductionPlannerService {

    private final ProductRepository productRepository;
    private final ProductCompositionRepository compositionRepository;

    public ProductionPlannerService(ProductRepository productRepository,
            ProductCompositionRepository compositionRepository) {
        this.productRepository = productRepository;
        this.compositionRepository = compositionRepository;
    }

    public List<ProductionPlanItem> calculatePlan() {

        List<Product> products = productRepository.findAll()
                .stream()
                .sorted((p1, p2) -> Double.compare(p2.getPrice(), p1.getPrice()))
                .toList();

        List<ProductComposition> allCompositions = compositionRepository.findAll();

        Map<Long, Double> stockMap = allCompositions.stream()
                .map(ProductComposition::getRawMaterial)
                .distinct()
                .collect(Collectors.toMap(
                        RawMaterial::getId,
                        RawMaterial::getStockQuantity));

        List<ProductionPlanItem> plan = new ArrayList<>();

        for (Product product : products) {

            List<ProductComposition> compositions = allCompositions.stream()
                    .filter(c -> c.getProduct().getId().equals(product.getId()))
                    .toList();

            if (compositions.isEmpty()) continue;

            int maxUnits = Integer.MAX_VALUE;

            for (ProductComposition composition : compositions) {

                Long rawMaterialId = composition.getRawMaterial().getId();
                Double availableStock = stockMap.get(rawMaterialId);
                Double required = composition.getQuantityRequired();

                int possibleUnits = (int) (availableStock / required);

                maxUnits = Math.min(maxUnits, possibleUnits);
            }

            if (maxUnits <= 0)
                continue;

            for (ProductComposition composition : compositions) {

                Long rawMaterialId = composition.getRawMaterial().getId();
                Double required = composition.getQuantityRequired();

                stockMap.put(
                        rawMaterialId,
                        stockMap.get(rawMaterialId) - (required * maxUnits));
            }

            plan.add(new ProductionPlanItem(
                    product.getName(),
                    maxUnits,
                    product.getPrice() * maxUnits));
        }

        return plan;
    }
}