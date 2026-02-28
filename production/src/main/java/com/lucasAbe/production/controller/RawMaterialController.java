package com.lucasAbe.production.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import com.lucasAbe.production.entity.RawMaterial;
import com.lucasAbe.production.service.RawMaterialService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/raw-materials")
public class RawMaterialController {

    private final RawMaterialService service;

    public RawMaterialController(RawMaterialService service){
        this.service = service;
    }

    @PutMapping("/{id}")
    public RawMaterial update(@PathVariable Long id, @Valid @RequestBody RawMaterial rawMaterial){
        return service.update(id, rawMaterial);
    }

    @PostMapping
    public RawMaterial create(@RequestBody @Valid RawMaterial rawMaterial){
        return service.create(rawMaterial);
    }

    @GetMapping
    public List<RawMaterial> list(){
        return service.list();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

}
