package com.lucasAbe.production.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lucasAbe.production.entity.RawMaterial;
import com.lucasAbe.production.repository.ProductCompositionRepository;
import com.lucasAbe.production.repository.RawMaterialRepository;

@Service
public class RawMaterialService {

    private final RawMaterialRepository repository;
    private final ProductCompositionRepository compositionRepository;

    public RawMaterialService(RawMaterialRepository repository, ProductCompositionRepository compositionRepository) {
        this.repository = repository;
        this.compositionRepository = compositionRepository;
    }

    public RawMaterial create(RawMaterial rawMaterial) {
        return repository.save(rawMaterial);
    }

    public RawMaterial update(Long id, RawMaterial rawMaterial) {

        RawMaterial existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Raw material not found"));

        existing.setName(rawMaterial.getName());
        existing.setStockQuantity(rawMaterial.getStockQuantity());

        return repository.save(existing);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Raw Material not found");
        }

        compositionRepository.deleteByRawMaterialId(id);

        repository.deleteById(id);
    }

    public List<RawMaterial> list() {
        return repository.findAll();
    }

}
