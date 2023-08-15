package com.example.Gestion_cabinet_backend.repository;

import com.example.Gestion_cabinet_backend.models.MedicamentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicamentRepository extends JpaRepository<MedicamentEntity,Integer> {
    boolean existsByNomAndForme(String nom, String forme);

}
