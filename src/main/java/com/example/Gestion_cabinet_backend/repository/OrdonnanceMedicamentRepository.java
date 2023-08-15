package com.example.Gestion_cabinet_backend.repository;

import com.example.Gestion_cabinet_backend.models.OrdonnanceEntity;
import com.example.Gestion_cabinet_backend.models.OrdonnanceMedicament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdonnanceMedicamentRepository extends JpaRepository<OrdonnanceMedicament,Integer> {
}
