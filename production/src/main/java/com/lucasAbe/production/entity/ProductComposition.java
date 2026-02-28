package com.lucasAbe.production.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
public class ProductComposition {

    public ProductComposition() { }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "raw_material_id", nullable = false)
    private RawMaterial rawMaterial;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @NotNull
    @Positive(message = "has to be more than zero")
    private Double quantityRequired;

    public Long getId(){
        return id;
    }

    public void setRawMaterial(RawMaterial rawMaterial){
        this.rawMaterial = rawMaterial;
    }

    public RawMaterial getRawMaterial(){
        return rawMaterial;
    }

    public void setProduct(Product product){
        this.product = product;
    }

    public Product getProduct(){
        return product;
    }

    public void setQuantityRequired(Double quantityRequired){
        this.quantityRequired = quantityRequired;
    }

    public Double getQuantityRequired(){
        return quantityRequired;
    }

}
