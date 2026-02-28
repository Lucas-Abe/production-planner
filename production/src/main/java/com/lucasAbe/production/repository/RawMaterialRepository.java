package com.lucasAbe.production.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lucasAbe.production.entity.RawMaterial;

public interface RawMaterialRepository extends JpaRepository<RawMaterial, Long> {

}
