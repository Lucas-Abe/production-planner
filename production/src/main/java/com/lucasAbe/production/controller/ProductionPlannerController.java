package com.lucasAbe.production.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasAbe.production.dto.ProductionPlanItem;
import com.lucasAbe.production.service.ProductionPlannerService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/production-plan")
public class ProductionPlannerController {

    private final ProductionPlannerService service;

    public ProductionPlannerController(ProductionPlannerService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductionPlanItem> generatePlan() {
        return service.calculatePlan();
    }
}