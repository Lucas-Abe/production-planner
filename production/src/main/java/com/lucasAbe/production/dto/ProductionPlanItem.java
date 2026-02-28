package com.lucasAbe.production.dto;

public class ProductionPlanItem {

    private String productName;
    private Integer quantityToProduce;
    private Double totalValue;

    public ProductionPlanItem(String productName, Integer quantityToProduce, Double totalValue) {
        this.productName = productName;
        this.quantityToProduce = quantityToProduce;
        this.totalValue = totalValue;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getQuantityToProduce() {
        return quantityToProduce;
    }

    public Double getTotalValue() {
        return totalValue;
    }
}